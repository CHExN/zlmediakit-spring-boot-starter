package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 音视频轨道信息。
 *
 * @param channels      音频通道数
 * @param codecId       编码 ID，H264=0,H265=1,AAC=2,G711A=3,G711U=4
 * @param codecIdName   编码类型名称
 * @param codecType     类型：视频=0，音频=1
 * @param fps           帧率
 * @param height        视频高度
 * @param ready         是否准备就绪
 * @param width         视频宽度
 * @param frames        累计接收帧数
 * @param sampleBit     音频采样位数
 * @param sampleRate    音频采样率
 * @param gopIntervalMs GOP 间隔时间（毫秒）
 * @param gopSize       GOP 大小（帧数）
 * @param keyFrames     累计接收关键帧数
 * @param duration      轨道时长（毫秒）
 * @param loss          丢包率，-1 表示未知
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Track(
        @JsonProperty("channels") Integer channels,
        @JsonProperty("codec_id") Integer codecId,
        @JsonProperty("codec_id_name") String codecIdName,
        @JsonProperty("codec_type") Integer codecType,
        @JsonProperty("fps") Double fps,
        @JsonProperty("height") Integer height,
        @JsonProperty("ready") Boolean ready,
        @JsonProperty("width") Integer width,
        @JsonProperty("frames") Integer frames,
        @JsonProperty("sample_bit") Integer sampleBit,
        @JsonProperty("sample_rate") Integer sampleRate,
        @JsonProperty("gop_interval_ms") Integer gopIntervalMs,
        @JsonProperty("gop_size") Integer gopSize,
        @JsonProperty("key_frames") Integer keyFrames,
        @JsonProperty("duration") Long duration,
        @JsonProperty("loss") Double loss
) {
}
