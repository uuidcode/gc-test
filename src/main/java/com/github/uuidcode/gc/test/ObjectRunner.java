package com.github.uuidcode.gc.test;

import org.github.jamm.MemoryMeter;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.util.CoreUtil;

public class ObjectRunner {
    public static void main(String[] args) {
        String memoryMeterJarPath = CoreUtil.getJarPath(MemoryMeter.class);

        String result = JavaProcessBuilder.of()
            .javaagent(memoryMeterJarPath)
            .addDefaultClasspath()
            .setClassName(ObjectSize.class)
            .runAndGetResult();

        System.out.println(result);
    }
}
