package io.github.lunasaw.zlm.api.client.exception;

/**
 * 协议层异常：响应不是合法 JSON 等解析错误。
 */
public class ZlmProtocolException extends ZlmClientException {

    public ZlmProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
