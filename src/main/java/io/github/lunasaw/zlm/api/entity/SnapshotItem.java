package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 截图任务参数
 *
 * @param url        需要截图的 URL，可以是本机的，也可以是远程主机的
 * @param timeoutSec 截图失败超时时间（秒）防止 FFmpeg 一直等待截图
 * @param expireSec  截图缓存过期时间（秒）该时间内产生的截图都会作为缓存返回
 */
@Builder
public record SnapshotItem(
        @JsonProperty("url") String url,
        @JsonProperty("timeout_sec") Integer timeoutSec,
        @JsonProperty("expire_sec") Integer expireSec
) {

    public SnapshotItem {
        Assert.notNull(url, "url must not be null");
        Assert.notNull(timeoutSec, "timeout_sec must not be null");
        Assert.notNull(expireSec, "expire_sec must not be null");
    }
}
