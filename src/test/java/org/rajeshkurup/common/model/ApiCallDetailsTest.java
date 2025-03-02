package org.rajeshkurup.common.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiCallDetailsTest {

    @Test
    public void testApiCallDetails() {
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

        String jsonTxt = apiCallDetails.toString();

        String expJsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 16:11:12.133 UTC","end_ts":"2025/02/28 17:11:12.133 UTC","updated_ts":"2025/02/28 18:11:12.133 UTC"}""";

        Assertions.assertEquals(expJsonTxt, jsonTxt);
        Assertions.assertEquals(1001L, apiCallDetails.getRecId());
        Assertions.assertEquals("/v1/google/search", apiCallDetails.getEndpoint());
        Assertions.assertEquals(1, apiCallDetails.getTier());
        Assertions.assertEquals("lombok", apiCallDetails.getClientId());
        Assertions.assertEquals(updatedTs.minusHours(1).toEpochSecond(), apiCallDetails.getEndTs().toEpochSecond());
        Assertions.assertEquals("POST", apiCallDetails.getHttpMethod());
        Assertions.assertEquals(201, apiCallDetails.getHttpStatus());
        Assertions.assertEquals(updatedTs.minusHours(2).toEpochSecond(), apiCallDetails.getStartTs().toEpochSecond());
        Assertions.assertEquals(updatedTs.toEpochSecond(), apiCallDetails.getUpdatedTs().toEpochSecond());
    }

    @Test
    public void testApiCallDetailsDeepCopy() {
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

        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        String jsonTxt = apiCallDetails2.toString();

        String expJsonTxt = """
                            {"rec_id":1001,"endpoint":"/v1/google/search","client_id":"lombok","http_method":"POST","http_status":201,"tier":1,"start_ts":"2025/02/28 16:11:12.133 UTC","end_ts":"2025/02/28 17:11:12.133 UTC","updated_ts":"2025/02/28 18:11:12.133 UTC"}""";

        Assertions.assertEquals(expJsonTxt, jsonTxt);
        Assertions.assertEquals(1001L, apiCallDetails2.getRecId());
        Assertions.assertEquals("/v1/google/search", apiCallDetails2.getEndpoint());
        Assertions.assertEquals(1, apiCallDetails2.getTier());
        Assertions.assertEquals("lombok", apiCallDetails2.getClientId());
        Assertions.assertEquals(updatedTs.minusHours(1).toEpochSecond(), apiCallDetails2.getEndTs().toEpochSecond());
        Assertions.assertEquals("POST", apiCallDetails2.getHttpMethod());
        Assertions.assertEquals(201, apiCallDetails2.getHttpStatus());
        Assertions.assertEquals(updatedTs.minusHours(2).toEpochSecond(), apiCallDetails2.getStartTs().toEpochSecond());
        Assertions.assertEquals(updatedTs.toEpochSecond(), apiCallDetails2.getUpdatedTs().toEpochSecond());
    }

}
