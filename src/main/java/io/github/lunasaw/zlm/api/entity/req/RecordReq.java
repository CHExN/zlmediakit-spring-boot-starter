package io.github.lunasaw.zlm.api.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * 录像相关请求参数。
 *
 * @param schema         协议
 * @param vhost          虚拟主机
 * @param app            应用名
 * @param stream         流 ID
 * @param period         录像日期，格式 2020-02-01
 * @param customizedPath 自定义路径
 * @param maxSeconds     mp4 录像切片时长（秒）
 * @param type           0 为 hls，1 为 mp4
 * @param speed          录像倍速
 * @param stamp          录像播放位置
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecordReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("period") String period,
        @JsonProperty("customized_path") String customizedPath,
        @JsonProperty("max_seconds") String maxSeconds,
        @JsonProperty("type") Integer type,
        @JsonProperty("speed") String speed,
        @JsonProperty("stamp") String stamp
) {

    public RecordReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> toMap() {
        return getMap();
    }

    public Map<String, String> getMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "period", period);
        MediaReq.putIfNotNull(map, "customized_path", customizedPath);
        return map;
    }

    public Map<String, String> getSaveMp4Map() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "customized_path", customizedPath);
        MediaReq.putIfNotNull(map, "max_seconds", maxSeconds);
        MediaReq.putIfNotNull(map, "type", type);
        return map;
    }

    public Map<String, String> getRecordSpeedMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "speed", speed);
        return map;
    }

    public Map<String, String> getSeekRecordStampMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "stamp", stamp);
        return map;
    }

    public Map<String, String> getStopRecordMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "type", type);
        return map;
    }

    public Map<String, String> getIsRecordingMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "type", type);
        return map;
    }
}
