package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - RTP 服务器超时 (on_rtp_server_timeout)
 *
 * @param mediaServerId 服务器 ID
 * @param localPort     本地端口
 * @param reUsePort     是否重用端口
 * @param ssrc          SSRC
 * @param streamId      流 ID
 * @param tcpMode       TCP 模式
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnRtpServerTimeout(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("local_port") int localPort,
        @JsonProperty("re_use_port") boolean reUsePort,
        @JsonProperty("ssrc") int ssrc,
        @JsonProperty("stream_id") String streamId,
        @JsonProperty("tcp_mode") int tcpMode
) implements HookParam {
}
