package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 播放地址模型，包含不同协议的播放 URL。
 *
 * @param rtsp    RTSP 播放地址
 * @param rtmp    RTMP 播放地址
 * @param httpFlv HTTP-FLV 播放地址
 * @param wsFlv   WebSocket-FLV 播放地址
 * @param hls     HLS 播放地址
 * @param httpTs  HTTP-TS 播放地址
 * @param wsTs    WebSocket-TS 播放地址
 * @param httpFmp4 HTTP-fMP4 播放地址
 * @param wsFmp4  WebSocket-fMP4 播放地址
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlayUrl(
        @JsonProperty("rtsp") String rtsp,
        @JsonProperty("rtmp") String rtmp,
        @JsonProperty("http_flv") String httpFlv,
        @JsonProperty("ws_flv") String wsFlv,
        @JsonProperty("hls") String hls,
        @JsonProperty("http_ts") String httpTs,
        @JsonProperty("ws_ts") String wsTs,
        @JsonProperty("http_fmp4") String httpFmp4,
        @JsonProperty("ws_fmp4") String wsFmp4
) {
}
