package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 主动连接 RTP 服务器参数。
 *
 * @param dstUrl   目标地址
 * @param dstPort  目标端口
 * @param vhost    虚拟主机
 * @param app      应用名
 * @param streamId 绑定的流 ID
 */
@Builder
public record ConnectRtpServerItem(
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("dst_port") Integer dstPort,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream_id") String streamId
) {

    public ConnectRtpServerItem {
        Assert.notNull(dstUrl, "dstUrl must not be null");
        Assert.notNull(dstPort, "dstPort must not be null");
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(streamId, "streamId must not be null");
    }
}
