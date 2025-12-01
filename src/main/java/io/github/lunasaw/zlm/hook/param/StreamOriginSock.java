package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @param identifier 连接标识符
 * @param localIp    本机 IP
 * @param localPort  本机端口
 * @param peerIp     对端 IP
 * @param peerPort   对端端口
 */
public record StreamOriginSock(
        @JsonProperty("identifier") String identifier,
        @JsonProperty("local_ip") String localIp,
        @JsonProperty("local_port") int localPort,
        @JsonProperty("peer_ip") String peerIp,
        @JsonProperty("peer_port") int peerPort
) {
}
