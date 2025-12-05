package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 事件录像结果。
 *
 * @param path 录像文件路径
 */
public record StartRecordTaskResult(
        @JsonProperty("path") String path
) {
}
