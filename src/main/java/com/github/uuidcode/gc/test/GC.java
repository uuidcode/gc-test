package com.github.uuidcode.gc.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.builder.process.Tail;

public class GC {
    private List<String> optionList = new ArrayList<>();
    private String logFileName;
    private Set<Mode> modeSet = new HashSet<>();

    public GC addMode(Mode mode) {
        this.modeSet.add(mode);
        return this;
    }

    public GC() {
        this.logFileName = this.getClass().getSimpleName() + ".log";
        Tail.of().setFile(this.logFileName).start();
    }

    public void addOption(String option) {
        this.optionList.add(option);
    }

    public void run() {
        JavaProcessBuilder javaProcessBuilder = JavaProcessBuilder.of()
            .addOption("-XX:+PrintGCDetails")
            .addOption("-XX:+PrintGCDateStamps")
            .addOption("-Xloggc:" + this.logFileName)
            .addOption("-Xmn256M")
            .addOption("-Xmx1G")
            .addOption(this.optionList)
            .addClasspath("target/classes")
            .setClassName(Application.class);

        this.modeSet.stream()
            .map(Enum::name)
            .forEach(javaProcessBuilder::addArgument);

        javaProcessBuilder.build();
    }
}
