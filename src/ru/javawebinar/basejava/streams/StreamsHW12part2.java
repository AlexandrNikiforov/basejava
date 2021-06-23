package ru.javawebinar.basejava.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsHW12part2 {

    /* реализовать метод List<Integer> oddOrEven(List<Integer> integers)
     * если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
     * Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
     */
    public static void main(String[] args) {
        List<Integer> intList1 = Stream.of(1, 2, 3, 4, 5, 6).collect(Collectors.toList()); //sum = 21
        List<Integer> intList2 = Stream.of(2, 2, 3, 4, 5).collect(Collectors.toList()); //sum = 16
        List<Integer> result1 = oddOrEven(intList1);
        List<Integer> result2 = oddOrEven(intList2);

        intList1.forEach((x) -> System.out.print(x + " "));
        System.out.println("Sum: " + intList1.stream().reduce(0, Integer::sum));
        System.out.print("Result: ");
        result1.forEach((x) -> System.out.print(x + " "));
        System.out.println("\n---");

        intList2.forEach((x) -> System.out.print(x + " "));
        System.out.println("Sum: " + intList2.stream().reduce(0, Integer::sum));
        System.out.print("Result: ");
        result2.forEach((x) -> System.out.print(x + " "));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Integer sum = integers.stream().reduce(0, Integer::sum);

        return integers.stream().filter((a) -> sum % 2 == 0 && a % 2 != 0 ||
                sum % 2 != 0 && a % 2 == 0)
                .collect(Collectors.toList());
    }
}
