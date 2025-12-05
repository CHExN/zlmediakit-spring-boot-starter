package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 多路复用 RTP 服务器参数。
 *
 * @param port     绑定端口，0 表示随机
 * @param tcpMode  TCP 模式，0 关闭，1 开启
 * @param vhost    虚拟主机
 * @param app      应用名
 * @param streamId 流 ID
 * @param onlyTrack 是否为单 track，0：不设置，1：音频，2：视频
 * @param localIp  指定绑定的本地 IP
 */
@Builder
public record RtpServerMultiplexItem(
        @JsonProperty("port") Integer port,
        @JsonProperty("tcp_mode") Integer tcpMode,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream_id") String streamId,
        @JsonProperty("only_track") Integer onlyTrack,
        @JsonProperty("local_ip") String localIp
) {

    public RtpServerMultiplexItem {
        Assert.notNull(port, "port must not be null");
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(streamId, "streamId must not be null");
    }
}
