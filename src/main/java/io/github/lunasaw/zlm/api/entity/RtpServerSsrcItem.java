package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * RTP 服务器 SSRC 更新参数。
 *
 * @param vhost    虚拟主机
 * @param app      应用名
 * @param streamId 流 ID
 * @param ssrc     十进制 SSRC
 */
@Builder
public record RtpServerSsrcItem(
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream_id") String streamId,
        @JsonProperty("ssrc") Integer ssrc
) {

    public RtpServerSsrcItem {
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(streamId, "streamId must not be null");
        Assert.notNull(ssrc, "ssrc must not be null");
    }
}
