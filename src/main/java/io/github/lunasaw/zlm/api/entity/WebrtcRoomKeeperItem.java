package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

/**
 * WebRTC 信令注册参数。
 *
 * @param serverHost 信令服务器地址
 * @param serverPort 信令服务器端口
 * @param roomId     房间 ID
 */
@Builder
public record WebrtcRoomKeeperItem(
        @JsonProperty("server_host") String serverHost,
        @JsonProperty("server_port") Integer serverPort,
        @JsonProperty("room_id") String roomId
) {

    public WebrtcRoomKeeperItem {
        Assert.notNull(serverHost, "serverHost must not be null");
        Assert.notNull(serverPort, "serverPort must not be null");
        Assert.notNull(roomId, "roomId must not be null");
    }
}
