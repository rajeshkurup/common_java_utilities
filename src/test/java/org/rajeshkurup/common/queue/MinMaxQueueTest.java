package org.rajeshkurup.common.queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rajeshkurup.common.model.ApiCallDetails;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MinMaxQueueTest {

    private MinMaxQueue<ApiCallDetails> minMaxQueue;

    @BeforeEach
    public void init() {
        this.minMaxQueue = new MinMaxQueue<>();
    }

    @AfterEach
    public void cleanup() {
        this.minMaxQueue = null;
    }

    @Test
    public void testPushPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());
    }

    @Test
    public void testPushFail() {
        Assertions.assertThrows(NullPointerException.class, () -> this.minMaxQueue.push(null));
    }

    @Test
    public void testPushFailAlreadyExist() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(1, this.minMaxQueue.size());
    }

    @Test
    public void testPeekPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails3 = apiCallDetails.deepCopy();
        apiCallDetails3.setRecId(901L);
        this.minMaxQueue.push(apiCallDetails3);
        Optional<ApiCallDetails> apiCallDetails2 = this.minMaxQueue.peek();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails2.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails2.get().getRecId());
    }

    @Test
    public void testPeekFail() {
        Optional<ApiCallDetails> apiCallDetails = this.minMaxQueue.peek();
        Assertions.assertFalse(apiCallDetails.isPresent());
    }

    @Test
    public void testMaxPeekPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.maxPeek();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails3.get().getRecId());
    }

    @Test
    public void testMaxPeekFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.maxPeek();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMinPeekPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.minPeek();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails3.get().getRecId());
    }

    @Test
    public void testMinPeekFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.minPeek();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.pop();
        Assertions.assertEquals(1, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxQueue.pop();
        Assertions.assertEquals(0, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1002L, apiCallDetails4.get().getRecId());
    }

    @Test
    public void testPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.pop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMaxPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxQueue.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxQueue.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.maxPop();
        Assertions.assertEquals(3, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxQueue.maxPop();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxQueue.maxPop();
        Assertions.assertEquals(1, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(905L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails8 = this.minMaxQueue.maxPop();
        Assertions.assertEquals(0, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails8.isPresent());
        Assertions.assertEquals(901L, apiCallDetails8.get().getRecId());
    }

    @Test
    public void testMaxPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.maxPop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMinPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxQueue.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxQueue.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.minPop();
        Assertions.assertEquals(3, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(901L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxQueue.minPop();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(905L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxQueue.minPop();
        Assertions.assertEquals(1, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails8 = this.minMaxQueue.minPop();
        Assertions.assertEquals(0, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails8.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails8.get().getRecId());
    }

    @Test
    public void testMinPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.minPop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testCombinationPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxQueue.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxQueue.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxQueue.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxQueue.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails8 = apiCallDetails.deepCopy();
        apiCallDetails8.setRecId(801L);
        this.minMaxQueue.push(apiCallDetails8);
        Assertions.assertEquals(5, this.minMaxQueue.size());
        ApiCallDetails apiCallDetails9 = apiCallDetails.deepCopy();
        apiCallDetails9.setRecId(1020L);
        this.minMaxQueue.push(apiCallDetails9);
        Assertions.assertEquals(6, this.minMaxQueue.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxQueue.minPop();
        Assertions.assertEquals(5, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(801L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxQueue.pop();
        Assertions.assertEquals(4, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxQueue.maxPop();
        Assertions.assertEquals(3, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(1020L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails10 = this.minMaxQueue.pop();
        Assertions.assertEquals(2, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails10.isPresent());
        Assertions.assertEquals(901L, apiCallDetails10.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails11 = this.minMaxQueue.minPop();
        Assertions.assertEquals(1, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails11.isPresent());
        Assertions.assertEquals(905L, apiCallDetails11.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails12 = this.minMaxQueue.pop();
        Assertions.assertEquals(0, this.minMaxQueue.size());
        Assertions.assertTrue(apiCallDetails12.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails12.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails13 = this.minMaxQueue.pop();
        Assertions.assertFalse(apiCallDetails13.isPresent());
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
