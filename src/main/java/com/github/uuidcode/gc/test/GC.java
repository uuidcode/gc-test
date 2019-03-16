package com.github.uuidcode.gc.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.builder.process.Tail;

import static org.slf4j.LoggerFactory.getLogger;

public class GC {
    protected static Logger logger = getLogger(GC.class);

    private List<String> optionList = new ArrayList<>();
    private String logFileName;
    private boolean fullGC = false;
    private boolean callSystemGC = false;

    public GC setCallSystemGC(boolean callSystemGC) {
        this.callSystemGC = callSystemGC;
        return this;
    }

    public GC setFullGC(boolean fullGC) {
        this.fullGC = fullGC;
        return this;
    }

    public GC() {
        this.logFileName = this.getClass().getSimpleName() + ".log";
        Tail.of().setFile(this.logFileName).start();
    }

    public void addOption(String vmOption) {
        this.optionList.add(vmOption);
    }

    public void runApplication() {
        JavaProcessBuilder javaProcessBuilder = JavaProcessBuilder.of()
            .addOption("-XX:+PrintGCDetails")
            .addOption("-XX:+PrintGCDateStamps")
            .addOption("-Xloggc:" + this.logFileName)
            .addOption("-Xmn256M")
            .addOption("-Xmx1G")
            .addOption(this.optionList)
            .addClasspath("target/classes")
            .setClassName(Application.class);

        if (this.fullGC) {
            javaProcessBuilder.addArgument("fullGC");
        }

        if (this.callSystemGC) {
            javaProcessBuilder.addArgument("callSystemGC");
        }

        javaProcessBuilder.build();
    }
}
