package io.github.lunasaw.zlm.api.client.exception;

/**
 * 客户端调用 ZLM 相关的基础异常。
 */
public class ZlmClientException extends RuntimeException {

    public ZlmClientException(String message) {
        super(message);
    }

    public ZlmClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
