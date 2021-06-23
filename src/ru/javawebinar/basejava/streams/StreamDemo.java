package ru.javawebinar.basejava.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {

        List<Integer> integers = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());

//        integers.stream().map()

    }


}
