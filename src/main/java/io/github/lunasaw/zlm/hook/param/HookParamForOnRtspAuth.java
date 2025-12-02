package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Protocol;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - RTSP 鉴权 (on_rtsp_auth)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param id            TCP 链接唯一 ID
 * @param ip            RTSP 播放器 IP
 * @param mustNoEncrypt 请求的密码是否必须为明文 (base64 鉴权需要明文密码)
 * @param params        RTSP URL 参数
 * @param port          RTSP 播放器端口号
 * @param realm         RTSP 播放鉴权加密 realm
 * @param schema        RTSP 媒体源类型
 * @param protocol      传输协议（RTSP / RTSPS ）
 * @param stream        流 ID
 * @param username      播放用户名
 * @param vhost         流虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnRtspAuth(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("id") String id,
        @JsonProperty("ip") String ip,
        @JsonProperty("must_no_encrypt") boolean mustNoEncrypt,
        @JsonProperty("params") String params,
        @JsonProperty("port") int port,
        @JsonProperty("realm") String realm,
        @JsonProperty("schema") Schema schema,
        @JsonProperty("protocol") Protocol protocol,
        @JsonProperty("stream") String stream,
        @JsonProperty("user_name") String username,
        @JsonProperty("vhost") String vhost
) implements StreamHookParam {
}
