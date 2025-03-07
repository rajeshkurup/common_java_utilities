package org.rajeshkurup.common.stack;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rajeshkurup.common.model.ApiCallDetails;

public class StackExTest {

    private StackEx<ApiCallDetails> stackEx;

    @BeforeEach
    public void init() {
        this.stackEx = new StackEx<>();
    }

    @AfterEach
    public void cleanup() {
        this.stackEx = null;
    }

    @Test
    public void testPushPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1015L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());
    }

    @Test
    public void testPushFail() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        Assertions.assertFalse(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());
    }

    @Test
    public void testPushFailNull() {
        Assertions.assertEquals(0, stackEx.size());
        Assertions.assertThrows(NullPointerException.class, () -> stackEx.push(null));
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testPeekPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.peek();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(1, stackEx.size());
        Assertions.assertEquals(1001L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testPeekFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.peek();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testMaxPeekPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1015L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.maxPeek();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(6, stackEx.size());
        Assertions.assertEquals(1020L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testMaxPeekFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.maxPeek();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testMinPeekPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(901L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.minPeek();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(6, stackEx.size());
        Assertions.assertEquals(901L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testMinPeekFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.minPeek();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testPopPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(901L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.pop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(5, stackEx.size());
        Assertions.assertEquals(1007L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testPopFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.pop();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testMaxPopPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(901L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.maxPop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(5, stackEx.size());
        Assertions.assertEquals(1020L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testMaxPopFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.maxPop();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testMinPopPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1015L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.minPop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(5, stackEx.size());
        Assertions.assertEquals(1001L, apiCallDetailOpt.get().getRecId());
    }

    @Test
    public void testMinPopFail() {
        Assertions.assertEquals(0, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.minPop();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
    }

    @Test
    public void testPopMinMaxPopPass() {
        Assertions.assertEquals(0, stackEx.size());

        ApiCallDetails apiCallDetails = buildApiCallDetails();
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(1, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1010L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(2, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1005L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(3, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1020L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(4, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1015L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(5, stackEx.size());

        apiCallDetails = apiCallDetails.deepCopy();
        apiCallDetails.setRecId(1007L);
        Assertions.assertTrue(stackEx.push(apiCallDetails));
        Assertions.assertEquals(6, stackEx.size());

        Optional<ApiCallDetails> apiCallDetailOpt = stackEx.minPop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(5, stackEx.size());
        Assertions.assertEquals(1001L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.pop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(4, stackEx.size());
        Assertions.assertEquals(1007L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.maxPop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(3, stackEx.size());
        Assertions.assertEquals(1020L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.pop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(2, stackEx.size());
        Assertions.assertEquals(1015L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.minPop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(1, stackEx.size());
        Assertions.assertEquals(1005L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.pop();
        Assertions.assertTrue(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
        Assertions.assertEquals(1010L, apiCallDetailOpt.get().getRecId());

        apiCallDetailOpt = stackEx.maxPop();
        Assertions.assertFalse(apiCallDetailOpt.isPresent());
        Assertions.assertEquals(0, stackEx.size());
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
