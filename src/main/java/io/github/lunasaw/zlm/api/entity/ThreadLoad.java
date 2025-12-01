package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * epoll/select 线程的负载及延迟。
 *
 * @param delay 线程延时
 * @param load  线程负载 0~100
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ThreadLoad(
        @JsonProperty("delay") String delay,
        @JsonProperty("load") String load
) {
}
