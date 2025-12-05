package io.github.lunasaw.zlm.api.client.http;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 Apache HttpClient5 的默认实现，内置连接池、超时与简单重试。
 */
public class HttpClient5Executor implements HttpExecutor {

    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;

    private HttpClient5Executor(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String postForm(String url, Map<String, String> params) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);

        List<NameValuePair> pairs = new ArrayList<>();
        if (params != null) {
            params.forEach((k, v) -> pairs.add(new BasicNameValuePair(k, v)));
        }
        post.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));

        return httpClient.execute(post, response -> {
            int code = response.getCode();
            String body = response.getEntity() != null
                    ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
                    : "";
            if (code >= 400) {
                throw new HttpStatusException(code, "Unexpected HTTP status " + code + " from " + url);
            }
            return body;
        });
    }

    @Override
    public byte[] postFormForBytes(String url, Map<String, String> params) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);

        List<NameValuePair> pairs = new ArrayList<>();
        if (params != null) {
            params.forEach((k, v) -> pairs.add(new BasicNameValuePair(k, v)));
        }
        post.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));

        return httpClient.execute(post, response -> {
            int code = response.getCode();
            byte[] body = response.getEntity() != null
                    ? EntityUtils.toByteArray(response.getEntity())
                    : new byte[0];
            if (code >= 400) {
                throw new HttpStatusException(code, "Unexpected HTTP status " + code + " from " + url);
            }
            return body;
        });
    }

    @Override
    public String postJson(String url, String json) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setEntity(new StringEntity(json == null ? "" : json, ContentType.APPLICATION_JSON));

        return httpClient.execute(post, response -> {
            int code = response.getCode();
            String body = response.getEntity() != null
                    ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
                    : "";
            if (code >= 400) {
                throw new HttpStatusException(code, "Unexpected HTTP status " + code + " from " + url);
            }
            return body;
        });
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }

    public static final class Builder {
        private int maxTotalConnections = 200;                 // 连接池总连接数
        private int maxConnectionsPerRoute = 50;               // 每路由最大连接数
        private Duration connectTimeout = Duration.ofSeconds(1);            // TCP 连接超时
        private Duration responseTimeout = Duration.ofSeconds(5);           // 响应超时/读超时
        private Duration connectionRequestTimeout = Duration.ofSeconds(1);  // 连接租借超时
        private Duration connectionTtl = Duration.ofMinutes(5);             // 连接存活时长
        private Duration validateAfterInactivity = Duration.ofSeconds(10);  // 空闲校验间隔
        private int retryCount = 0;                                         // 重试次数（仅针对幂等请求）
        private Duration retryInterval = Duration.ofMillis(200);            // 重试间隔

        public Builder maxTotalConnections(int maxTotalConnections) {
            this.maxTotalConnections = maxTotalConnections;
            return this;
        }

        public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
            this.maxConnectionsPerRoute = maxConnectionsPerRoute;
            return this;
        }

        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder responseTimeout(Duration responseTimeout) {
            this.responseTimeout = responseTimeout;
            return this;
        }

        public Builder connectionRequestTimeout(Duration connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }

        public Builder connectionTtl(Duration connectionTtl) {
            this.connectionTtl = connectionTtl;
            return this;
        }

        public Builder validateAfterInactivity(Duration validateAfterInactivity) {
            this.validateAfterInactivity = validateAfterInactivity;
            return this;
        }

        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public Builder retryInterval(Duration retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public HttpClient5Executor build() {
            // 连接级配置：控制 TCP 连接建立/读超时，生存时间与空闲校验
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setConnectTimeout(asTimeout(connectTimeout))
                    .setSocketTimeout(asTimeout(responseTimeout))
                    .setTimeToLive(asTimeValue(connectionTtl))
                    .setValidateAfterInactivity(asTimeValue(validateAfterInactivity))
                    .build();

            // 连接池：总量/每路由容量 + 严格并发 + LIFO 复用
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .setDefaultConnectionConfig(connectionConfig)
                            .setMaxConnTotal(maxTotalConnections)
                            .setMaxConnPerRoute(maxConnectionsPerRoute)
                            .build();

            // 每次请求的超时：响应超时、从池中租借连接超时
            RequestConfig config = RequestConfig.custom()
                    .setResponseTimeout(asTimeout(responseTimeout))
                    .setConnectionRequestTimeout(asTimeout(connectionRequestTimeout))
                    .build();

            // 简单重试策略：仅用于幂等请求，非幂等调用不建议开启
            TimeValue interval = retryInterval == null
                    ? TimeValue.ZERO_MILLISECONDS
                    : TimeValue.ofMilliseconds(retryInterval.toMillis());
            DefaultHttpRequestRetryStrategy retryStrategy = new DefaultHttpRequestRetryStrategy(retryCount, interval);

            // HttpClient：连接池 + 过期/空闲清理 + 可选重试
            CloseableHttpClient client = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setRetryStrategy(retryStrategy)
                    .evictExpiredConnections()
                    .evictIdleConnections(TimeValue.ofMinutes(2))
                    .build();

            return new HttpClient5Executor(client, config);
        }

        private Timeout asTimeout(Duration duration) {
            return duration == null ? Timeout.ZERO_MILLISECONDS : Timeout.ofMilliseconds(duration.toMillis());
        }

        private TimeValue asTimeValue(Duration duration) {
            return duration == null ? TimeValue.ZERO_MILLISECONDS : TimeValue.ofMilliseconds(duration.toMillis());
        }
    }
}
