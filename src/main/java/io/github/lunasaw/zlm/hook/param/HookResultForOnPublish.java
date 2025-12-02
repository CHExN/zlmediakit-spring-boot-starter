package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;

/**
 * ZLM Hook 回调结果 - 推流鉴权
 *
 * @param code           错误代码，0 表示成功
 * @param msg            错误提示信息
 * @param enableHls      是否生成 hls-ts
 * @param enableHlsFmp4  是否生成 hls-fmp4
 * @param enableMp4      是否启用 mp4 录制
 * @param enableRtsp     是否转 rtsp
 * @param enableRtmp     是否转 rtmp/flv
 * @param enableTs       是否转 http-ts/ws-ts
 * @param enableFmp4     是否转 http-fmp4/ws-fmp4
 * @param hlsDemand      是否按需生成（nullable → 使用默认值）
 * @param rtspDemand     是否按需生成（nullable → 使用默认值）
 * @param rtmpDemand     是否按需生成（nullable → 使用默认值）
 * @param tsDemand       是否按需生成（nullable → 使用默认值）
 * @param fmp4Demand     是否按需生成（nullable → 使用默认值）
 * @param enableAudio    转协议是否开启音频
 * @param addMuteAudio   无音频时是否添加静音轨 AAC
 * @param mp4SavePath    mp4 保存根目录
 * @param mp4MaxSecond   mp4 切片大小（秒）
 * @param mp4AsPlayer    是否参与播放人数计数
 * @param hlsSavePath    hls 保存目录
 * @param modifyStamp    时间戳覆盖模式（0/1/2）
 * @param continuePushMs 断连续推延时
 * @param autoClose      无人观看是否自动关闭流
 * @param streamReplace  自定义流 ID（可替换 ssrc）
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@With
public record HookResultForOnPublish(

        @JsonProperty("code") int code,
        @JsonProperty("msg") String msg,

        @JsonProperty("enable_hls") Boolean enableHls,              // 是否生成 hls-ts
        @JsonProperty("enable_hls_fmp4") Boolean enableHlsFmp4,     // 是否生成 hls-fmp4
        @JsonProperty("enable_mp4") Boolean enableMp4,              // 是否启用 mp4 录制
        @JsonProperty("enable_rtsp") Boolean enableRtsp,            // 是否转 rtsp
        @JsonProperty("enable_rtmp") Boolean enableRtmp,            // 是否转 rtmp/flv
        @JsonProperty("enable_ts") Boolean enableTs,                // 是否转 http-ts/ws-ts
        @JsonProperty("enable_fmp4") Boolean enableFmp4,            // 是否转 http-fmp4/ws-fmp4

        @JsonProperty("hls_demand") Boolean hlsDemand,              // 是否按需生成（nullable → 使用默认值）
        @JsonProperty("rtsp_demand") Boolean rtspDemand,
        @JsonProperty("rtmp_demand") Boolean rtmpDemand,
        @JsonProperty("ts_demand") Boolean tsDemand,
        @JsonProperty("fmp4_demand") Boolean fmp4Demand,

        @JsonProperty("enable_audio") Boolean enableAudio,          // 转协议是否开启音频
        @JsonProperty("add_mute_audio") Boolean addMuteAudio,       // 无音频时是否添加静音轨 AAC

        @JsonProperty("mp4_save_path") String mp4SavePath,          // mp4 保存根目录
        @JsonProperty("mp4_max_second") Integer mp4MaxSecond,       // mp4 切片大小（秒）
        @JsonProperty("mp4_as_player") Boolean mp4AsPlayer,         // 是否参与播放人数计数

        @JsonProperty("hls_save_path") String hlsSavePath,          // hls 保存目录
        @JsonProperty("modify_stamp") Integer modifyStamp,          // 时间戳覆盖模式（0/1/2）
        @JsonProperty("continue_push_ms") Integer continuePushMs,   // 断连续推延时
        @JsonProperty("auto_close") Boolean autoClose,              // 无人观看是否自动关闭流
        @JsonProperty("stream_replace") String streamReplace        // 自定义流 ID（可替换 ssrc）
) {

    public static HookResultForOnPublish success() {
        return HookResultForOnPublish.builder()
                .code(0).msg("")
                .build();
    }

    public static HookResultForOnPublish fail() {
        return HookResultForOnPublish.builder()
                .code(1).msg("")
                .build();
    }

    public static HookResultForOnPublish fail(String msg) {
        return HookResultForOnPublish.builder()
                .code(1).msg(msg)
                .build();
    }

}
