package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 流是否在线的查询结果。
 *
 * @param code   状态码
 * @param data   附带的数据
 * @param msg    提示信息
 * @param result 结果状态
 * @param online 是否在线
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaOnlineStatus(
        @JsonProperty("code") Integer code,
        @JsonProperty("data") String data,
        @JsonProperty("msg") String msg,
        @JsonProperty("result") String result,
        @JsonProperty("online") Boolean online
) {

    public MediaOnlineStatus {
        msg = msg == null ? "success" : msg;
        result = result == null ? "success" : result;
    }
}
