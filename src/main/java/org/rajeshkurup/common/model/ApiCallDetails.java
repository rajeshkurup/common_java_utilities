package org.rajeshkurup.common.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.mapper.JsonSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class ApiCallDetails implements Listable<ApiCallDetails>, Comparable<ApiCallDetails>, Copyable<ApiCallDetails>, Cloneable {

    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS z";

    @JsonProperty(value = "rec_id")
    @Setter @Getter
    private long recId;

    @JsonProperty(value = "endpoint")
    @Setter @Getter
    private String endpoint;

    @JsonProperty(value = "client_id")
    @Setter @Getter
    private String clientId;

    @JsonProperty(value = "http_method")
    @Setter @Getter
    private String httpMethod;

    @JsonProperty(value = "http_status")
    @Setter @Getter
    private int httpStatus;

    @JsonProperty(value = "tier")
    @Setter @Getter
    private int tier;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @JsonProperty(value = "start_ts")
    @Setter @Getter
    private ZonedDateTime startTs;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @JsonProperty(value = "end_ts")
    @Setter @Getter
    private ZonedDateTime endTs;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @JsonProperty(value = "updated_ts")
    @Setter @Getter
    private ZonedDateTime updatedTs;

    @JsonIgnore
    private ApiCallDetails next;

    @JsonIgnore
    private ApiCallDetails prev;

    @Override
    public int compareTo(final ApiCallDetails o) {
        return Long.compare(this.recId, o.recId);
    }

    @Override
    public ApiCallDetails getNext() {
        return this.next;
    }

    @Override
    public void setNext(final ApiCallDetails next) {
        this.next = next;
    }

    @Override
    public ApiCallDetails getPrev() {
        return this.prev;
    }

    @Override
    public void setPrev(final ApiCallDetails prev) {
        this.prev = prev;
    }

    @Override
    public boolean hasNext() {
        return Objects.nonNull(this.next);
    }

    @Override
    public boolean hasPrev() {
        return Objects.nonNull(this.prev);
    }

    @Override
    public String toString() {
        return new JsonSerializer<>(ApiCallDetails.class).toText(this);
    }

    @Override
    public ApiCallDetails clone() {
        try {
            ApiCallDetails clone = (ApiCallDetails) super.clone();
            clone.setUpdatedTs(this.updatedTs);
            clone.setTier(this.tier);
            clone.setEndTs(this.endTs);
            clone.setStartTs(this.startTs);
            clone.setEndpoint(this.endpoint);
            clone.setHttpStatus(this.httpStatus);
            clone.setHttpMethod(this.httpMethod);
            clone.setClientId(this.clientId);
            clone.setRecId(this.recId);
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public ApiCallDetails deepCopy() {
        return this.clone();
    }

}
