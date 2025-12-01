package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 媒体列表中的单条数据。
 *
 * @param app              应用名
 * @param readerCount      本协议观看人数
 * @param totalReaderCount 观看总人数
 * @param schema           协议
 * @param stream           流 ID
 * @param originSock       客户端和服务器网络信息
 * @param originType       产生源类型编号
 * @param originTypeStr    产生源类型描述
 * @param originUrl        产生源 URL
 * @param createStamp      GMT unix 时间戳（秒）
 * @param aliveSecond      存活时间（秒）
 * @param bytesSpeed       数据产生速度（byte/s）
 * @param tracks           音视频轨道
 * @param vhost            虚拟主机名
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaData(
        @JsonProperty("app") String app,
        @JsonProperty("readerCount") Integer readerCount,
        @JsonProperty("totalReaderCount") Integer totalReaderCount,
        @JsonProperty("schema") String schema,
        @JsonProperty("stream") String stream,
        @JsonProperty("originSock") MediaPlayer originSock,
        @JsonProperty("originType") Integer originType,
        @JsonProperty("originTypeStr") String originTypeStr,
        @JsonProperty("originUrl") String originUrl,
        @JsonProperty("createStamp") Long createStamp,
        @JsonProperty("aliveSecond") Integer aliveSecond,
        @JsonProperty("bytesSpeed") Integer bytesSpeed,
        @JsonProperty("tracks") List<Track> tracks,
        @JsonProperty("vhost") String vhost
) {
}
