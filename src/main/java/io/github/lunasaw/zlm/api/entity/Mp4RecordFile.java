package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 录像文件列表信息。
 *
 * @param paths    录像日期或文件列表
 * @param rootPath 根目录
 */
public record Mp4RecordFile(
        @JsonProperty("paths") List<String> paths,
        @JsonProperty("rootPath") String rootPath
) {
}
