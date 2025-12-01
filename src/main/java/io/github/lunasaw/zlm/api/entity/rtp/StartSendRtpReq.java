package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;

import java.util.Map;

/**
 * 作为 GB28181 客户端启动 ps-rtp 推流请求。
 *
 * @param schema    协议
 * @param vhost     虚拟主机
 * @param app       应用名
 * @param stream    流 ID
 * @param ssrc      推流的 SSRC
 * @param dstUrl    目标 IP 或域名
 * @param dstPort   目标端口
 * @param isUdp     是否使用 UDP，默认 TCP
 * @param srcPort   本机端口，0 为随机
 * @param pt        RTP pt（uint8），默认 96
 * @param usePs     负载类型，1 表示 ps，0 表示 es，默认 1
 * @param onlyAudio usePs 为 0 时有效，为 1 时发送音频，为 0 时发送视频，默认 0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StartSendRtpReq(
        @JsonProperty("schema") String schema,
        @JsonProperty("vhost") String vhost,
        @JsonProperty("app") String app,
        @JsonProperty("stream") String stream,
        @JsonProperty("ssrc") Integer ssrc,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("dst_port") Integer dstPort,
        @JsonProperty("is_udp") Boolean isUdp,
        @JsonProperty("src_port") Integer srcPort,
        @JsonProperty("pt") Integer pt,
        @JsonProperty("use_ps") Integer usePs,
        @JsonProperty("only_audio") Boolean onlyAudio
) {

    public StartSendRtpReq {
        schema = schema == null ? "rtsp" : schema;
        vhost = vhost == null ? "__defaultVhost__" : vhost;
    }

    public Map<String, String> toMap() {
        return getMap();
    }

    public Map<String, String> getMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "ssrc", ssrc);
        MediaReq.putIfNotNull(map, "dst_url", dstUrl);
        MediaReq.putIfNotNull(map, "dst_port", dstPort);
        MediaReq.putIfNotNull(map, "is_udp", isUdp);
        MediaReq.putIfNotNull(map, "src_port", srcPort);
        MediaReq.putIfNotNull(map, "pt", pt);
        MediaReq.putIfNotNull(map, "use_ps", usePs);
        MediaReq.putIfNotNull(map, "only_audio", onlyAudio);
        return map;
    }

    /**
     * TCP passive 模式推流参数。
     */
    public Map<String, String> getPassiveMap() {
        Map<String, String> map = MediaReq.baseParams(schema, vhost, app, stream);
        MediaReq.putIfNotNull(map, "ssrc", ssrc);
        MediaReq.putIfNotNull(map, "src_port", srcPort);
        MediaReq.putIfNotNull(map, "pt", pt);
        MediaReq.putIfNotNull(map, "use_ps", usePs);
        MediaReq.putIfNotNull(map, "only_audio", onlyAudio);
        return map;
    }
}
