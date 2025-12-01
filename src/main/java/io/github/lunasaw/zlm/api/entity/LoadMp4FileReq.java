package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;

import java.util.Map;

/**
 * 点播 mp4 文件请求。
 *
 * @param schema   协议
 * @param vhost    虚拟主机
 * @param app      应用名
 * @param stream   流 ID
 * @param filePath mp4 文件绝对路径
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoadMp4FileReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("file_path") String filePath
) {

    public LoadMp4FileReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> getMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "file_path", filePath);
        return map;
    }
}
