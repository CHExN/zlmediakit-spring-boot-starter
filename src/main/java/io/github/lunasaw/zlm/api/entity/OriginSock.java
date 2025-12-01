package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 源套接字信息。
 *
 * @param identifier 连接标识
 * @param localIp    本地 IP
 * @param localPort  本地端口
 * @param peerIp     对端 IP
 * @param peerPort   对端端口
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OriginSock(
        @JsonProperty("identifier") String identifier,
        @JsonProperty("local_ip") String localIp,
        @JsonProperty("local_port") Integer localPort,
        @JsonProperty("peer_ip") String peerIp,
        @JsonProperty("peer_port") Integer peerPort
) {
}
