package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Protocol;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - RTSP 域鉴权事件 (on_rtsp_realm)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param id            TCP 链接唯一 ID
 * @param ip            RTSP 播放器 IP
 * @param params        播放 RTSP URL 参数
 * @param port          RTSP 播放器端口号
 * @param schema        RTSP 媒体源类型
 * @param protocol      RTSP 传输协议
 * @param stream        流 ID
 * @param vhost         流虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnRtspRealm(
        @JsonProperty("mediaServerId") String mediaServerId,
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
