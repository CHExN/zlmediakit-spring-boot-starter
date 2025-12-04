package io.github.lunasaw.zlm.hook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lunasaw.zlm.enums.Schema;

/**
 * ZLM Hook 回调参数 - 流无人观看 (on_stream_none_reader)
 *
 * @param mediaServerId 服务器 ID
 * @param app           流应用名
 * @param schema        媒体源类型
 * @param stream        流 ID
 * @param vhost         流虚拟主机
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnStreamNoneReader(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("app") String app,
        @JsonProperty("schema") Schema schema,
        @JsonProperty("stream") String stream,
        @JsonProperty("vhost") String vhost
) implements HookParam, HookParamForStream {
}
