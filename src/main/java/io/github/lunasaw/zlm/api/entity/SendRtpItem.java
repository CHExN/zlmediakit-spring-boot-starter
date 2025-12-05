package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 作为 GB28181 客户端启动 active 模式 ps-rtp 推流请求时，传递的推流参数
 *
 * @param vhost                 虚拟主机
 * @param app                   应用名
 * @param stream                流 ID
 * @param ssrcMultiSend         是否支持同 ssrc 推流到多个上级服务器，默认 false
 * @param ssrc                  RTP 推流的 SSRC
 * @param dstUrl                目标 IP 或域名
 * @param dstPort               目标端口
 * @param isUdp                 是否使用 UDP，1:udp active模式, 0:tcp active模式
 * @param srcPort               本机端口，0 为随机
 * @param pt                    RTP pt（uint8），默认 96
 * @param usePs                 负载类型，1 表示 ps，0 表示 es，默认 1
 * @param onlyAudio             usePs 为 0 时有效，为 1 时发送音频，为 0 时发送视频，默认 0
 * @param recvStreamId          发送 rtp 同时接收，一般用于双向语言对讲, 如果不为空，说明开启接收，值为接收流的 ID
 * @param closeDelayMs          等待 TCP 连接超时时间，单位毫秒，默认 5000 毫秒
 * @param enableOriginRecvLimit 转发 RTP (TCP 模式) 时，如果发送不出去，是否限制源端收流速度，此参数在多倍速 RTP 转发时作用较大
 */
@Builder
public record SendRtpItem(
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("ssrc_multi_send") String ssrcMultiSend,
        @JsonProperty("ssrc") Integer ssrc,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("dst_port") Integer dstPort,
        @JsonProperty("is_udp") Boolean isUdp,
        @JsonProperty("src_port") Integer srcPort,
        @JsonProperty("pt") Integer pt,
        @JsonProperty("use_ps") Integer usePs,
        @JsonProperty("only_audio") Boolean onlyAudio,
        @JsonProperty("recv_stream_id") String recvStreamId,
        @JsonProperty("close_delay_ms") Integer closeDelayMs,
        @JsonProperty("enable_origin_recv_limit") Boolean enableOriginRecvLimit
) {

    public SendRtpItem {
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(stream, "stream must not be null");
        Assert.notNull(ssrc, "ssrc must not be null");
        Assert.notNull(dstUrl, "dst_url must not be null");
        Assert.notNull(dstPort, "dst_port must not be null");
        Assert.notNull(isUdp, "is_udp must not be null");
    }

}
