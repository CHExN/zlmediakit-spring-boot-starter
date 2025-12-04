package io.github.lunasaw.zlm.hook.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.OriginType;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - 流注册/注销变更 (on_stream_changed)
 *
 * @param mediaServerId    服务器 ID
 * @param app              流应用名
 * @param regist           是否注册（true：注册，false：注销）
 * @param schema           媒体源类型
 * @param stream           流 ID
 * @param vhost            流虚拟主机
 * @param aliveSecond      存活时间（秒）
 * @param bytesSpeed       数据产生速度（byte/s）
 * @param createStamp      GMT unix 系统时间戳（秒）
 * @param originSock       源地址信息
 * @param originType       产生源类型（unknown=0,rtmp_push=1,rtsp_push=2,rtp_push=3,pull=4,ffmpeg_pull=5,mp4_vod=6,device_chn=7,rtc_push=8）
 * @param originTypeStr    产生源类型字符串描述
 * @param originUrl        产生源的 URL
 * @param readerCount      本协议观看人数
 * @param totalReaderCount 观看总人数，包括 hls/rtsp/rtmp/http-flv/ws-flv/rtc
 * @param tracks           流的媒体轨道信息列表
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnStreamChanged(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("regist") boolean regist,
        @JsonProperty("schema") Schema schema,
        @JsonProperty("stream") String stream,
        @JsonProperty("vhost") String vhost,

        // 注册时才有的扩展字段：
        @JsonProperty("aliveSecond") Integer aliveSecond,
        @JsonProperty("bytesSpeed") Long bytesSpeed,
        @JsonProperty("createStamp") Long createStamp,
        @JsonProperty("originSock") StreamOriginSock originSock,
        @JsonProperty("originType") OriginType originType,
        @JsonProperty("originTypeStr") String originTypeStr,
        @JsonProperty("originUrl") String originUrl,
        @JsonProperty("readerCount") Integer readerCount,
        @JsonProperty("totalReaderCount") Integer totalReaderCount,
        @JsonProperty("tracks") List<StreamTrack> tracks
) implements HookParam, HookParamForStream {
}
