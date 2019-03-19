package com.github.uuidcode.gc.test;

public class UseSerialGC extends GC {
    public UseSerialGC() {
        this.useSerialGC();
    }

    public static void main(String[] args){
        new UseSerialGC().run();
    }
}
