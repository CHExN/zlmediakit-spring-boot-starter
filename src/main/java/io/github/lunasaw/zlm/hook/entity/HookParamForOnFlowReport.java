package io.github.lunasaw.zlm.hook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Protocol;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - 流量统计事件 (on_flow_report)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param duration      TCP 链接维持时间（秒）
 * @param params        推流或播放 URL 参数
 * @param player        true:播放器 false:推流器
 * @param schema        播放或推流的媒体源类型（rtsp/rtmp/fmp4/ts/hls）
 * @param protocol      传输协议（http/https/ws/wss/rtsp/rtmp/rtsps/rtmps/rtc/srt/rtp/tcp/udp）
 * @param stream        流 ID
 * @param totalBytes    耗费上下行流量总和（字节）
 * @param vhost         流虚拟主机
 * @param ip            客户端 IP
 * @param port          客户端端口号
 * @param id            TCP 链接唯一 ID
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnFlowReport(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("duration") int duration,
        @JsonProperty("params") String params,
        @JsonProperty("player") boolean player,
        @JsonProperty("schema") Schema schema,
        @JsonProperty("protocol") Protocol protocol,
        @JsonProperty("stream") String stream,
        @JsonProperty("totalBytes") long totalBytes,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("ip") String ip,
        @JsonProperty("port") int port,
        @JsonProperty("id") String id
) implements HookParam, HookParamForStream {
}
