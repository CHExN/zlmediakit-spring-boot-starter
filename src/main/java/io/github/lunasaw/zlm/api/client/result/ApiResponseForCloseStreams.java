package io.github.lunasaw.zlm.api.client.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.api.entity.CloseStreams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ZLM API 结果 - 关闭流
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class ApiResponseForCloseStreams extends CloseStreams implements ApiResponse {

    @JsonProperty("code")
    private final Integer code;

    @JsonProperty("msg")
    private final String msg;

}
