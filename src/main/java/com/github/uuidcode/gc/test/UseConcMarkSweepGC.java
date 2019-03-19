package com.github.uuidcode.gc.test;

public class UseConcMarkSweepGC extends GC {
    public UseConcMarkSweepGC() {
        this.useConcMarkSweepGC();
    }

    public static void main(String[] args) {
        new UseConcMarkSweepGC().run();
    }
}
