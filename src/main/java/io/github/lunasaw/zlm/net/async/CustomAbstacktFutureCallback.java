package io.github.lunasaw.zlm.net.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * @author luna
 */
@Slf4j
public abstract class CustomAbstacktFutureCallback<T> implements FutureCallback<T> {

    @Override
    public void completed(T result) {
        log.info("completed::result = {}", result);
    }

    @Override
    public void failed(Exception ex) {
        // 请求失败处理
        log.error("failed::", ex);
    }

    @Override
    public void cancelled() {
    }
}
