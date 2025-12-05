package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM 版本信息
 *
 * @param buildTime  构建时间
 * @param branchName 分支名称
 * @param commitHash commit id（提交哈希值）
 */
public record Version(
        @JsonProperty("buildTime") String buildTime,
        @JsonProperty("branchName") String branchName,
        @JsonProperty("commitHash") String commitHash
) {
}