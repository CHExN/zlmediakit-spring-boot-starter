package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 点播 MP4 文件结果。
 *
 * @param durationMs 时长（毫秒）
 */
public record Mp4LoadInfo(
        @JsonProperty("duration_ms") Long durationMs
) {
}
