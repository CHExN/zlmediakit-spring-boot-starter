package io.github.lunasaw.zlm.hook.param;

/**
 * ZLM Hook 回调参数 - 服务器退出 (on_server_exited)
 *
 * @param mediaServerId 服务器 ID
 * @author CHEaN
 */
public record HookParamForOnServerExited(String mediaServerId) implements HookParam {
}
