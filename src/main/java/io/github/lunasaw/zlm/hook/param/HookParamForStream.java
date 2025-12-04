package io.github.lunasaw.zlm.hook.param;

import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Stream Hook 相关回调参数接口
 * <p>
 * 所有与流相关的 Hook 参数类均应实现此接口，以确保包含必要地流信息
 *
 * @author CHEaN
 */
public interface HookParamForStream {
    String mediaServerId();
    String app();
    Schema schema();
    String stream();
    String vhost();
}
