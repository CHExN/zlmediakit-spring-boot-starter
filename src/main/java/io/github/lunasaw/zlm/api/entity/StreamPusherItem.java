package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 推流代理配置。
 *
 * @param schema      推流协议，支持 rtsp、rtmp、srt
 * @param vHost       虚拟主机，例如 __defaultVhost__
 * @param app         应用名
 * @param stream      流 ID
 * @param dstUrl      推流地址，需要与 schema 一致
 * @param rtpType     RTSP 推流方式，0：tcp，1：udp
 * @param timeoutSec  推流超时时间（秒）
 * @param retryCount  推流重试次数；不传或 <=0 表示无限重试
 * @param latency     srt 延时，毫秒
 * @param passphrase  srt 推流密码
 */
@Builder
public record StreamPusherItem(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vHost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("rtp_type") Integer rtpType,
        @JsonProperty("timeout_sec") Integer timeoutSec,
        @JsonProperty("retry_count") Integer retryCount,
        @JsonProperty("latency") String latency,
        @JsonProperty("passphrase") String passphrase
) {

    public StreamPusherItem {
        Assert.notNull(schema, "schema must not be null");
        Assert.notNull(vHost, "vHost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(stream, "stream must not be null");
        Assert.notNull(dstUrl, "dstUrl must not be null");
    }
}
