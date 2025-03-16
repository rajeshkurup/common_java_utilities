package org.rajeshkurup.common;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

import org.rajeshkurup.common.b64.B64Operations;
import org.rajeshkurup.common.b64.B64Operator;
import org.rajeshkurup.common.fileops.FileOperations;
import org.rajeshkurup.common.fileops.FileOperator;
import org.rajeshkurup.common.list.IntArrayList;
import org.rajeshkurup.common.mapper.JsonSerializer;
import org.rajeshkurup.common.mapper.JsonSerializer.JsonSerializerBuilder;
import org.rajeshkurup.common.model.ApiCallDetails;
import org.rajeshkurup.common.prime.PrimeChecker;
import org.rajeshkurup.common.queue.MinMaxQueue;
import org.rajeshkurup.common.queue.QueueEx;
import org.rajeshkurup.common.queue.Queueable;
import org.rajeshkurup.common.stack.MinMaxStack;
import org.rajeshkurup.common.stack.StackEx;
import org.rajeshkurup.common.stack.Stackable;

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

        JsonSerializer<ApiCallDetails> jsonSerializerPst = JsonSerializerBuilder.builder(ApiCallDetails.class).timeZone("America/Los_Angeles").build();
        ApiCallDetails apiCallDetails2 = jsonSerializerPst.fromJson(jsonTxt);
        System.out.println(jsonSerializerPst.toJson(apiCallDetails2) + "\n");

        JsonSerializer<ApiCallDetails> jsonSerializerIst = JsonSerializerBuilder.builder(ApiCallDetails.class).timeZone("Asia/Kolkata").build();
        ApiCallDetails apiCallDetails3 = jsonSerializerIst.fromJson(jsonTxt);
        System.out.println(jsonSerializerIst.toJson(apiCallDetails3) + "\n");


        Stackable<ApiCallDetails> minMaxStack = new MinMaxStack<>();
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
        System.out.println("isPrime = " + PrimeChecker.isPrime(num));
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

        Queueable<ApiCallDetails> minMaxQueue = new MinMaxQueue<>();
        apiCallDetails.setRecId(1001L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());
        apiCallDetails.setRecId(1010L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());
        apiCallDetails.setRecId(1020L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());
        apiCallDetails.setRecId(1005L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());
        apiCallDetails.setRecId(1015L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());
        apiCallDetails.setRecId(1004L);
        minMaxQueue.push(apiCallDetails);
        System.out.println(minMaxQueue.size());

        minMaxQueue.peek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.maxPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.minPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        System.out.println();

        minMaxQueue.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        minMaxQueue.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(minMaxQueue.size());
        System.out.println();

        StackEx<ApiCallDetails> stackEx = new StackEx<>();
        apiCallDetails.setRecId(1001L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());
        apiCallDetails.setRecId(1010L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());
        apiCallDetails.setRecId(1020L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());
        apiCallDetails.setRecId(1005L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());
        apiCallDetails.setRecId(1015L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());
        apiCallDetails.setRecId(1004L);
        stackEx.push(apiCallDetails);
        System.out.println(stackEx.size());

        stackEx.peek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.maxPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.minPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        System.out.println();

        stackEx.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        stackEx.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(stackEx.size());
        System.out.println();

        QueueEx<ApiCallDetails> queueEx = new QueueEx<>();
        apiCallDetails.setRecId(1001L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());
        apiCallDetails.setRecId(1010L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());
        apiCallDetails.setRecId(1020L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());
        apiCallDetails.setRecId(1005L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());
        apiCallDetails.setRecId(1015L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());
        apiCallDetails.setRecId(1004L);
        queueEx.push(apiCallDetails);
        System.out.println(queueEx.size());

        queueEx.peek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.maxPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.minPeek().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        System.out.println();

        queueEx.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.maxPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.pop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        queueEx.minPop().ifPresent(node -> System.out.println(node.getRecId()));
        System.out.println(queueEx.size());
        System.out.println();

        IntArrayList intArray = new IntArrayList();
        intArray.add(1);
        intArray.add(9);
        intArray.add(5);
        intArray.add(2);
        intArray.add(8);
        intArray.add(7);
        intArray.add(6);
        intArray.add(3);
        intArray.add(4);
        
        System.out.println(intArray.findSubArraySize(2, 15));
        System.out.println(intArray.findSmallestSubArraySize(16));
        System.out.println();

        FileOperations fileOperations = new FileOperator();
        System.out.println("isExist=" + fileOperations.isExist("/Users/rajeshraghavakurup/Desktop/image.png"));
        try {
            System.out.println("size=" + fileOperations.size("/Users/rajeshraghavakurup/Desktop/image.png"));
            System.out.println("listFiles=" + fileOperations.listFiles("/Users/rajeshraghavakurup/Desktop").orElse(null));
            System.out.println("createFolder=" + fileOperations.createFolder("/Users/rajeshraghavakurup/Desktop/myfolder"));
            System.out.println("write=" + fileOperations.write("/Users/rajeshraghavakurup/Desktop/myfolder/myfile.txt", "Hello World", true));
            System.out.println("delete=" + fileOperations.delete("/Users/rajeshraghavakurup/Desktop/myfolder"));
            System.out.println("createFolder2=" + fileOperations.createFolder("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder"));
            System.out.println("write2=" + fileOperations.write("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder/myfile.txt", "Hello World", true));
            System.out.println("copyFile=" + fileOperations.copy("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder/myfile.txt", "/Users/rajeshraghavakurup/Desktop/myfolder"));
            System.out.println("copyFile2=" + fileOperations.copy("/Users/rajeshraghavakurup/Desktop/myfolder", "/Users/rajeshraghavakurup/Desktop/myfolder2"));
            System.out.println("moveFile=" + fileOperations.copy("/Users/rajeshraghavakurup/Desktop/myfolder2", "/Users/rajeshraghavakurup/Desktop/myfolder3"));
            System.out.println("writeLines=" + fileOperations.writeLines("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder3/myfolder2/myfolder/myfile.txt", List.of("Hello World1","Hello World2"), true));
            System.out.println("readLines=" + fileOperations.readLines("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder3/myfolder2/myfolder/myfile.txt").orElse(null));
            System.out.println("read=" + fileOperations.read("/Users/rajeshraghavakurup/Desktop/myfolder/myfolder3/myfolder2/myfolder/myfile.txt").orElse(null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        B64Operations b64Operations = new B64Operator();
        System.out.println("encoded_val=" + b64Operations.encode("Hello World!").orElse(""));
        System.out.println("decoded_val=" + b64Operations.decode("SGVsbG8gV29ybGQh").orElse(""));
    }

}
