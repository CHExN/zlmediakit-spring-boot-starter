package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.lunasaw.zlm.api.entity.ServerConfig;

import java.util.Map;

/**
 * ZLM Hook 回调参数 - 服务器启动完成 (on_server_started)
 * <p>
 * 这个 hook 的 body 就是 config.ini 的扁平 JSON，
 * 用 DELEGATING 模式直接拿整个对象做 Map。
 * @author CHEaN
 */
public record HookParamForOnServerStarted(
        Map<String, String> config
) implements HookParam {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public HookParamForOnServerStarted {}

    @Override
    public String mediaServerId() {
        return config.getOrDefault(ServerConfig.Keys.GENERAL.MEDIA_SERVER_ID, "your_server_id");
    }

}
