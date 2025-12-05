package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 代理源标识。
 *
 * @param app   应用名
 * @param params 额外参数
 * @param stream 流 ID
 * @param vhost 虚拟主机
 */
public record ProxySource(
        @JsonProperty("app") String app,
        @JsonProperty("params") String params,
        @JsonProperty("stream") String stream,
        @JsonProperty("vhost") String vhost
) {
}
