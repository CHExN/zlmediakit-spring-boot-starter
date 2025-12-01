package io.github.lunasaw.zlm.net;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author luna
 */
@Getter
@AllArgsConstructor
public enum HttpContentTypeEnum {

    /**
     * JSON
     */
    CONTENT_TYPE_JSON(1, "content-type", HttpUtilsConstant.JSON),
    /**
     * form-urlencoded
     */
    CONTENT_TYPE_X_WWW_FORM_URLENCODED(2, "content-type", HttpUtilsConstant.X_WWW_FORM_URLENCODED),
    /**
     * form-data
     */
    CONTENT_TYPE_FORM_DATA(3, "content-type", HttpUtilsConstant.FORM_DATA),
    /**
     * application/form-data/utf-8
     */
    CONTENT_TYPE_APPLICATION_FORM_DATA_UTF8(4, "content-type", HttpUtilsConstant.APPLICATION_FORM_DATA_UTF8),
    /**
     * application/form-data
     */
    CONTENT_TYPE_APPLICATION_FORM_DATA(5, "content-type", HttpUtilsConstant.APPLICATION_FORM_DATA),
    /**
     * msexcel
     */
    CONTENT_TYPE_MSEXCEL(6, "content-type", HttpUtilsConstant.MSEXCEL),
    /**
     * text
     */
    CONTENT_TYPE_TEXT(7, "content-type", HttpUtilsConstant.TEXT),
    /**
     * octet-stream
     */
    CONTENT_OCTET_STREAM(8, "content-type", HttpUtilsConstant.OCTET_STREAM);

    /**
     * 编号
     */
    private final Integer code;
    /**
     * k
     */
    private final String key;
    /**
     * v
     */
    private final String value;
}
