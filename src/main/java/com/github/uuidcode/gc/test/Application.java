package com.github.uuidcode.gc.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {
    private Map<Long, List<Integer>> map = new HashMap<>();

    private boolean fullGC = false;
    private boolean callSystemGC = false;

    public Application setCallSystemGC(boolean callSystemGC) {
        this.callSystemGC = callSystemGC;
        return this;
    }

    public Application setFullGC(boolean fullGC) {
        this.fullGC = fullGC;
        return this;
    }

    public void run() {
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            List<Integer> list = IntStream.range(0, 100_000)
                .boxed()
                .collect(Collectors.toList());

            this.addData(i, list);

            this.sleep();
        }
    }

    private void addData(long i, List<Integer> list) {
        int mapSize = this.getMapSize();

        if (map.size() < mapSize) {
            map.put(i, list);
        } else {
            this.removeDataAndCallSystemGC();
        }
    }

    private void removeDataAndCallSystemGC() {
        if (new Random().nextBoolean()) {
            return;
        }

        map.keySet().stream()
            .findFirst()
            .ifPresent(map::remove);

        if (callSystemGC) {
            System.gc();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (Throwable t) {
        }
    }

    private int getMapSize() {
        if (this.fullGC) {
            return 1000;
        }

        return 100;
    }

    public static void main(String[] args){
        new Application().run();
    }
}
