package org.rajeshkurup.common.prime;

import java.util.stream.LongStream;

public class PrimeChecker {

    public static boolean isPrime(final long num) {
        if(num <= 0) {
            throw new IllegalArgumentException("Number is less than or equal to zero!");
        }
        return (num == 1 || LongStream.range(2, (int)Math.sqrt(num)).parallel().noneMatch(divider -> num % divider == 0));
    }

}
