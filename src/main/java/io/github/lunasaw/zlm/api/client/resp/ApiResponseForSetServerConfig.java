package io.github.lunasaw.zlm.api.client.resp;

/**
 * ZLM API 结果 - 设置服务器配置
 *
 * @param changed 配置项变更个数
 */
public record ApiResponseForSetServerConfig(
        Integer code,
        String msg,
        Integer changed
) implements ApiResponse {
}
