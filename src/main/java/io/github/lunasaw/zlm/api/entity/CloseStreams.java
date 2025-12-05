package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 关闭流结果
 */
@Data
@Accessors(fluent = true)
public class CloseStreams {

    /**
     * 命中关闭的流数量
     */
    @JsonProperty("count_hit")
    private Integer countHit;
    /**
     * 实际关闭的流数量，可能小于 countHit
     */
    @JsonProperty("count_closed")
    private Integer countClosed;

}
