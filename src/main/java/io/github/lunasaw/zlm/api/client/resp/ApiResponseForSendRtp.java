package io.github.lunasaw.zlm.api.client.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM API 结果 - 发送 RTP 结果
 *
 * @param code      状态码
 * @param localPort 使用的本地端口号
 */
public record ApiResponseForSendRtp(
        @JsonProperty("code") Integer code,
        @JsonProperty("msg") String msg,
        @JsonProperty("local_port") Integer localPort) implements ApiResponse {
}
