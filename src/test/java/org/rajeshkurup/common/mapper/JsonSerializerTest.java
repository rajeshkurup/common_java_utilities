package org.rajeshkurup.common.mapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rajeshkurup.common.model.ApiCallDetails;
import org.rajeshkurup.common.model.TestObject;

public class JsonSerializerTest {

    private JsonSerializer<ApiCallDetails> jsonSerializer;
    private JsonSerializer<ApiCallDetails> jsonSerializerIst;
    private JsonSerializer<TestObject> jsonSerializerPst;

    @BeforeEach
    public void init() {
        this.jsonSerializer = JsonSerializer.builder(ApiCallDetails.class).timeZone("").build();
        this.jsonSerializerIst = JsonSerializer.builder(ApiCallDetails.class).timeZone("IST").build();
        this.jsonSerializerPst = JsonSerializer.builder(TestObject.class).timeZone("PST").build();
    }

    @AfterEach
    public void cleanup() {
        this.jsonSerializer = null;
        this.jsonSerializerIst = null;
        this.jsonSerializerPst = null;
    }

    @Test
    public void testToJsonPass() {
        String jsonTxt = this.jsonSerializer.toJson(buildApiCallDetails());

        String expJsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 16:11:12.133 UTC","end_ts":"2025/02/28 17:11:12.133 UTC","updated_ts":"2025/02/28 18:11:12.133 UTC"}""";

        Assertions.assertEquals(expJsonTxt, jsonTxt);
    }

    @Test
    public void testToJsonIstPass() {
        String jsonTxt = this.jsonSerializerIst.toJson(buildApiCallDetails());

        String expJsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 21:41:12.133 IST","end_ts":"2025/02/28 22:41:12.133 IST","updated_ts":"2025/02/28 23:41:12.133 IST"}""";

        Assertions.assertEquals(expJsonTxt, jsonTxt);
    }

    @Test
    public void testToJsonFail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.jsonSerializerPst.toJson(new TestObject()));
    }

    @Test
    public void testFromJsonPass() {
        String jsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 08:11:12.133 PST","end_ts":"2025/02/28 09:11:12.133 PST","updated_ts":"2025/02/28 10:11:12.133 PST"}""";

        ApiCallDetails apiCallDetails = this.jsonSerializer.fromJson(jsonTxt);

        Assertions.assertEquals(1001L, apiCallDetails.getRecId());
        Assertions.assertEquals("/v1/google/search", apiCallDetails.getEndpoint());
        Assertions.assertEquals(1, apiCallDetails.getTier());
        Assertions.assertEquals("lombok", apiCallDetails.getClientId());
        Assertions.assertEquals("2025/02/28 17:11:12.133 Z", apiCallDetails.getEndTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
        Assertions.assertEquals("POST", apiCallDetails.getHttpMethod());
        Assertions.assertEquals(201, apiCallDetails.getHttpStatus());
        Assertions.assertEquals("2025/02/28 16:11:12.133 Z", apiCallDetails.getStartTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
        Assertions.assertEquals("2025/02/28 18:11:12.133 Z", apiCallDetails.getUpdatedTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
    }

    @Test
    public void testFromJsonIstPass() {
        String jsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 08:11:12.133 PST","end_ts":"2025/02/28 09:11:12.133 PST","updated_ts":"2025/02/28 10:11:12.133 PST"}""";

        ApiCallDetails apiCallDetails = this.jsonSerializerIst.fromJson(jsonTxt);

        Assertions.assertEquals(1001L, apiCallDetails.getRecId());
        Assertions.assertEquals("/v1/google/search", apiCallDetails.getEndpoint());
        Assertions.assertEquals(1, apiCallDetails.getTier());
        Assertions.assertEquals("lombok", apiCallDetails.getClientId());
        Assertions.assertEquals("2025/02/28 22:41:12.133 IST", apiCallDetails.getEndTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
        Assertions.assertEquals("POST", apiCallDetails.getHttpMethod());
        Assertions.assertEquals(201, apiCallDetails.getHttpStatus());
        Assertions.assertEquals("2025/02/28 21:41:12.133 IST", apiCallDetails.getStartTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
        Assertions.assertEquals("2025/02/28 23:41:12.133 IST", apiCallDetails.getUpdatedTs().format(DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT)));
    }

    @Test
    public void testFromJsonFail() {
        String jsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 08:11:12.133 PST","end_ts":"2025/02/28 09:11:12.133 PST","updated_ts":"2025/02/28 10:11:12.133 PST"}""";

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.jsonSerializerPst.fromJson(jsonTxt));
    }

    private ApiCallDetails buildApiCallDetails() {
        ZonedDateTime updatedTs = ZonedDateTime.parse("2025/02/28 10:11:12.133 PST", DateTimeFormatter.ofPattern(ApiCallDetails.DATE_TIME_FORMAT));

        ApiCallDetails apiCallDetails = new ApiCallDetails();
        apiCallDetails.setRecId(1001L);
        apiCallDetails.setEndpoint("/v1/google/search");
        apiCallDetails.setTier(1);
        apiCallDetails.setClientId("lombok");
        apiCallDetails.setEndTs(updatedTs.minusHours(1));
        apiCallDetails.setHttpMethod("POST");
        apiCallDetails.setHttpStatus(201);
        apiCallDetails.setStartTs(updatedTs.minusHours(2));
        apiCallDetails.setUpdatedTs(updatedTs);
        return apiCallDetails;
    }

}
