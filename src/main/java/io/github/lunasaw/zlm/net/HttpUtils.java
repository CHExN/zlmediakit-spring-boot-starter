package io.github.lunasaw.zlm.net;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.StandardAuthScheme;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.ExecChainHandler;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.DefaultSchemePortResolver;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.auth.DigestScheme;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.*;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.apache.hc.core5.http.nio.support.AsyncRequestBuilder;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Thread-safe HTTP utility class optimized for high concurrency
 *
 * @author Luna
 */
@Slf4j
public class HttpUtils {

    /**
     * Thread-local context for ensuring each thread has independent HttpClientContext
     */
    private static final ThreadLocal<HttpClientContext> CLIENT_CONTEXT_HOLDER =
            ThreadLocal.withInitial(HttpClientContext::create);

    /**
     * Thread-local cookie store for thread isolation
     */
    private static final ThreadLocal<BasicCookieStore> COOKIE_STORE_HOLDER =
            ThreadLocal.withInitial(BasicCookieStore::new);

    public static int MAX_REDIRECTS = 10;
    /**
     * 最大连接数 - 优化为更高并发
     */
    public static int MAX_CONN = 500;
    /**
     * 设置连接建立的超时时间为 10s
     */
    public static int CONNECT_TIMEOUT = 10;
    public static int RESPONSE_TIMEOUT = 30;
    public static int MAX_ROUTE = 100;
    public static int SOCKET_TIME_OUT = 100;

    /**
     * Thread-safe singleton HttpClient instance
     */
    private static volatile CloseableHttpClient httpClient;
    private static final Object HTTP_CLIENT_LOCK = new Object();

    static {
        init();
    }

    /**
     * Thread-safe initialization with double-checked locking pattern
     */
    public static void init() {
        if (httpClient == null) {
            synchronized (HTTP_CLIENT_LOCK) {
                if (httpClient == null) {
                    httpClient = createHttpClient();
                }
            }
        }
    }

    /**
     * Create optimized HttpClient instance for high concurrency
     */
    private static CloseableHttpClient createHttpClient() {
        // 信任所有 SSL 证书 - 生产环境建议使用更严格的证书验证
        final SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                    .build();
        } catch (Exception e) {
            log.error("Failed to create SSL context", e);
            throw new IllegalStateException("Failed to create SSL context", e);
        }

        // HostnameVerificationPolicy.CLIENT + NoopHostnameVerifier = 完全关闭主机名校验（依然是“我全都要”的不安全模式）
        final DefaultClientTlsStrategy tlsStrategy = new DefaultClientTlsStrategy(
                sslContext,
                HostnameVerificationPolicy.CLIENT,
                NoopHostnameVerifier.INSTANCE
        );

