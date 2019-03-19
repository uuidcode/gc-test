package com.github.uuidcode.gc.test;

public class UseParallelOldGC extends GC {
    public UseParallelOldGC() {
        this.useParallelOldGC();
    }

    public static void main(String[] args) {
        new UseParallelOldGC().run();
    }
}
