package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - RTP 发送停止 (on_send_rtp_stopped)
 *
 * @param mediaServerId 服务器ID
 * @param app           流应用名
 * @param stream        流 ID
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnSendRtpStopped(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream
) implements HookParam {
}
