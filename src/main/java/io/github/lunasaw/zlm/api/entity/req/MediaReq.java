package io.github.lunasaw.zlm.api.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 媒体流通用查询参数。
 *
 * @param schema 协议，默认 rtsp
 * @param vhost  虚拟主机，默认 __defaultVhost__
 * @param app    应用名
 * @param stream 流 ID
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream
) {

    public MediaReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> toMap() {
        return baseParams(schema, vhost, app, stream);
    }

    public static Map<String, String> baseParams(String schema, String vhost, String app, String stream) {
        Map<String, String> map = new HashMap<>();
        String normalizedSchema = schema == null ? "rtsp" : schema;
        String normalizedVhost = vhost == null ? "__defaultVhost__" : vhost;
        map.put("schema", normalizedSchema);
        map.put("vhost", normalizedVhost);
        putIfNotNull(map, "app", app);
        putIfNotNull(map, "stream", stream);
        return map;
    }

    public static Map<String, String> getParam(String app, String stream) {
        return baseParams("rtsp", "__defaultVhost__", app, stream);
    }

    public static void putIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, String.valueOf(value));
        }
    }
}
