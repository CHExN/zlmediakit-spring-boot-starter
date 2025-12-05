package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除操作返回的标识。
 *
 * @param flag 删除标识
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeleteFlag(@JsonProperty("flag") Boolean flag) {
}
