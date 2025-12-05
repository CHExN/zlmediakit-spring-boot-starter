package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * FFmpeg 拉流配置
 *
 * @param srcUrl       FFmpeg 拉流地址
 * @param dstUrl       FFmpeg 推流地址
 * @param timeoutMs    推流成功超时时间
 * @param enableHls    是否开启 hls 录制
 * @param enableMp4    是否开启 mp4 录制
 * @param ffmpegCmdKey 配置文件中自定义 FFmpeg 命令模板 key (非内容)，置空则采用默认模板: ffmpeg.cmd
 */
@Builder
public record StreamFFmpegItem(
        @JsonProperty("src_url") String srcUrl,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("timeout_ms") Integer timeoutMs,
        @JsonProperty("enable_hls") Boolean enableHls,
        @JsonProperty("enable_mp4") Boolean enableMp4,
        @JsonProperty("ffmpeg_cmd_key") String ffmpegCmdKey
) {

    public StreamFFmpegItem {
        Assert.notNull(srcUrl, "src_url must not be null");
        Assert.notNull(dstUrl, "dst_url must not be null");
        Assert.notNull(timeoutMs, "timeout_ms must not be null");
        Assert.notNull(enableHls, "enable_hls must not be null");
        Assert.notNull(enableMp4, "enable_mp4 must not be null");
    }

}
