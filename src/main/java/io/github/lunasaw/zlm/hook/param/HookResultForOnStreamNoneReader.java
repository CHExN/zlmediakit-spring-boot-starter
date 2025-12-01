package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调结果 - 流无人观看
 *
 * @param code  返回码 (0: 成功，!0: 失败)
 * @param close 是否关闭无人观看的流 (true: 关闭，false: 不关闭)
 */
public record HookResultForOnStreamNoneReader(
        @JsonProperty("code") int code,
        @JsonProperty("close") boolean close
) {

    /**
     * 默认返回：关闭无人观看的流。
     */
    public static HookResultForOnStreamNoneReader success() {
        return new HookResultForOnStreamNoneReader(0, true);
    }

}
