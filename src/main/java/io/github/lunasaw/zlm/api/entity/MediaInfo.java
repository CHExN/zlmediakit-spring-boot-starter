package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 媒体会话信息。
 *
 * @param readerCount      本协议观看人数
 * @param totalReaderCount 观看总人数（包含 hls/rtsp/rtmp/http-flv/ws-flv）
 * @param tracks           轨道列表
 * @param aliveSecond      存活时间（秒）
 * @param app              应用名
 * @param bytesSpeed       数据产生速度（byte/s）
 * @param createStamp      数据产生时间戳
 * @param recordingHls     是否正在录制 HLS
 * @param recordingMp4     是否正在录制 MP4
 * @param originSock       源套接字信息
 * @param originType       源类型编号
 * @param originTypeStr    源类型描述
 * @param originUrl        源 URL
 * @param params           额外参数
 * @param schema           协议
 * @param stream           流 ID
 * @param totalBytes       累计接收字节数
 * @param vhost            虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaInfo(
        @JsonProperty("readerCount") Integer readerCount,
        @JsonProperty("totalReaderCount") Integer totalReaderCount,
        @JsonProperty("tracks") List<Track> tracks,
        @JsonProperty("aliveSecond") Integer aliveSecond,
        @JsonProperty("app") String app,
        @JsonProperty("bytesSpeed") Long bytesSpeed,
        @JsonProperty("createStamp") Long createStamp,
        @JsonProperty("isRecordingHLS") Boolean recordingHls,
        @JsonProperty("isRecordingMP4") Boolean recordingMp4,
        @JsonProperty("originSock") OriginSock originSock,
        @JsonProperty("originType") Integer originType,
        @JsonProperty("originTypeStr") String originTypeStr,
        @JsonProperty("originUrl") String originUrl,
        @JsonProperty("params") String params,
        @JsonProperty("schema") String schema,
        @JsonProperty("stream") String stream,
        @JsonProperty("totalBytes") Long totalBytes,
        @JsonProperty("vhost") String vhost
) {
}
