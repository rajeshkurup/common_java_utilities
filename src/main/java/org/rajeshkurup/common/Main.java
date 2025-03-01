package org.rajeshkurup.common;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.rajeshkurup.common.mapper.JsonSerializer;
import org.rajeshkurup.common.model.ApiCallDetails;

public class Main {

    public static void main(String[] args) {
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
        System.out.println(jsonTxt + "\n");

        JsonSerializer<ApiCallDetails> jsonSerializerPst = new JsonSerializer<>(ApiCallDetails.class, "America/Los_Angeles");
        ApiCallDetails apiCallDetails2 = jsonSerializerPst.toObject(jsonTxt);
        System.out.println(jsonSerializerPst.toText(apiCallDetails2) + "\n");

        JsonSerializer<ApiCallDetails> jsonSerializerIst = new JsonSerializer<>(ApiCallDetails.class, "Asia/Kolkata");
        ApiCallDetails apiCallDetails3 = jsonSerializerIst.toObject(jsonTxt);
        System.out.println(jsonSerializerIst.toText(apiCallDetails3) + "\n");
    }

}
