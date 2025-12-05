package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 流标识信息。
 *
 * @param key 流的唯一标识
 */
public record StreamKey(@JsonProperty("key") String key) {
}
