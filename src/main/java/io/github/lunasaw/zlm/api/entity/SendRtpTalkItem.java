package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 双向对讲推流参数。
 *
 * @param vhost                虚拟主机
 * @param app                  应用名
 * @param stream               本地流 ID
 * @param ssrc                 推流 SSRC
 * @param recvStreamId         对端推流的流 ID
 * @param fromMp4              是否从本地 MP4 推送
 * @param type                 负载类型：0 ES，1 PS，2 TS
 * @param pt                   RTP payload type
 * @param onlyAudio            仅发送音频
 * @param enableOriginRecvLimit 限制源端收流速度
 */
@Builder
public record SendRtpTalkItem(
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("ssrc") Integer ssrc,
        @JsonProperty("recv_stream_id") String recvStreamId,
        @JsonProperty("from_mp4") Boolean fromMp4,
        @JsonProperty("type") Integer type,
        @JsonProperty("pt") Integer pt,
        @JsonProperty("only_audio") Boolean onlyAudio,
        @JsonProperty("enable_origin_recv_limit") Boolean enableOriginRecvLimit
) {

    public SendRtpTalkItem {
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(stream, "stream must not be null");
        Assert.notNull(ssrc, "ssrc must not be null");
    }
}
