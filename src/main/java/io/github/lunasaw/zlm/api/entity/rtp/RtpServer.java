package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RTP 服务器信息。
 *
 * @param port     端口
 * @param streamId 绑定的流 ID
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RtpServer(
        @JsonProperty("port") String port,
        @JsonProperty("streamId") String streamId
) {
}
