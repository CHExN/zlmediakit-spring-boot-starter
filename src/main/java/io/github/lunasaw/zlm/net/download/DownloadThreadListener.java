package io.github.lunasaw.zlm.net.download;

import java.util.EventListener;

public interface DownloadThreadListener extends EventListener {
    void afterPerDown(DownloadThreadEvent event);

    void downCompleted(DownloadThreadEvent event);

}