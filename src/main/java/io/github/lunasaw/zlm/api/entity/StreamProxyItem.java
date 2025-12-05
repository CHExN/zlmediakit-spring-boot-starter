package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * 拉流代理配置
 *
 * @param vhost         虚拟主机，例如 __defaultVhost__
 * @param app           应用名
 * @param stream        流 ID
 * @param url           拉流地址
 * @param retryCount    拉流重试次数，不传此参数或传值 <=0 时，则无限重试
 * @param rtpType       RTSP 拉流方式，0：tcp，1：udp，2：组播
 * @param timeoutSec    拉流超时时间（秒）
 * @param enableHls     是否转换为 hls-mpegts
 * @param enableHlsFmp4 是否转换为 hls-fmp4
 * @param enableMp4     是否允许 mp4 录制
 * @param enableRtsp    是否转 rtsp 协议
 * @param enableRtmp    是否转 rtmp/flv 协议
 * @param enableTs      是否转 http-ts/ws-ts 协议
 * @param enableFmp4    是否转 http-fmp4/ws-fmp4 协议
 * @param hlsDemand     hls 是否按需生成
 * @param rtspDemand    rtsp 是否按需生成
 * @param rtmpDemand    rtmp 是否按需生成
 * @param tsDemand      ts 是否按需生成
 * @param fmp4Demand    fmp4 是否按需生成
 * @param enableAudio   转协议时是否开启音频
 * @param addMuteAudio  无音频时是否补静音 AAC
 * @param mp4SavePath   mp4 录制根目录，置空使用默认目录
 * @param mp4MaxSecond  mp4 切片时长（秒）
 * @param mp4AsPlayer   mp4 录制是否计入播放人数
 * @param hlsSavePath   hls 录制根目录，置空使用默认目录
 * @param modifyStamp   是否修改原始时间戳，默认值2；取值范围：0.采用源视频流绝对时间戳，不做任何改变;1.采用zlmediakit接收数据时的系统时间戳(有平滑处理);2.采用源视频流时间戳相对时间戳(增长量)，有做时间戳跳跃和回退矫正
 * @param autoClose     无人观看是否自动关闭流 (不触发无人观看 Hook)
 * @param latency       srt 延时, 单位毫秒
 * @param passphrase    srt 拉流的密码
 */
@Builder
public record StreamProxyItem(
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("url") String url,
        @JsonProperty("retry_count") Integer retryCount,
        @JsonProperty("rtp_type") Integer rtpType,
        @JsonProperty("timeout_sec") Integer timeoutSec,
        @JsonProperty("enable_hls") Boolean enableHls,
        @JsonProperty("enable_hls_fmp4") Boolean enableHlsFmp4,
        @JsonProperty("enable_mp4") Boolean enableMp4,
        @JsonProperty("enable_rtsp") Boolean enableRtsp,
        @JsonProperty("enable_rtmp") Boolean enableRtmp,
        @JsonProperty("enable_ts") Boolean enableTs,
        @JsonProperty("enable_fmp4") Boolean enableFmp4,
        @JsonProperty("hls_demand") Boolean hlsDemand,
        @JsonProperty("rtsp_demand") Boolean rtspDemand,
        @JsonProperty("rtmp_demand") Boolean rtmpDemand,
        @JsonProperty("ts_demand") Boolean tsDemand,
        @JsonProperty("fmp4_demand") Boolean fmp4Demand,
        @JsonProperty("enable_audio") Boolean enableAudio,
        @JsonProperty("add_mute_audio") Boolean addMuteAudio,
        @JsonProperty("mp4_save_path") String mp4SavePath,
        @JsonProperty("mp4_max_second") Integer mp4MaxSecond,
        @JsonProperty("mp4_as_player") Boolean mp4AsPlayer,
        @JsonProperty("hls_save_path") String hlsSavePath,
        @JsonProperty("modify_stamp") Integer modifyStamp,
        @JsonProperty("auto_close") Boolean autoClose,
        @JsonProperty("latency") String latency,
        @JsonProperty("passphrase") String passphrase
) {

    public StreamProxyItem {
        Assert.notNull(vhost, "vhost must not be null");
        Assert.notNull(app, "app must not be null");
        Assert.notNull(stream, "stream must not be null");
        Assert.notNull(url, "url must not be null");
    }

}
