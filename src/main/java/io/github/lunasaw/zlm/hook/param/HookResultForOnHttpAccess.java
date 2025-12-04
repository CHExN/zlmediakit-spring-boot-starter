package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调结果 - HTTP 访问
 *
 * @param code   错误代码，0 表示成功
 * @param err    不允许访问的错误提示，允许访问请置空
 * @param path   该客户端能访问或被禁止的顶端目录，如果为空字符串，则表述为当前目录
 * @param second 本次授权结果的有效期（秒）
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookResultForOnHttpAccess(
        @JsonProperty("code") int code,
        @JsonProperty("err") String err,
        @JsonProperty("path") String path,
        @JsonProperty("second") int second
) {

    public static HookResultForOnHttpAccess success() {
        return new HookResultForOnHttpAccess(0, "", "", 3600);
    }
}
