package io.github.lunasaw.zlm.net.hander;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingEventSourceListener<T, E> extends AbstactEventFutureCallback<T, E> {

    @Override
    public void onEvent(E result) {
        log.info("onEvent::result = {}", result);
    }

    @Override
    public void completed(T result) {}

    @Override
    public void failed(Exception ex) {
        super.failed(ex);
    }

    @Override
    public void cancelled() {
        super.cancelled();
    }
}
