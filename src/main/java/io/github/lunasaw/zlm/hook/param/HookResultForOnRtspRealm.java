package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调结果 - RTSP 鉴权 Realm
 *
 * @param code  错误代码，固定返回 0
 * @param realm RTSP 鉴权 Realm，空字符串表示不需要鉴权
 */
public record HookResultForOnRtspRealm(
        @JsonProperty("code") int code,
        @JsonProperty("realm") String realm
) {

    public HookResultForOnRtspRealm {
        code = 0;
    }

    public HookResultForOnRtspRealm(String realm) {
        this(0, realm);
    }

    public static HookResultForOnRtspRealm noAuthRequired() {
        return new HookResultForOnRtspRealm("");
    }

    public static HookResultForOnRtspRealm realm(String realm) {
        return new HookResultForOnRtspRealm(realm);
    }

}
