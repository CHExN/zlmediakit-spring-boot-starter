package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Version(

        @JsonProperty("buildTime") String buildTime,
        @JsonProperty("branchName") String branchName,
        @JsonProperty("commitHash") String commitHash

) { }