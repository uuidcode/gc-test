package com.github.uuidcode.gc.test;

public class UseG1GC extends GC {
    public UseG1GC() {
        this.useG1GC();
    }

    public static void main(String[] args) {
        new UseG1GC().run();
    }
}
