package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取 RTP 代理时的某路 SSRC 信息。
 *
 * @param code     状态码
 * @param exist    会话是否存在
 * @param peerIp   推流客户端 IP
 * @param peerPort 客户端端口
 * @param localIp  本地监听 IP
 * @param localPort 本地端口
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RtpInfoResult(
        @JsonProperty("code") Integer code,
        @JsonProperty("exist") Boolean exist,
        @JsonProperty("peer_ip") String peerIp,
        @JsonProperty("peer_port") Integer peerPort,
        @JsonProperty("local_ip") String localIp,
        @JsonProperty("local_port") Integer localPort
) {
}
