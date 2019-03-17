package com.github.uuidcode.gc.test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.jamm.MemoryMeter;

public class ObjectSize {
    public static void main(String[] args) {
        MemoryMeter memoryMeter = new MemoryMeter();
        int i = 1;
        long l = 1;
        byte b = 1;
        short s = 1;
        String str = "Hello";
        int[] ia = {0, 1, 2, 3, 4 ,5 ,6, 7, 8, 9};
        List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());

        System.out.println("byte: " + memoryMeter.measure(b));
        System.out.println("short: " + memoryMeter.measure(s));
        System.out.println("int: " + memoryMeter.measure(i));
        System.out.println("long: " + memoryMeter.measure(l));
        System.out.println("String: " + memoryMeter.measure(str));
        System.out.println("String: " + memoryMeter.measureDeep(str));
        System.out.println("int array: " + memoryMeter.measureDeep(ia));
        System.out.println("int List: " + memoryMeter.measureDeep(list));
    }
}
