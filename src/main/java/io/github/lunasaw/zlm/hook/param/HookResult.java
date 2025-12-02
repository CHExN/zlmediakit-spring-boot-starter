package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * ZLM Hook 回调结果 - 通用
 *
 * @param code 错误代码，0 表示成功
 * @param msg  错误提示信息
 */
@Builder
public record HookResult(
        @JsonProperty("code") int code,
        @JsonProperty("msg") String msg
) {

    /**
     * 成功结果
     */
    public static HookResult success() {
        return new HookResult(0, "success");
    }

    public static HookResult fail(String msg) {
        return new HookResult(1, msg);
    }

    public static HookResult fail(int code, String msg) {
        return new HookResult(code, msg);
    }

}
