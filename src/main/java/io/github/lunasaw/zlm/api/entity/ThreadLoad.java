package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * epoll/select 线程的负载及延迟
 *
 * @param delay   线程延时
 * @param fdCount 线程句柄数
 * @param load    线程负载 0~100
 * @param name    线程名称
 */
public record ThreadLoad(
        @JsonProperty("delay") Integer delay,
        @JsonProperty("fd_count") Integer fdCount,
        @JsonProperty("load") Integer load,
        @JsonProperty("name") String name
) {
}
