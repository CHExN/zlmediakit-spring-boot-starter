package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调结果 - RTSP 鉴权
 *
 * @param code      错误代码，0表示成功
 * @param msg       错误提示信息
 * @param encrypted 是否加密
 * @param passwd    用户密码明文或摘要 (md5(username:realm:password))
 */
public record HookResultForOnRtspAuth(

        @JsonProperty("code") int code,
        @JsonProperty("msg") String msg,
        @JsonProperty("encrypted") boolean encrypted,
        @JsonProperty("passwd") String passwd
) {

    public static HookResultForOnRtspAuth success() {
        return new HookResultForOnRtspAuth(0, "", false, "");
    }

}
