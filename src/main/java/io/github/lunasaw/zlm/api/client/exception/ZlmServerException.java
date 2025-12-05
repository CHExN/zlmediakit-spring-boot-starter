package io.github.lunasaw.zlm.api.client.exception;

import lombok.Getter;

/**
 * ZLM 业务层异常：code != 0。
 */
@Getter
public class ZlmServerException extends ZlmClientException {

    private final int serverCode;
    private final String serverMessage;

    public ZlmServerException(int serverCode, String serverMessage) {
        super("ZLMediaKit error code " + serverCode + ": " + serverMessage);
        this.serverCode = serverCode;
        this.serverMessage = serverMessage;
    }

}
