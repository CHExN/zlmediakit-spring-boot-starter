package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

/**
 * 关闭 RTP 推流请求时，传递的参数
 *
 * @param vhost  虚拟主机
 * @param app    应用名
 * @param stream 流 ID
 * @param ssrc   RTP 推流的 SSRC
 */
public record CloseSendRtpItem(
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("ssrc") String ssrc
) {

    public CloseSendRtpItem {
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(stream, "stream must not be null");
    }

}
