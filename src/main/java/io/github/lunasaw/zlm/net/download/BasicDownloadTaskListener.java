package io.github.lunasaw.zlm.net.download;

import lombok.extern.slf4j.Slf4j;

/**
 * @author luna
 */
@Slf4j
public class BasicDownloadTaskListener implements DownloadTaskListener{
    @Override
    public void downloadCompleted() {
        log.info("downloadCompleted::");
    }
}
