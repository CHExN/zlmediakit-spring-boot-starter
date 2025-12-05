package io.github.lunasaw.zlm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Lightweight Jackson helper to keep JSON handling in one place.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();

    private JsonUtils() {
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Failed to deserialize json", e);
        }
    }

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Failed to serialize object", e);
        }
    }

    /**
     * Convert a bean into a map of string values, skipping null entries.
     */
    public static Map<String, String> toStringMap(Object value) {
        if (value == null) {
            return new HashMap<>();
        }
        Map<String, Object> raw = MAPPER.convertValue(value, new TypeReference<Map<String, Object>>() {});
        return raw.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.valueOf(entry.getValue())
                ));
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
