package io.github.lunasaw.zlm.hook.entity;

import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Stream Hook 相关回调参数接口
 * <p>
 * 所有与流相关的 Hook 参数类均应实现此接口，以确保包含必要地流信息
 *
 * @author CHEaN
 */
public interface HookParamForStream extends HookParam {
    /**
     * 服务器 ID
     */
    String mediaServerId();
    /**
     * 流应用名
     */
    String app();
    /**
     * 播放或推流的媒体源类型
     */
    Schema schema();
    /**
     * 流 ID
     */
    String stream();
    /**
     * 流虚拟主机
     */
    String vhost();
}
