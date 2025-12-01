package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除录像目录的结果。
 *
 * @param code   状态码
 * @param data   响应数据
 * @param msg    提示信息
 * @param result 结果状态
 * @param path   被删除的目录路径
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeleteRecordDirectory(
        @JsonProperty("code") Integer code,
        @JsonProperty("data") String data,
        @JsonProperty("msg") String msg,
        @JsonProperty("result") String result,
        @JsonProperty("path") String path
) {

    public DeleteRecordDirectory {
        msg = msg == null ? "success" : msg;
        result = result == null ? "success" : result;
    }
}
