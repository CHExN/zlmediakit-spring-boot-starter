package io.github.lunasaw.zlm.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OriginType {

    // 不可调整顺序
    UNKNOWN("UNKNOWN"),
    RTMP_PUSH("PUSH"),
    RTSP_PUSH("PUSH"),
    RTP_PUSH("RTP"),
    PULL("PULL"),
    FFMPEG_PULL("PULL"),
    MP4_VOD("MP4_VOD"),
    DEVICE_CHN("DEVICE_CHN"),
    RTC_PUSH("PUSH");

    private final String type;

    @JsonCreator
    public static OriginType fromOrdinal(int ordinal) {
        for (OriginType originType : values()) {
            if (originType.ordinal() == ordinal) {
                return originType;
            }
        }

        throw new IllegalArgumentException("Unknown OriginType: " + ordinal);
    }

    @JsonValue
    public int getOrdinal() {
        return this.ordinal();
    }

}
