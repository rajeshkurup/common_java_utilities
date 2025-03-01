package org.rajeshkurup.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.TimeZone;

public class JsonSerializer<T> {

    public static final String TIME_ZONE_UTC = "UTC";

    private final ObjectMapper objectMapper;
    private final Class<T> typeRef;

    public JsonSerializer(final Class<T> typeRef) {
        this.typeRef = typeRef;
        this.objectMapper = JsonMapper.builder().findAndAddModules().defaultTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC)).build();
    }

    public JsonSerializer(final Class<T> typeRef, final String timeZone) {
        this.typeRef = typeRef;
        this.objectMapper = JsonMapper.builder().findAndAddModules().defaultTimeZone(TimeZone.getTimeZone(timeZone)).build();
    }

    public String toText(final T o) {
        try {
            return this.objectMapper.writeValueAsString(o);
        } catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public T toObject(final String json) {
        try {
            return this.objectMapper.readValue(json, this.typeRef);
        } catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
