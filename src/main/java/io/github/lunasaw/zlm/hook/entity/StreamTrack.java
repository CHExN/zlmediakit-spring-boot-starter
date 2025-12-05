package io.github.lunasaw.zlm.hook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @param channels     音频通道数
 * @param codecId      H264 = 0, H265 = 1, AAC = 2, G711A = 3, G711U = 4
 * @param codecIdName  编码类型名称
 * @param codecType    编解码器类型（Video = 0, Audio = 1）
 * @param ready        轨道是否准备就绪
 * @param sampleBit    音频采样位数
 * @param sampleRate   音频采样率
 * @param fps          视频帧率
 * @param height       视频高度
 * @param width        视频宽度
 */
@Deprecated // TODO 见 io.github.lunasaw.zlm.api.entity.Track，后续统一使用该实体类
public record StreamTrack(
        // 公共字段
        @JsonProperty("channels") Integer channels,
        @JsonProperty("codec_id") Integer codecId,
        @JsonProperty("codec_id_name") String codecIdName,
        @JsonProperty("codec_type") Integer codecType,
        @JsonProperty("ready") Boolean ready,

        // 音频专有字段
        @JsonProperty("sample_bit") Integer sampleBit,
        @JsonProperty("sample_rate") Integer sampleRate,

        // 视频专有字段
        @JsonProperty("fps") Integer fps,
        @JsonProperty("height") Integer height,
        @JsonProperty("width") Integer width
) { }
