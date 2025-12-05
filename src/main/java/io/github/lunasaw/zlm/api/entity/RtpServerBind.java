package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

/**
 * RTP 服务器绑定信息
 *
 * @param port     接收端口
 * @param streamId 该端口绑定的流 ID
 */
public record RtpServerBind(
        @JsonProperty("port") Integer port,
        @JsonProperty("stream_id") String streamId
) {

    public RtpServerBind {
        Assert.notNull(port, "port must not be null");
        Assert.notNull(streamId, "stream_id must not be null");
    }

}
