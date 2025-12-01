package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Protocol;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - 推流鉴权事件 (on_publish)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param id            TCP 链接唯一 ID
 * @param ip            推流器 IP
 * @param params        推流 URL 参数
 * @param port          推流器端口号（unsigned short → int）
 * @param schema        推流的媒体源类型（rtsp / rtmp / srt）
 * @param protocol      传输协议（rtsp/rtmp/rtsps/rtmps/rtc/srt/rtp/tcp/udp）
 * @param stream        流 ID
 * @param vhost         流虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnPublish(
        @JsonProperty("mediaServerId") String mediaServerId, // 服务器 ID
        @JsonProperty("app") String app,
        @JsonProperty("id") String id,
        @JsonProperty("ip") String ip,
        @JsonProperty("params") String params,
        @JsonProperty("port") int port,
        @JsonProperty("schema") Schema schema,
        @JsonProperty("protocol") Protocol protocol,
        @JsonProperty("stream") String stream,
        @JsonProperty("vhost") String vhost
) {
}
