package io.github.lunasaw.zlm.api.client.exception;

/**
 * 网络层异常：连接超时、读超时、DNS 失败等。
 */
public class ZlmNetworkException extends ZlmClientException {

    public ZlmNetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
