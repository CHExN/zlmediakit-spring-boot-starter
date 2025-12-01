package io.github.lunasaw.zlm.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Protocol {
    HTTP("http"),
    HTTPS("https"),
    WS("ws"),
    WSS("wss"),
    RTSP("rtsp"),
    RTSPS("rtsps"),
    RTMP("rtmp"),
    RTMPS("rtmps"),
    RTC("rtc"),
    SRT("srt"),
    RTP("rtp"),
    TCP("tcp"),
    UDP("udp");

    private final String value;

    @JsonCreator
    public static Protocol fromValue(String value) {
        if (value == null) return null;
        for (Protocol p : values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown protocol: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
