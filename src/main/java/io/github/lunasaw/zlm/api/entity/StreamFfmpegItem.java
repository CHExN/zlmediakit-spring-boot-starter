package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.util.JsonUtils;

import java.util.Map;

/**
 * FFmpeg 拉流配置。
 *
 * @param srcUrl      FFmpeg 拉流地址
 * @param dstUrl      FFmpeg 推流地址
 * @param timeoutMs   推流成功超时时间
 * @param enableHls   是否开启 hls 录制
 * @param enableMp4   是否开启 mp4 录制
 * @param ffmpegCmdKey 自定义 FFmpeg 命令模板 key
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StreamFfmpegItem(
        @JsonProperty("src_url") String srcUrl,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("timeout_ms") Integer timeoutMs,
        @JsonProperty("enable_hls") Boolean enableHls,
        @JsonProperty("enable_mp4") Boolean enableMp4,
        @JsonProperty("ffmpeg_cmd_key") String ffmpegCmdKey
) {

    public Map<String, String> toMap() {
        return JsonUtils.toStringMap(this);
    }
}
