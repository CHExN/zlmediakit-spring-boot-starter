package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 通用的 ZLM 接口响应包装。
 *
 * @param code   返回码，0 表示成功
 * @param data   业务数据
 * @param msg    提示信息，默认 success
 * @param result 结果状态，默认 success
 * @param <T>    数据类型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServerResponse<T>(
        @JsonProperty("code") Integer code,
        @JsonProperty("data") T data,
        @JsonProperty("msg") String msg,
        @JsonProperty("result") String result
) {

    public ServerResponse {
        msg = msg == null ? "success" : msg;
        result = result == null ? "success" : result;
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<>(0, data, "success", "success");
    }
}