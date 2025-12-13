package io.github.lunasaw.zlm.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Schema {
    RTSP("rtsp"),
    RTMP("rtmp"),
    RTSPS("rtsps"),
    RTP("rtp"),
    HTTP("http"),
    HLS("hls"),
    FMP4("fmp4"),
    TS("ts"),
    RTC("rtc"),
    SRT("srt"),
    MUXER("muxer");

    private final String value;

    Schema(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Schema fromValue(String value) {
        if (value == null) return null;
        for (Schema s : values()) {
            if (s.value.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown schema: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
