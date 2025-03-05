package org.rajeshkurup.common.stack;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rajeshkurup.common.model.ApiCallDetails;

public class MinMaxStackTest {

    private MinMaxStack<ApiCallDetails> minMaxStack;

    @BeforeEach
    public void init() {
        this.minMaxStack = new MinMaxStack<>();
    }

    @AfterEach
    public void cleanup() {
        this.minMaxStack = null;
    }

    @Test
    public void testPushPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());
    }
    @Test
    public void testPushFail() {
        Assertions.assertThrows(NullPointerException.class, () -> this.minMaxStack.push(null));
    }

    @Test
    public void testPeekPass() {
        this.minMaxStack.push(buildApiCallDetails());
        Optional<ApiCallDetails> apiCallDetails = this.minMaxStack.peek();
        Assertions.assertEquals(1, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails.get().getRecId());
    }

    @Test
    public void testPeekFail() {
        Optional<ApiCallDetails> apiCallDetails = this.minMaxStack.peek();
        Assertions.assertFalse(apiCallDetails.isPresent());
    }

    @Test
    public void testMaxPeekPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.maxPeek();
        Assertions.assertEquals(2, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails3.get().getRecId());
    }

    @Test
    public void testMaxPeekFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.maxPeek();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMinPeekPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.minPeek();
        Assertions.assertEquals(2, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails3.get().getRecId());
    }

    @Test
    public void testMinPeekFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.minPeek();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(1002L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.pop();
        Assertions.assertEquals(1, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1002L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxStack.pop();
        Assertions.assertEquals(0, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails4.get().getRecId());
    }

    @Test
    public void testPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.pop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMaxPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxStack.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxStack.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxStack.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.maxPop();
        Assertions.assertEquals(3, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxStack.maxPop();
        Assertions.assertEquals(2, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxStack.maxPop();
        Assertions.assertEquals(1, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(905L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails8 = this.minMaxStack.maxPop();
        Assertions.assertEquals(0, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails8.isPresent());
        Assertions.assertEquals(901L, apiCallDetails8.get().getRecId());
    }

    @Test
    public void testMaxPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.maxPop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testMinPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxStack.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxStack.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxStack.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.minPop();
        Assertions.assertEquals(3, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(901L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxStack.minPop();
        Assertions.assertEquals(2, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(905L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxStack.minPop();
        Assertions.assertEquals(1, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails8 = this.minMaxStack.minPop();
        Assertions.assertEquals(0, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails8.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails8.get().getRecId());
    }

    @Test
    public void testMinPopFail() {
        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.minPop();
        Assertions.assertFalse(apiCallDetails3.isPresent());
    }

    @Test
    public void testCombinationPopPass() {
        ApiCallDetails apiCallDetails = buildApiCallDetails();
        this.minMaxStack.push(apiCallDetails);
        ApiCallDetails apiCallDetails2 = apiCallDetails.deepCopy();
        apiCallDetails2.setRecId(901L);
        this.minMaxStack.push(apiCallDetails2);
        Assertions.assertEquals(2, this.minMaxStack.size());
        ApiCallDetails apiCallDetails5 = apiCallDetails.deepCopy();
        apiCallDetails5.setRecId(1010L);
        this.minMaxStack.push(apiCallDetails5);
        Assertions.assertEquals(3, this.minMaxStack.size());
        ApiCallDetails apiCallDetails7 = apiCallDetails.deepCopy();
        apiCallDetails7.setRecId(905L);
        this.minMaxStack.push(apiCallDetails7);
        Assertions.assertEquals(4, this.minMaxStack.size());
        ApiCallDetails apiCallDetails8 = apiCallDetails.deepCopy();
        apiCallDetails8.setRecId(801L);
        this.minMaxStack.push(apiCallDetails8);
        Assertions.assertEquals(5, this.minMaxStack.size());
        ApiCallDetails apiCallDetails9 = apiCallDetails.deepCopy();
        apiCallDetails9.setRecId(1020L);
        this.minMaxStack.push(apiCallDetails9);
        Assertions.assertEquals(6, this.minMaxStack.size());

        Optional<ApiCallDetails> apiCallDetails3 = this.minMaxStack.minPop();
        Assertions.assertEquals(5, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails3.isPresent());
        Assertions.assertEquals(801L, apiCallDetails3.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails4 = this.minMaxStack.pop();
        Assertions.assertEquals(4, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails4.isPresent());
        Assertions.assertEquals(1020L, apiCallDetails4.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails6 = this.minMaxStack.maxPop();
        Assertions.assertEquals(3, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails6.isPresent());
        Assertions.assertEquals(1010L, apiCallDetails6.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails10 = this.minMaxStack.pop();
        Assertions.assertEquals(2, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails10.isPresent());
        Assertions.assertEquals(905L, apiCallDetails10.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails11 = this.minMaxStack.minPop();
        Assertions.assertEquals(1, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails11.isPresent());
        Assertions.assertEquals(901L, apiCallDetails11.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails12 = this.minMaxStack.pop();
        Assertions.assertEquals(0, this.minMaxStack.size());
        Assertions.assertTrue(apiCallDetails12.isPresent());
        Assertions.assertEquals(1001L, apiCallDetails12.get().getRecId());

        Optional<ApiCallDetails> apiCallDetails13 = this.minMaxStack.pop();
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
