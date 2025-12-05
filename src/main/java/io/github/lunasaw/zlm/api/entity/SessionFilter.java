package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * TCP 会话过滤条件。
 *
 * @param localPort 筛选本机端口
 * @param peerIp    筛选对端 IP
 */
@Builder
public record SessionFilter(
        @JsonProperty("local_port") String localPort,
        @JsonProperty("peer_ip") String peerIp
) {
}
