package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 媒体会话信息
 *
 * @param app              应用名
 * @param readerCount      本协议观看人数
 * @param totalReaderCount 观看总人数（包含 hls/rtsp/rtmp/http-flv/ws-flv）
 * @param totalBytes       累计接收字节数
 * @param schema           协议
 * @param stream           流 ID
 * @param originSock       客户端和服务器网络信息
 * @param originType       源类型编号
 * @param originTypeStr    源类型描述
 * @param originUrl        源 URL
 * @param createStamp      数据产生时间戳
 * @param currentStamp     当前时间戳
 * @param aliveSecond      存活时间（秒）
 * @param bytesSpeed       数据产生速度（byte/s）
 * @param tracks           轨道列表
 * @param recordingHls     是否正在录制 HLS
 * @param recordingMp4     是否正在录制 MP4
 * @param params           额外参数
 * @param vhost            虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaInfo(
        @JsonProperty("app") String app,
        @JsonProperty("readerCount") Integer readerCount,
        @JsonProperty("totalReaderCount") Integer totalReaderCount,
        @JsonProperty("totalBytes") Long totalBytes,
        @JsonProperty("schema") String schema,
        @JsonProperty("stream") String stream,
        @JsonProperty("originSock") OriginSock originSock,
        @JsonProperty("originType") Integer originType,
        @JsonProperty("originTypeStr") String originTypeStr,
        @JsonProperty("originUrl") String originUrl,
        @JsonProperty("createStamp") Long createStamp,
        @JsonProperty("currentStamp") Long currentStamp,
        @JsonProperty("aliveSecond") Integer aliveSecond,
        @JsonProperty("bytesSpeed") Long bytesSpeed,
        @JsonProperty("tracks") List<Track> tracks,
        @JsonProperty("isRecordingHLS") Boolean recordingHls,
        @JsonProperty("isRecordingMP4") Boolean recordingMp4,
        @JsonProperty("params") String params,
        @JsonProperty("vhost") String vhost
) {
}
