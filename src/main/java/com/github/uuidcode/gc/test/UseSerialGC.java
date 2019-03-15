package com.github.uuidcode.gc.test;

public class UseSerialGC extends GC {
    public static void main(String[] args) throws Exception {
        UseSerialGC useSerialGC = new UseSerialGC();

        useSerialGC.addVmOption("-XX:+UseSerialGC");
        useSerialGC.addVmOption("-Xmn256M");
        useSerialGC.addVmOption("-Xmx1G");

        Runtime.getRuntime().exec(useSerialGC.getJavaCommand());
    }
}
