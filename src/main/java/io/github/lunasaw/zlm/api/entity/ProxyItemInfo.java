package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 代理任务信息（拉流或推流）。
 *
 * @param bytesSpeed       当前速率
 * @param key              任务唯一 key（列表时返回）
 * @param liveSecs         运行时长（秒）
 * @param rePullCount      重拉次数（拉流）
 * @param rePublishCount   重推次数（推流）
 * @param src              源标识
 * @param status           任务状态
 * @param totalBytes       累计字节
 * @param totalReaderCount 累计观看人数
 * @param url              源/目标 URL
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProxyItemInfo(
        @JsonProperty("bytesSpeed") Long bytesSpeed,
        @JsonProperty("key") String key,
        @JsonProperty("liveSecs") Integer liveSecs,
        @JsonProperty("rePullCount") Integer rePullCount,
        @JsonProperty("rePublishCount") Integer rePublishCount,
        @JsonProperty("src") ProxySource src,
        @JsonProperty("status") Integer status,
        @JsonProperty("totalBytes") Long totalBytes,
        @JsonProperty("totalReaderCount") Integer totalReaderCount,
        @JsonProperty("url") String url
) {
}
