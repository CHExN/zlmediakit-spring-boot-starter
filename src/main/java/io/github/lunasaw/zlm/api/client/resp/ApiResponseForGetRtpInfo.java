package io.github.lunasaw.zlm.api.client.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.SocketAddressPair;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ZLM API 结果 - RTP 代理时的某路 SSRC 信息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class ApiResponseForGetRtpInfo extends SocketAddressPair implements ApiResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String msg;

    /**
     * 是否存在，如为 false，则表示该 SSRC 会话不存在，{@link SocketAddressPair} 中不会有数据
     */
    @JsonProperty("exist")
    private Boolean exist;

}
