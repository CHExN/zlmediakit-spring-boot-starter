package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * ZLM Hook 回调结果 - RTSP 鉴权
 *
 * @param code      错误代码，0 表示成功
 * @param msg       错误提示信息
 * @param encrypted 是否加密
 * @param passwd    用户密码明文或摘要 (md5(username:realm:password))
 */
@Builder
public record HookResultForOnRtspAuth(

        @JsonProperty("code") int code,
        @JsonProperty("msg") String msg,
        @JsonProperty("encrypted") boolean encrypted,
        @JsonProperty("passwd") String passwd
) {

    public static HookResultForOnRtspAuth success(boolean encrypted, String passwd) {
        return new HookResultForOnRtspAuth(0, "", encrypted, passwd);
    }

    public static HookResultForOnRtspAuth fail() {
        return new HookResultForOnRtspAuth(1, "", false, "");
    }

    public static HookResultForOnRtspAuth fail(String msg) {
        return new HookResultForOnRtspAuth(1, msg, false, "");
    }

    public static HookResultForOnRtspAuth fail(int code, String msg) {
        return new HookResultForOnRtspAuth(code, msg, false, "");
    }

}
