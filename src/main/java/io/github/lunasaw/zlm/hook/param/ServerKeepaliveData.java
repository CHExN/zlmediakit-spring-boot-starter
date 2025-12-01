package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM 服务器保活数据
 *
 * @param buffer                缓冲区数量
 * @param bufferLikeString      类字符串缓冲区数量
 * @param bufferList            缓冲区列表数量
 * @param bufferRaw             原始缓冲区数量
 * @param frame                 帧数量
 * @param frameImp              帧实现数量
 * @param mediaSource           媒体源数量
 * @param multiMediaSourceMuxer 多媒体源复用器数量
 * @param rtmpPacket            RTMP 包数量
 * @param rtpPacket             RTP 包数量
 * @param socket                套接字数量
 * @param tcpClient             TCP 客户端数量
 * @param tcpServer             TCP 服务器数量
 * @param tcpSession            TCP 会话数量
 * @param udpServer             UDP 服务器数量
 * @param udpSession            UDP 会话数量
 * @author CHEaN
 */
public record ServerKeepaliveData(
        @JsonProperty("Buffer") int buffer,
        @JsonProperty("BufferLikeString") int bufferLikeString,
        @JsonProperty("BufferList") int bufferList,
        @JsonProperty("BufferRaw") int bufferRaw,
        @JsonProperty("Frame") int frame,
        @JsonProperty("FrameImp") int frameImp,
        @JsonProperty("MediaSource") int mediaSource,
        @JsonProperty("MultiMediaSourceMuxer") int multiMediaSourceMuxer,
        @JsonProperty("RtmpPacket") int rtmpPacket,
        @JsonProperty("RtpPacket") int rtpPacket,
        @JsonProperty("Socket") int socket,
        @JsonProperty("TcpClient") int tcpClient,
        @JsonProperty("TcpServer") int tcpServer,
        @JsonProperty("TcpSession") int tcpSession,
        @JsonProperty("UdpServer") int udpServer,
        @JsonProperty("UdpSession") int udpSession
) {
}
