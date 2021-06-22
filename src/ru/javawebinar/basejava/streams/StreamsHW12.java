package ru.javawebinar.basejava.streams;

import java.util.Arrays;

public class StreamsHW12 {
    public static void main(String[] args) {
        int[] testIntArray1 = {2, 2, 1, 3,};
        int[] testIntArray2 = {9, 8};

        System.out.println("Result with reduce(): " + minValue(testIntArray2));
        System.out.println("Result with reduce(): " + minValue(testIntArray1) + "\n");
        System.out.println("Result with forEach(): " + minValueWithForEach(testIntArray1));
        System.out.println("Result with forEach(): " + minValueWithForEach(testIntArray2)+ "\n");
        System.out.println("Result with for loop: " + minValueWithoutStreams(testIntArray1));
        System.out.println("Result with for loop: " + minValueWithoutStreams(testIntArray2) + "\n");
        System.out.println("Result with bitwise operations: " + minValueWithBitwiseOperations(testIntArray1));
        System.out.println("Result with bitwise operations: " + minValueWithBitwiseOperations(testIntArray2) + "\n");

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()                   // unique digits
                .sorted()                     // sorted digits (descending)
                .reduce((l, r) -> l * 10 + r) // shifts a digit and adds another one
                .orElseThrow(RuntimeException::new);
    }

    private static int minValueWithoutStreams(int[] values) {
        int result = 0;
        int capacity = 1;
        int[] ints = Arrays.stream(values).distinct().sorted().toArray();

        for (int i = ints.length - 1; i >= 0; i--) {

            result = result + ints[i] * capacity;
            capacity = capacity * 10;
        }

        return result;
    }

    private static int minValueWithForEach(int[] values) {
        final int[] result = {0};

        Arrays.stream(values).boxed().distinct().sorted()
                .mapToInt(Integer::intValue).forEach(i -> result[0] = i + result[0] * 10);
        return result[0];
    }

    private static int minValueWithBitwiseOperations(int[] values) {
        int allDigits = 0;
        for (int i : values) allDigits |= 1 << i;

        int result = 0;
        for (int digit = 1; digit < 10; digit++)
            if ((allDigits & (1 << digit)) != 0) result = result * 10 + digit;

        return result;
    }
}
