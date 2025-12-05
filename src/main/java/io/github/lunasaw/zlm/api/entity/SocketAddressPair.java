package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class SocketAddressPair {

    @JsonProperty("peer_ip")
    private String peerIp;
    @JsonProperty("peer_port")
    private Integer peerPort;
    @JsonProperty("local_ip")
    private String localIp;
    @JsonProperty("local_port")
    private Integer localPort;

}
