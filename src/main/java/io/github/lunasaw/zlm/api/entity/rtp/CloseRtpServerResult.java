package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 关闭 RTP 服务器结果。
 *
 * @param hit  是否找到记录并关闭
 * @param code 状态码
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CloseRtpServerResult(
        @JsonProperty("hit") String hit,
        @JsonProperty("code") String code
) {
}
