package io.github.lunasaw.zlm.hook.param;

/**
 * ZLM Hook 参数接口
 * <p>
 * 所有 Hook 参数类均应实现此接口，以确保包含媒体服务器 ID 信息
 *
 * @author CHEaN
 */
public interface HookParam {
    String mediaServerId();
}
