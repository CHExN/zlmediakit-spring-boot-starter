package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 系统核心对象统计。
 *
 * @param buffer              Buffer 数量
 * @param rtpPacket           RTP 包数量
 * @param frame               Frame 数量
 * @param rtmpPacket          RTMP 包数量
 * @param tcpSession          TCP 会话数量
 * @param udpServer           UDP 服务数量
 * @param tcpServer           TCP 服务数量
 * @param frameImp            FrameImp 数量
 * @param bufferList          BufferList 数量
 * @param bufferRaw           BufferRaw 数量
 * @param mediaSource         MediaSource 数量
 * @param multiMediaSourceMuxer MultiMediaSourceMuxer 数量
 * @param tcpClient           TCP 客户端数量
 * @param bufferLikeString    BufferLikeString 数量
 * @param socket              Socket 数量
 * @param udpSession          UDP 会话数量
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ImportantObjectNum(
        @JsonProperty("Buffer") Integer buffer,
        @JsonProperty("RtpPacket") Integer rtpPacket,
        @JsonProperty("Frame") Integer frame,
        @JsonProperty("RtmpPacket") Integer rtmpPacket,
        @JsonProperty("TcpSession") Integer tcpSession,
        @JsonProperty("UdpServer") Integer udpServer,
        @JsonProperty("TcpServer") Integer tcpServer,
        @JsonProperty("FrameImp") Integer frameImp,
        @JsonProperty("BufferList") Integer bufferList,
        @JsonProperty("BufferRaw") Integer bufferRaw,
        @JsonProperty("MediaSource") Integer mediaSource,
        @JsonProperty("MultiMediaSourceMuxer") Integer multiMediaSourceMuxer,
        @JsonProperty("TcpClient") Integer tcpClient,
        @JsonProperty("BufferLikeString") Integer bufferLikeString,
        @JsonProperty("Socket") Integer socket,
        @JsonProperty("UdpSession") Integer udpSession
) {
}
