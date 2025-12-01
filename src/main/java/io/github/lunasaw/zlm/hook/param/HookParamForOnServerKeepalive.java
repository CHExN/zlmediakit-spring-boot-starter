package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - 服务器心跳 (on_server_keepalive)
 *
 * @param mediaServerId 服务器 ID
 * @param data          心跳数据
 * @author CHEaN
 */
public record HookParamForOnServerKeepalive(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("data") ServerKeepaliveData data
) { }