package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Protocol;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - 流未找到 (on_stream_not_found)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param id            TCP 链接唯一 ID
 * @param ip            播放器 IP
 * @param params        推流 URL 参数
 * @param port          推流器端口号（unsigned short → int）
 * @param schema        播放的媒体源类型（rtsp / rtmp）
 * @param protocol      传输协议
 * @param stream        流 ID
 * @param vhost         流虚拟主机
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnStreamNotFound(
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
