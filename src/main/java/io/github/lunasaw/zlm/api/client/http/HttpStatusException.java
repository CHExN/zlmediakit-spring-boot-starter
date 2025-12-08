package io.github.lunasaw.zlm.api.client.http;

import lombok.Getter;

import java.io.IOException;

/**
 * HTTP 状态码异常，用于区分网络异常和 4xx/5xx。
 */
@Getter
public class HttpStatusException extends IOException {

    private final String body;
    private final int statusCode;

    public HttpStatusException(int statusCode, String body, String message) {
        super(message);
        this.body = body;
        this.statusCode = statusCode;
    }

}
