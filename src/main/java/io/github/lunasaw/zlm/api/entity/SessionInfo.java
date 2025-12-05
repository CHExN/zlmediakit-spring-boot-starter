package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TCP 会话信息。
 *
 * @param id        会话 ID
 * @param localIp   本机 IP
 * @param localPort 本机端口
 * @param peerIp    对端 IP
 * @param peerPort  对端端口
 * @param typeId    会话类型标识
 */
public record SessionInfo(
        @JsonProperty("id") String id,
        @JsonProperty("local_ip") String localIp,
        @JsonProperty("local_port") Integer localPort,
        @JsonProperty("peer_ip") String peerIp,
        @JsonProperty("peer_port") Integer peerPort,
        @JsonProperty("typeid") String typeId
) {
}
