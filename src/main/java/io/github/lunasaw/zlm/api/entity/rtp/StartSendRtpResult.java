package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 启动 RTP 推流结果。
 *
 * @param code      状态码
 * @param localPort 本地端口
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StartSendRtpResult(
        @JsonProperty("code") String code,
        @JsonProperty("local_port") String localPort
) {
}
