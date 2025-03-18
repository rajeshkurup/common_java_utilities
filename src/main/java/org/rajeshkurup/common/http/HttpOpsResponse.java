package org.rajeshkurup.common.http;

import org.rajeshkurup.common.mapper.JsonSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter @Setter
public class HttpOpsResponse {

    private String response;

    private int httpStatus;

    private HttpOpsError error;

    public HttpOpsResponse() {
        // Empty
    }

    public HttpOpsResponse(final int httpStatus, final String response, final HttpOpsError error) {
        this.httpStatus = httpStatus;
        this.response = response;
        this.error = error;
    }

    @Override
    public String toString() {
        return JsonSerializer.builder(HttpOpsResponse.class).build().toJson(this);
    }

}
