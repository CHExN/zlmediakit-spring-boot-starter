package io.github.lunasaw.zlm.api.entity.rtp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建 RTP 服务器请求。
 *
 * @param port     接收端口，0 表示随机端口
 * @param tcpMode  0 udp，1 tcp 被动，2 tcp 主动
 * @param streamId 绑定的流 ID
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OpenRtpServerReq(
        @JsonProperty("port") Integer port,
        @JsonProperty("tcp_mode") Integer tcpMode,
        @JsonProperty("stream_id") String streamId
) {

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        MediaReq.putIfNotNull(map, "port", port);
        MediaReq.putIfNotNull(map, "tcp_mode", tcpMode);
        MediaReq.putIfNotNull(map, "stream_id", streamId);
        return map;
    }
}
