package org.rajeshkurup.common.http;

import org.rajeshkurup.common.mapper.JsonSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter @Setter
public class HttpOpsError {

    private int errCode;

    private String errMsg;

    public HttpOpsError() {
        // Empty
    }
    
    public HttpOpsError(final int errCode, final String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return JsonSerializer.builder(HttpOpsError.class).build().toJson(this);
    }

}
