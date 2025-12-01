package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.util.JsonUtils;

import java.util.Map;

/**
 * 推流代理配置。
 *
 * @param vHost      虚拟主机
 * @param schema     协议 rtsp/rtmp
 * @param app        应用名
 * @param stream     流 ID
 * @param dstUrl     目标推流地址
 * @param retryCount 推流失败重试次数
 * @param rtpType    推流方式，0：tcp，1：udp
 * @param timeoutSec 推流超时时间（秒）
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StreamPusherItem(
        @JsonProperty("vhost") String vHost,
        @JsonProperty("schema") String schema,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("retry_count") Integer retryCount,
        @JsonProperty("rtp_type") Integer rtpType,
        @JsonProperty("timeout_sec") Integer timeoutSec
) {

    public Map<String, String> toMap() {
        return JsonUtils.toStringMap(this);
    }
}
