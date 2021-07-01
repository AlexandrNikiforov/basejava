package ru.javawebinar.basejava.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamsHW12part2 {

    /* реализовать метод List<Integer> oddOrEven(List<Integer> integers)
     * если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
     * Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
     */
    public static void main(String[] args) {
        List<Integer> intList1 = Stream.of(1, 2, 3, 4, 5, 6).collect(toList()); //sum = 21
        List<Integer> intList2 = Stream.of(2, 2, 3, 4, 5).collect(toList()); //sum = 16
        List<Integer> result1 = oddOrEven4(intList1);
        List<Integer> result2 = oddOrEven4(intList2);

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
//WORKS
//    private static List<Integer> oddOrEven(List<Integer> integers) {
//        Integer sum = integers.stream().reduce(0, Integer::sum);
//
//        return integers.stream().filter((a) -> sum % 2 == 0 && a % 2 != 0 ||
//                sum % 2 != 0 && a % 2 == 0)
//                .collect(toList());
//    }
//
//    private static List<Integer> oddOrEven2(List<Integer> integers) {
//        Integer sum = integers.stream().reduce(0, Integer::sum);
//
//        return (List<Integer>) integers.stream().flatMap(a -> Stream.of(a, a+a)).mapToInt(a -> a)
//       .collect(Collectors.toList(Integer::new);
//    }

    private static List<Integer> oddOrEven3(List<Integer> integers) {
        class IntegerWrapper {
            private int value = 0;
        }

        final IntegerWrapper sum = new IntegerWrapper();

        return integers.stream()
                .peek((a) -> sum.value += a)
                .filter((a) -> {
                    return sum.value % 2 == 0 && a % 2 != 0 ||
                            sum.value % 2 != 0 && a % 2 == 0;
                })
                .collect(toList());
    }

    private static List<Integer> oddOrEven4(List<Integer> integers) {
        final Map<Boolean, List<Integer>> oddsAndEvens = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        return oddsAndEvens.get(oddsAndEvens.get(false).size() % 2 != 0);
    }

//    SINCE JAVA 12
//    private static List<Integer> oddOrEven5(List<Integer> integers) {
//
//        return integers.stream().collect(Collectors.teeing(
//                Collectors.filtering(i -> i % 2 == 0,
//                        Collectors.toList()),
//                Collectors.filtering(i -> i % 2 == 1,
//                        Collectors.toList()),
//                (even, odd) -> odd.size() % 2 == 1 ? even : odd));
//
//    }


}
