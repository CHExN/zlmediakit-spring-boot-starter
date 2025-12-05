package io.github.lunasaw.zlm.api.entity;

import lombok.Builder;

/**
 * ZLM API 参数 - 流过滤
 *
 * @param schema 流协议
 * @param vhost  流虚拟主机
 * @param app    流应用名
 * @param stream 流 ID
 * @author CHEaN
 */
@Builder
public record StreamFilter(
        String schema,
        String vhost,
        String app,
        String stream
) {
}
