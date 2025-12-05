package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 主要对象数量统计。
 * <p>
 * 预置常见指标，同时兼容未来新增字段。
 */
@Getter
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticData {

    @JsonProperty("Buffer")
    private Integer buffer;
    @JsonProperty("BufferLikeString")
    private Integer bufferLikeString;
    @JsonProperty("BufferList")
    private Integer bufferList;
    @JsonProperty("BufferRaw")
    private Integer bufferRaw;
    @JsonProperty("Frame")
    private Integer frame;
    @JsonProperty("FrameImp")
    private Integer frameImp;
    @JsonProperty("MediaSource")
    private Integer mediaSource;
    @JsonProperty("MultiMediaSourceMuxer")
    private Integer multiMediaSourceMuxer;
    @JsonProperty("RtmpPacket")
    private Integer rtmpPacket;
    @JsonProperty("RtpPacket")
    private Integer rtpPacket;
    @JsonProperty("Socket")
    private Integer socket;
    @JsonProperty("TcpClient")
    private Integer tcpClient;
    @JsonProperty("TcpServer")
    private Integer tcpServer;
    @JsonProperty("TcpSession")
    private Integer tcpSession;
    @JsonProperty("UdpServer")
    private Integer udpServer;
    @JsonProperty("UdpSession")
    private Integer udpSession;

    private final Map<String, Object> others = new HashMap<>();

    @JsonAnySetter
    public void addOther(String key, Object value) {
        others.put(key, value);
    }

}
