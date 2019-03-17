package com.github.uuidcode.gc.test;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.github.jamm.MemoryMeter;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.util.CoreUtil;

public class ObjectRunner {
    public static void main(String[] args) throws Exception {
        String memoryMeterJarPath = CoreUtil.getJarPath(MemoryMeter.class);

        String result = JavaProcessBuilder.of()
            .addOption("-javaagent:" + memoryMeterJarPath)
            .addClasspath("target/classes")
            .setClassName(ObjectSize.class)
            .runAndGetResult();

        System.out.println(result);
    }
}
