package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 流标识信息。
 *
 * @param key 流的唯一标识
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StreamKey(@JsonProperty("key") String key) {

    /**
     * 删除操作返回的标识。
     *
     * @param flag 删除标识
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record StringDelFlag(@JsonProperty("flag") String flag) {
    }
}
