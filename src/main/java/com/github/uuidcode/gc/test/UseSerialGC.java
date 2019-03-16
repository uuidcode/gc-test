package com.github.uuidcode.gc.test;

public class UseSerialGC extends GC {
    public UseSerialGC() {
        this.addOption("-XX:+UseSerialGC");
    }

    public static void main(String[] args){
        new UseSerialGC().runApplication();
    }
}
