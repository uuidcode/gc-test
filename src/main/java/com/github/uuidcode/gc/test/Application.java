package com.github.uuidcode.gc.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {
    private Map<Long, List<Integer>> map = new HashMap<>();

    public void run() {
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            List<Integer> list = IntStream.range(0, 100_000)
                .boxed()
                .collect(Collectors.toList());

            if (map.size() < 100) {
                map.put(i, list);
            } else {
                if (new Random().nextBoolean()) {
                    map.keySet().stream()
                        .findFirst()
                        .ifPresent(map::remove);

//                    System.gc();
                }
            }

            try {
                Thread.sleep(10);
            } catch (Throwable t) {
            }
        }
    }

    public static void main(String[] args){
        new Application().run();
    }
}
