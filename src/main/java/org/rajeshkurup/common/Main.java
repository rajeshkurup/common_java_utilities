package org.rajeshkurup.common;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.LongStream;
import org.rajeshkurup.common.mapper.JsonSerializer;
import org.rajeshkurup.common.model.ApiCallDetails;
import org.rajeshkurup.common.prime.PrimeChecker;
import org.rajeshkurup.common.stack.MinMaxStack;

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


        MinMaxStack<ApiCallDetails> minMaxStack = new MinMaxStack<>();
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());
        apiCallDetails.setRecId(1010L);
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());
        apiCallDetails.setRecId(1020L);
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());
        apiCallDetails.setRecId(1005L);
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());
        apiCallDetails.setRecId(1015L);
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());
        apiCallDetails.setRecId(1004L);
        minMaxStack.push(apiCallDetails);
        System.out.println(minMaxStack.size());

        minMaxStack.peek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.maxPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.minPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        System.out.println();

        minMaxStack.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        minMaxStack.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxStack.size());
        System.out.println();

        long num = 1000000007L;
        Date start = new Date();
        boolean isPrime = PrimeChecker.isPrime(num);
        long timeTakenMs = new Date().getTime() - start.getTime();
        System.out.println(new DecimalFormat("###,###,###,### = ").format(num) + num);
        System.out.println("TimeTakenMs = " + timeTakenMs);
        System.out.println();

        long limit = 1000000007L;
        start = new Date();
        long sum = LongStream.range(1, limit).parallel().sum();
        timeTakenMs = new Date().getTime() - start.getTime();
        System.out.println("Sum = " + sum);
        System.out.println("TimeTakenMs = " + timeTakenMs);
        System.out.println();
    }

}
