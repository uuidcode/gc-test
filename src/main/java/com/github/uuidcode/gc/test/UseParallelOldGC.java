package com.github.uuidcode.gc.test;

public class UseParallelOldGC extends GC {
    public UseParallelOldGC() {
        this.addOption("-XX:+UseParallelOldGC");
    }

    public static void main(String[] args) {
        new UseParallelOldGC().run();
    }
}
