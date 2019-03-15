package com.github.uuidcode.gc.test;

public class UseConcMarkSweepGC extends GC {
    public UseConcMarkSweepGC() {
        this.addVmOption("-XX:+UseConcMarkSweepGC");
    }

    public static void main(String[] args) {
        new UseConcMarkSweepGC().run();
    }
}
