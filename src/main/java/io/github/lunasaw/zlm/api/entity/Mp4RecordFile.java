package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * MP4 录像文件查询结果。
 *
 * @param paths    录像文件路径列表
 * @param rootPath 根路径
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Mp4RecordFile(
        @JsonProperty("paths") List<String> paths,
        @JsonProperty("rootPath") String rootPath
) {
}
