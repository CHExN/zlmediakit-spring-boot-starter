package io.github.lunasaw.zlm.api.client.exception;

import lombok.Getter;

/**
 * HTTP 层异常：4xx / 5xx。
 */
@Getter
public class ZlmHttpException extends ZlmClientException {

    private final int statusCode;

    public ZlmHttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public ZlmHttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

}
