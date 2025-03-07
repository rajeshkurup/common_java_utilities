package org.rajeshkurup.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.TimeZone;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public class JsonSerializer<T> {

    public static final String TIME_ZONE_UTC = "UTC";

    private final ObjectMapper objectMapper;
    private final Class<T> typeRef;

    private JsonSerializer(final Class<T> typeRef, final String timeZone) {
        this.typeRef = typeRef;
        this.objectMapper = JsonMapper.builder().findAndAddModules().defaultTimeZone(TimeZone.getTimeZone(timeZone)).build();
    }

    public String toJson(final T o) {
        try {
            return this.objectMapper.writeValueAsString(o);
        } catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public T fromJson(final String json) {
        try {
            return this.objectMapper.readValue(json, this.typeRef);
        } catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static class JsonSerializerBuilder<T> {

        private final Class<T> typeRef;
        private String timeZone;

        private JsonSerializerBuilder(@NonNull final Class<T> typeRef) {
            this.typeRef = typeRef;
            this.timeZone = JsonSerializer.TIME_ZONE_UTC;
        }

        public JsonSerializerBuilder<T> timeZone(@NonNull final String timeZone) {
            if(StringUtils.isNotBlank(timeZone)) {
                this.timeZone =  timeZone;
            }
            return this;
        }

        public JsonSerializer<T> build() {
            return new JsonSerializer<>(this.typeRef, this.timeZone);
        }

        public static <T> JsonSerializerBuilder<T> builder(@NonNull final Class<T> typeRef) {
            return new JsonSerializerBuilder<>(typeRef);
        }

    }

}
