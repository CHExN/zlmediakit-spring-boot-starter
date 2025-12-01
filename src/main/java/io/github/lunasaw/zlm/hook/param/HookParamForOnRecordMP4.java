package io.github.lunasaw.zlm.hook.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - MP4 录制事件 (on_record_mp4)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param fileName      文件名
 * @param filePath      文件绝对路径
 * @param fileSize      文件大小（字节）
 * @param folder        文件所在目录路径
 * @param startTime     开始录制时间戳
 * @param stream        录制的流 ID
 * @param timeLen       录制时长（秒）
 * @param url           http/rtsp/rtmp 点播相对 url 路径
 * @param vhost         流虚拟主机
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnRecordMP4(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("file_name") String fileName,
        @JsonProperty("file_path") String filePath,
        @JsonProperty("file_size") long fileSize,
        @JsonProperty("folder") String folder,
        @JsonProperty("start_time") long startTime,
        @JsonProperty("stream") String stream,
        @JsonProperty("time_len") float timeLen,
        @JsonProperty("url") String url,
        @JsonProperty("vhost") String vhost
) {
}
