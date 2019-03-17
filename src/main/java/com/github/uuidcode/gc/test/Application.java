package com.github.uuidcode.gc.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.ofNullable;

public class Application {
    private Map<Long, List<Integer>> map = new HashMap<>();

    private Set<Mode> modeSet = new HashSet<>();

    public static Application of() {
        return new Application();
    }

    public Application setModeSet(Set<Mode> modeSet) {
        this.modeSet = modeSet;
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

        if (this.modeSet.contains(Mode.CALL_SYSTEM_GC)) {
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
        if (this.modeSet.contains(Mode.FULL_GC)) {
            return 1000;
        }

        return 100;
    }

    public static void main(String[] args) {
        Set<Mode> modeSet = ofNullable(args)
            .map(Arrays::asList)
            .orElse(new ArrayList<>())
            .stream()
            .map(Mode::parse)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        Application.of().setModeSet(modeSet).run();
    }
}
