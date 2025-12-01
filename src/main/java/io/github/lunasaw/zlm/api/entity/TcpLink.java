package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TCP 会话信息。
 *
 * @param id       链接唯一 id
 * @param localIp  本机网卡 IP
 * @param localPort 本机端口
 * @param peerIp   客户端 IP
 * @param peerPort 客户端端口
 * @param typeId   会话类型标识
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TcpLink(
        @JsonProperty("id") String id,
        @JsonProperty("local_ip") String localIp,
        @JsonProperty("local_port") Integer localPort,
        @JsonProperty("peer_ip") String peerIp,
        @JsonProperty("peer_port") Integer peerPort,
        @JsonProperty("typeid") String typeId
) {
}