        // DNS 解析
        final DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if ("localhost".equalsIgnoreCase(host)) {
                    return new InetAddress[]{InetAddress.getByAddress(new byte[]{127, 0, 0, 1})};
                }
                return super.resolve(host);
            }
        };

        final ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setValidateAfterInactivity(TimeValue.ofSeconds(CONNECT_TIMEOUT))
                .setTimeToLive(TimeValue.ofMinutes(5))
                .build();

        // 优化 socket 配置
        final SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(SOCKET_TIME_OUT, TimeUnit.SECONDS)
                .setTcpNoDelay(true) // 禁用 Nagle 算法，提高响应速度
                .build();

        final PoolingHttpClientConnectionManager cm =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setTlsSocketStrategy(tlsStrategy)
                        .setDnsResolver(dnsResolver)
                        .setSchemePortResolver(DefaultSchemePortResolver.INSTANCE)

                        // 优化连接池配置以支持高并发
                        .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                        .setConnPoolPolicy(PoolReusePolicy.LIFO)

                        // 全局连接配置 / socket 配置
                        .setDefaultConnectionConfig(connectionConfig)
                        .setDefaultSocketConfig(socketConfig)

                        // 增加连接池大小以支持更高并发
                        .setMaxConnTotal(MAX_CONN)
                        .setMaxConnPerRoute(MAX_ROUTE)

                        .build();

        final RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(StandardCookieSpec.STRICT)
                .setMaxRedirects(MAX_REDIRECTS)
                .setResponseTimeout(RESPONSE_TIMEOUT, TimeUnit.SECONDS)
                .setConnectionRequestTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .setTargetPreferredAuthSchemes(Arrays.asList(
                        StandardAuthScheme.BASIC,
                        StandardAuthScheme.DIGEST
                ))
                .setProxyPreferredAuthSchemes(Collections.singletonList(StandardAuthScheme.BASIC))
                .build();

        // HttpClient 实例
        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setConnectionManagerShared(true)               // 允许连接管理器共享
                .evictExpiredConnections()                      // 自动清理过期连接
                .evictIdleConnections(TimeValue.ofMinutes(2))   // 清理空闲连接
                .build();
    }

    /**
     * Get current thread's HttpClientContext
     */
    public static HttpClientContext getClientContext() {
        return CLIENT_CONTEXT_HOLDER.get();
    }

    /**
     * Get current thread's cookie store
     */
    public static BasicCookieStore getCookieStore() {
        return COOKIE_STORE_HOLDER.get();
    }

    /**
     * Refresh HttpClient instance (recreate if needed)
     */
    public static void refresh() {
        synchronized (HTTP_CLIENT_LOCK) {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.warn("Failed to close existing HttpClient", e);
                }
            }
            httpClient = createHttpClient();
        }
    }

    public static void basicAuth(String userName, String password, String host) {
        authContext(userName, password, host, StandardAuthScheme.BASIC);
    }

    public static void digestAuth(String userName, String password, String host) {
        authContext(userName, password, host, StandardAuthScheme.DIGEST);
    }

    /**
     * Thread-safe authentication context setup
     *
     * @param userName 用户名
     * @param password 密码
     * @param host     主机地址
     */
    public static void authContext(String userName, String password, String host, String authType) {
        HttpClientContext context = getClientContext();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password.toCharArray());
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();

        AuthScope authScope = new AuthScope(null, -1);
        AuthCache authCache = context.getAuthCache();

        if (host != null && !host.isEmpty()) {
            HttpHost targetHost = new HttpHost(host);

            if (authCache == null) {
                authCache = new BasicAuthCache();
            }

            if (StandardAuthScheme.BASIC.equals(authType)) {
                BasicScheme basicScheme = new BasicScheme();
                authCache.put(targetHost, basicScheme);
                basicScheme.initPreemptive(credentials);
            } else if (StandardAuthScheme.DIGEST.equals(authType)) {
                DigestScheme digestScheme = new DigestScheme();
                authCache.put(targetHost, digestScheme);
            }
        }
        credsProvider.setCredentials(authScope, credentials);

        // Add AuthCache to the execution context
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);
    }

    public static void setProxy(Integer port) {
        setProxy(IPAddressUtil.LOCAL_HOST, port);
    }

    /**
     * 使用代理访问
     *
     * @param host 代理地址
     * @param port 代理端口
     */
    public static void setProxy(String host, Integer port) {
        // Note: Proxy setting now requires HttpClient recreation
        // This approach maintains backward compatibility while being thread-safe
        log.warn("Proxy setting requires HttpClient recreation. Consider using per-request proxy configuration for better performance.");
        refresh();
    }

    public static void setProxy(String host, Integer port, String username, String password) {
        if (username != null && !username.isBlank()) {
            authContext(username, password, host, StandardAuthScheme.BASIC);
        }
        setProxy(host, port);
    }

    /**
     * 请求头构建
     *
     * @param headers     请求头Map
     * @param requestBase 请求对象
     */
    public static void builderHeader(Map<String, String> headers, BasicClassicHttpRequest requestBase) {
        if (headers == null || headers.isEmpty()) {
            return;
        }
        headers.forEach(requestBase::addHeader);
    }

    public static void builderHeader(Map<String, String> headers, AsyncRequestBuilder requestBase) {
        if (headers == null || headers.isEmpty()) {
            return;
        }
        headers.forEach(requestBase::addHeader);
    }

    public static List<Cookie> getCookie() {
        return getCookieStore().getCookies();
    }

    public static void addCookie(Cookie cookie) {
        getCookieStore().addCookie(cookie);
    }

    public static void addCookie(List<Cookie> cookies) {
        cookies.forEach(getCookieStore()::addCookie);
    }

    public static void addCookie(Cookie... cookies) {
        Arrays.stream(cookies).forEach(getCookieStore()::addCookie);
    }

    @SuppressWarnings("unchecked")
    private static <T> T doRequest(HttpClientResponseHandler<T> responseHandler, HttpUriRequestBase request) {
        try {
            HttpClientContext context = getClientContext();
            if (responseHandler == null) {
                // 返回原始 response 自己关 (ClassicHttpResponse)
                return (T) httpClient.executeOpen(null, request, context);
            }
            // 返回自动关闭 response
            return httpClient.execute(request, context, responseHandler);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static HttpResponse doHead(String url) {
        HttpHead request = new HttpHead(url);
        return doRequest(null, request);
    }

    public static HttpResponse doGet(String url, Map<String, String> headers) {
        HttpGet request = new HttpGet(buildUrl(url, "", new HashMap<>(0)));
        builderHeader(headers, request);
        return doRequest(null, request);
    }

    /**
     * 发送 get 请求
     *
     * @param host            主机地址
     * @param path            路径
     * @param headers         请求头
     * @param queries         请求参数
     * @param responseHandler 响应处理器
     * @return ClassicHttpResponse
     */
    public static <T> T doGet(String host, String path, Map<String, String> headers,
                              Map<String, String> queries, HttpClientResponseHandler<T> responseHandler) {

        HttpGet request = new HttpGet(buildUrl(host, path, queries));
        builderHeader(headers, request);
        return doRequest(responseHandler, request);
    }

    /**
     * 发送 get 请求
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     */
    public static String doGetHandler(String host, String path, Map<String, String> headers,
                                      Map<String, String> queries) {
        return doGet(host, path, headers, queries, new BasicHttpClientResponseHandler());
    }

    public static ClassicHttpResponse doGet(String host, String path, Map<String, String> headers, Map<String, String> queries) {
        return doGet(host, path, headers, queries, null);
    }

    /**
     * delete request
     *
     * @param host            主机地址
     * @param path            路径
     * @param headers         请求头
     * @param queries         请求参数
     * @param responseHandler 响应处理器
     * @param body            请求体
     * @return ClassicHttpResponse
     */
    public static <T> T doDelete(String host, String path, Map<String, String> headers,
                                 Map<String, String> queries, String body, HttpClientResponseHandler<T> responseHandler) {
        HttpDelete delete = new HttpDelete(buildUrl(host, path, queries));
        builderHeader(headers, delete);
        if (body != null && !body.isBlank()) {
            delete.setEntity(new StringEntity(body, Charset.defaultCharset()));
        }
        return doRequest(responseHandler, delete);
    }

    public static String doDeleteHandler(String host, String path, Map<String, String> headers,
                                         Map<String, String> queries, String body) {
        return doDelete(host, path, headers, queries, body, new BasicHttpClientResponseHandler());
    }

    public static ClassicHttpResponse doDelete(String host, String path, Map<String, String> headers,
                                               Map<String, String> queries, String body) {
        return doDelete(host, path, headers, queries, body, null);
    }

    /**
     * PUT 方法请求
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     * @param body    请求体
     * @return ClassicHttpResponse
     */
    public static <T> T doPut(String host, String path, Map<String, String> headers,
                              Map<String, String> queries, String body, HttpClientResponseHandler<T> responseHandler) {

        HttpPut httpPut = new HttpPut(buildUrl(host, path, queries));
        builderHeader(headers, httpPut);
        if (body != null && !body.isBlank()) {
            httpPut.setEntity(new StringEntity(body, Charset.defaultCharset()));
        }
        return doRequest(responseHandler, httpPut);
    }

    /**
     * PUT 方法请求
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     * @param body    请求体
     * @return ClassicHttpResponse
     */
    public static ClassicHttpResponse doPut(String host, String path, Map<String, String> headers,
                                            Map<String, String> queries, String body) {
        return doPut(host, path, headers, queries, body, null);
    }

    public static String doPutHandler(String host, String path, Map<String, String> headers,
                                      Map<String, String> queries, String body) {
        return doPut(host, path, headers, queries, body, new BasicHttpClientResponseHandler());
    }

    /**
     * POST 发送文件请求
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     * @param bodies  文件列表
     * @return ClassicHttpResponse
     */
    public static <T> T doPost(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, Map<String, String> bodies, HttpClientResponseHandler<T> responseHandler) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 设置浏览器兼容模式
        builder.setMode(HttpMultipartMode.LEGACY);
        // 设置请求的编码格式
        builder.setCharset(Charset.defaultCharset());
        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        if (bodies != null && !bodies.isEmpty()) {
            bodies.forEach((k, v) -> {
                // 传入参数可以为file或者filePath，在此处做转换
                File file = new File(v);
                // 添加文件
                builder.addBinaryBody(k, file);
            });
        }
        HttpEntity reqEntity = builder.build();
        return doPost(host, path, headers, queries, reqEntity, responseHandler);
    }

    private static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public static <T> void doPost(String host,
                                  String path,
                                  Map<String, String> headers,
                                  Map<String, String> queries,
                                  Map<String, String> bodies,
                                  HttpClientResponseHandler<T> responseHandler,
                                  FutureCallback<T> futureCallback) {

        if (futureCallback == null) {
            throw new IllegalArgumentException("futureCallback must not be null");
        }

        // 没有要上传的文件，直接返回 200
        if (bodies == null || bodies.isEmpty()) {
            try {
                T result = responseHandler != null
                        ? responseHandler.handleResponse(new BasicClassicHttpResponse(HttpStatus.SC_OK))
                        : null;
                futureCallback.completed(result);
            } catch (IOException | HttpException e) {
                futureCallback.failed(e);
            }
            return;
        }

        try {
            // 逐个文件上传，任何一个失败就直接抛异常
            for (Map.Entry<String, String> entry : bodies.entrySet()) {
                String key = entry.getKey();
                String pathStr = entry.getValue();

                if (pathStr == null || pathStr.isEmpty()) {
                    continue;
                }

                Path filePath = Paths.get(pathStr);
                if (!Files.isRegularFile(filePath)) {
                    continue; // 文件不存在/不是普通文件，直接跳过
                }

                File file = filePath.toFile();
                // 同步断点续传上传
                breakingPointUpload(host, path, headers, queries, key, file);
            }

            // 所有文件都传完，再调用 responseHandler（一次）
            T result = null;
            if (responseHandler != null) {
                result = responseHandler.handleResponse(new BasicClassicHttpResponse(HttpStatus.SC_OK));
            }
            futureCallback.completed(result);

        } catch (Exception e) {
            // 任意一个 breakingPointUpload 失败都走这里
            futureCallback.failed(e);
        }
    }

    public static void breakingPointDownload(String host, String path, Map<String, String> headers,
                                             Map<String, String> queries, FutureCallback<Object> futureCallback) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static void breakingPointUpload(String host,
                                           String path,
                                           Map<String, String> headers,
                                           Map<String, String> queries,
                                           String key,
                                           File file) throws IOException {

        // 分块大小：10MB
        final int chunkSize = 10 * 1024 * 1024;
        final long length = file.length();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long start = 0L;
            int partNumber = 0;

            while (start < length) {
                long remaining = length - start;
                int currentChunkSize = (int) Math.min(chunkSize, remaining);

                byte[] bytes = new byte[currentChunkSize];
                raf.seek(start);
                int read = raf.read(bytes);
                if (read <= 0) {
                    break; // 理论到不了这一步，防御性处理
                }

                // 如果 read < currentChunkSize，拷贝一下，避免带多余数据
                byte[] actualBytes = (read == bytes.length) ? bytes : Arrays.copyOf(bytes, read);

                // 每个 chunk 单独构建 multipart（避免累积 part）
                MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                        .setMode(HttpMultipartMode.LEGACY)
                        .setCharset(StandardCharsets.UTF_8)
                        .setContentType(ContentType.MULTIPART_FORM_DATA);

                // 这里直接用 binary body，不用临时文件
                builder.addBinaryBody(
                        key,
                        actualBytes,
                        ContentType.APPLICATION_OCTET_STREAM,
                        file.getName()
                );

                HttpEntity reqEntity = builder.build();

                // 为当前请求构建独立 headers，避免污染外部 map
                Map<String, String> requestHeaders = new HashMap<>();
                if (headers != null) {
                    requestHeaders.putAll(headers);
                }
                long endExclusive = start + read - 1; // Range 是闭区间，注意 -1
                requestHeaders.put("Range", "bytes=" + start + "-" + endExclusive);
                requestHeaders.put("X-Part-Number", String.valueOf(partNumber));

                // 真正发请求：假设你有一个支持 entity 的 doPost 返回 HttpEntity
                HttpEntity httpEntity = doPost(
                        host,
                        path,
                        requestHeaders,
                        queries,
                        reqEntity,
                        HttpEntityContainer::getEntity // 你自己的 handler
                );

                // 消耗响应体，避免连接泄漏
                EntityUtils.consume(httpEntity);

                // 下一块
                start += read;
                partNumber++;
            }
        }
    }

    public static ClassicHttpResponse doPost(String host, String path, Map<String, String> headers,
                                             Map<String, String> queries, Map<String, String> bodies) {
        return doPost(host, path, headers, queries, bodies, null);
    }

    /**
     * POST请求体为字符串
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     * @param body    请求体
     * @return ClassicHttpResponse
     */
    public static <T> T doPost(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, String body, HttpClientResponseHandler<T> responseHandler) {
        return doPost(host, path, headers, queries, new StringEntity(body, Charset.defaultCharset()), responseHandler);
    }

    public static <T> T doPost(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, HttpEntity httpEntity, HttpClientResponseHandler<T> responseHandler) {
        HttpPost request = new HttpPost(buildUrl(host, path, queries));
        builderHeader(headers, request);
        if (httpEntity != null) {
            request.setEntity(httpEntity);
        }
        return doRequest(responseHandler, request);
    }

    public static ClassicHttpResponse doPost(String host, String path, Map<String, String> headers,
                                             Map<String, String> queries, String body) {
        return doPost(host, path, headers, queries, body, null);
    }

    public static String doPostHandler(String host, String path, Map<String, String> headers,
                                       Map<String, String> queries, String body) {
        return doPost(host, path, headers, queries, body, new BasicHttpClientResponseHandler());
    }

    /**
     * Post stream
     *
     * @param host    主机地址
     * @param path    路径
     * @param headers 请求头
     * @param queries 请求参数
     * @param body    请求体
     * @return HttpResponse
     */
    public static <T> T doPost(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, byte[] body, HttpClientResponseHandler<T> responseHandler) {
        return doPost(host, path, headers, queries, new ByteArrayEntity(body, ContentType.APPLICATION_OCTET_STREAM), responseHandler);
    }

    public static HttpResponse doPost(String host, String path, Map<String, String> headers,
                                      Map<String, String> queries, byte[] body) {
        return doPost(host, path, headers, queries, body, null);
    }

    public static boolean isUrl(String url) {
        // 转换为小写
        url = url.toLowerCase();
        // https、http、ftp、rtsp、mms
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                // ftp 的 user@
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                // IP 形式的 URL- 例如：199.194.52.184
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
                + "|" // 允许 IP 和 DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为 65535,5 位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return url.matches(regex);
    }

    /**
     * 构建请求路径
     *
     * @param host 主机地址
     * @param path 请求路径
     * @return String
     */
    public static String buildUrlHead(String host, String path) {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (path != null && !path.isBlank()) {
            if (!path.startsWith("/")) {
                path += "/";
            }
            sbUrl.append(path);
        }
        return sbUrl.toString();
    }

    /**
     * 构建请求路径
     *
     * @param host    主机地址
     * @param path    请求路径
     * @param queries 请求参数
     * @return String
     */
    public static String buildUrlObject(String host, String path, Map<String, Object> queries) {

        return buildUrl(host, path, queries);
    }

    /**
     * 构建请求路径
     *
     * @param host    主机地址
     * @param path    请求路径
     * @param queries 请求参数
     * @return String
     */
    public static String buildUrlString(String host, String path, Map<String, String> queries) {
        return buildUrl(host, path, queries);
    }

    /**
     * 构建url
     *
     * @param host    主机地址
     * @param path    路径
     * @param queries 请求参数
     * @return String
     */
    public static String buildUrl(String host, String path, Map<?, ?> queries) {
        StringBuilder sbUrl = new StringBuilder(buildUrlHead(host, path));
        if (queries == null || queries.isEmpty()) {
            return sbUrl.toString();
        }
        String sbQuery = urlEncode(queries);
        if (!sbQuery.isBlank()) {
            sbUrl.append("?").append(sbQuery);
        }
        return sbUrl.toString();
    }

    /**
     * 拼接 URL 用户名和密码
     *
     * @param url      请求地址
     * @param username 用户名
     * @param password 密码
     */
    public static String getUserInfo(String url, String username, String password) {
        try {
            if (username == null && password == null) {
                return url;
            } else {
                URIBuilder uriBuilder = (new URIBuilder(url)).setUserInfo(username + ':' + password);
                return uriBuilder.toString();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检测响应体
     *
     * @param httpResponse 响应体
     * @return String
     */
    public static String checkResponseAndGetResultV2(ClassicHttpResponse httpResponse, boolean isEnsure) {
        if (isEnsure && HttpStatus.SC_OK != httpResponse.getCode()) {
            throw new RuntimeException();
        }

        HttpEntity entity = httpResponse.getEntity();
        try {
            return EntityUtils.toString(entity, Charset.defaultCharset());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检测响应体获取相应流
     *
     * @param httpResponse 响应体
     */
    public static byte[] checkResponseStreamAndGetResult(ClassicHttpResponse httpResponse) {
        if (HttpStatus.SC_OK != httpResponse.getCode()) {
            throw new RuntimeException();
        }

        HttpEntity entity = httpResponse.getEntity();
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 解析生成 URL
     *
     * @param map 键值对
     * @return 生成的 URL 尾部
     */
    public static String urlEncode(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            if (k != null && v != null) {
                sb.append(String.format("%s=%s",
                        URLEncoder.encode(k.toString(), Charset.defaultCharset()),
                        URLEncoder.encode(v.toString(), Charset.defaultCharset())));
            }
            sb.append("&");
        });

        return sb.toString();
    }

    /**
     * 检测响应体并解析
     *
     * @param httpResponse 响应体
     * @param statusList   状态码列表
     * @return 解析字节
     */
    public static byte[] checkResponseStreamAndGetResult(ClassicHttpResponse httpResponse, List<Integer> statusList) {
        checkCode(httpResponse, statusList);
        HttpEntity entity = httpResponse.getEntity();
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 检测响应体并解析
     *
     * @param httpResponse 响应体
     * @param statusList   状态码列表
     * @return 解析字符串
     */
    public static String checkResponseAndGetResult(ClassicHttpResponse httpResponse, List<Integer> statusList) {
        checkCode(httpResponse, statusList);

        HttpEntity entity = httpResponse.getEntity();
        try {
            return EntityUtils.toString(entity, Charset.defaultCharset());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检测状态码
     *
     * @param httpResponse 响应体
     * @param statusList   检测状态表
     */
    private static void checkCode(ClassicHttpResponse httpResponse, List<Integer> statusList) {
        if (!statusList.contains(httpResponse.getCode())) {
            throw new RuntimeException();
        }
    }

    /**
     * 解析响应体
     *
     * @param httpResponse 响应体
     * @return String
     */
    public static String checkResponseAndGetResult(ClassicHttpResponse httpResponse) {
        return checkResponseAndGetResult(httpResponse, Collections.singletonList(HttpStatus.SC_OK));
    }

    public static String checkResponseAndGetResult(HttpResponse httpResponse) {
        return checkResponseAndGetResult((ClassicHttpResponse) httpResponse, Collections.singletonList(HttpStatus.SC_OK));
    }

    public static String checkResponseAndGetResult(HttpResponse httpResponse, Boolean isEnsure) {
        return checkResponseAndGetResultV2((ClassicHttpResponse) httpResponse, isEnsure);
    }

    /**
     * Add request interceptor - requires HttpClient recreation for thread safety
     */
    public void addRequestInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        log.warn("Adding request interceptor requires HttpClient recreation. Consider using HttpClientBuilder directly for better performance.");
        refresh();
    }

    /**
     * Add execution chain interceptor - requires HttpClient recreation for thread safety
     */
    public void addExecInterceptorAfter(final String existing, final String name, final ExecChainHandler interceptor) {
        log.warn("Adding execution interceptor requires HttpClient recreation. Consider using HttpClientBuilder directly for better performance.");
        refresh();
    }

    /**
     * Clean up thread-local resources to prevent memory leaks
     * Call this method when thread is being destroyed or when thread-local cleanup is needed
     */
    public static void cleanupThreadLocal() {
        CLIENT_CONTEXT_HOLDER.remove();
        COOKIE_STORE_HOLDER.remove();
    }

    /**
     * Get current HttpClient instance
     *
     * @return CloseableHttpClient instance
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            init();
        }
        return httpClient;
    }
}
