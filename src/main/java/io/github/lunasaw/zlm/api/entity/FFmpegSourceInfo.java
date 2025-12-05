package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FFmpeg 拉流任务信息。
 *
 * @param cmd          FFmpeg 命令
 * @param dstUrl       推流地址
 * @param ffmpegCmdKey 配置 key
 * @param key          任务 key
 * @param srcUrl       拉流地址
 */
public record FFmpegSourceInfo(
        @JsonProperty("cmd") String cmd,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("ffmpeg_cmd_key") String ffmpegCmdKey,
        @JsonProperty("key") String key,
        @JsonProperty("src_url") String srcUrl
) {
}
