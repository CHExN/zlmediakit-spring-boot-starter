package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;

import java.util.Map;

/**
 * 停止 GB28181 PS-RTP 推流请求。
 *
 * @param schema 协议
 * @param vhost  虚拟主机
 * @param app    应用名
 * @param stream 流 ID
 * @param ssrc   推流的 SSRC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CloseSendRtpReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("ssrc") String ssrc
) {

    public CloseSendRtpReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> getMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "ssrc", ssrc);
        return map;
    }
}
