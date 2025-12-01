package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接 RTP 服务器请求（TCP 主动模式）。
 *
 * @param dstPort 服务器端口
 * @param dstUrl  服务器地址
 * @param streamId 绑定的流 ID
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConnectRtpServerReq(
        @JsonProperty("dst_port") Integer dstPort,
        @JsonProperty("dst_url") String dstUrl,
        @JsonProperty("stream_id") String streamId
) {

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        MediaReq.putIfNotNull(map, "dst_port", dstPort);
        MediaReq.putIfNotNull(map, "dst_url", dstUrl);
        MediaReq.putIfNotNull(map, "stream_id", streamId);
        return map;
    }
}
