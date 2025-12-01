package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 创建 RTP 服务器结果。
 *
 * @param port 端口
 * @param code 状态码
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OpenRtpServerResult(
        @JsonProperty("port") String port,
        @JsonProperty("code") String code
) {
}
