package io.github.lunasaw.zlm.api.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * 批量关流请求。
 *
 * @param schema 协议
 * @param vhost  虚拟主机
 * @param app    应用名
 * @param stream 流 ID
 * @param force  是否强制关闭
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CloseStreamsReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("force") Integer force
) {

    public CloseStreamsReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "force", force);
        return map;
    }
}
