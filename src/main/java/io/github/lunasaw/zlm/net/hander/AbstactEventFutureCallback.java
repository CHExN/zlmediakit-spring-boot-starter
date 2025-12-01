package io.github.lunasaw.zlm.net.hander;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * @author luna
 */
@Slf4j
public abstract class AbstactEventFutureCallback<T, E> implements FutureCallback<T> {

    public void onEvent(E result) {
        log.info("onEvent::result = {}", result);
    }

    @Override
    public void completed(T result) {
        log.info("completed::result = {}", result);
    }

    @Override
    public void failed(Exception ex) {

    }

    @Override
    public void cancelled() {

    }
}
