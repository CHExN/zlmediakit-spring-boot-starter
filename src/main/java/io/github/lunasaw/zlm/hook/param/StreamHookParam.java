package io.github.lunasaw.zlm.hook.param;

import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Stream Hook 相关回调参数接口
 *
 * @author CHEaN
 */
public interface StreamHookParam {
    String mediaServerId();
    String app();
    Schema schema();
    String stream();
    String vhost();
}
