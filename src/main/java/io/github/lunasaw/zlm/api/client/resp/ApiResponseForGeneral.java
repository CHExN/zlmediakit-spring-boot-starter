package io.github.lunasaw.zlm.api.client.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM API 通用响应封装，兼容 code 同级的简单字段（online/status/result）。
 *
 * @param code   状态码
 * @param msg    提示信息
 * @param data   业务数据
 * @param result 通用布尔标记（如 startRecord/stopRecord）
 * @param status 录制状态标记（isRecording）
 * @param online 在线标记（isMediaOnline）
 * @param <T>    业务数据类型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponseForGeneral<T>(
        @JsonProperty("code") Integer code,
        @JsonProperty("msg") String msg,
        @JsonProperty("data") T data,
        @JsonProperty("result") Boolean result,
        @JsonProperty("status") Boolean status,
        @JsonProperty("online") Boolean online) implements ApiResponse {
}
