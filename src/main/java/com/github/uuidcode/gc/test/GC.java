package com.github.uuidcode.gc.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.builder.process.MavenProcessBuilder;
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
        List<String> libraryPathList = MavenProcessBuilder.of()
            .getLibraryPathList();

        JavaProcessBuilder javaProcessBuilder = JavaProcessBuilder.of()
            .printGCDetails()
            .printGCDateStamps()
            .loggc(this.logFileName)
            .XmnMega(256)
            .XmxGiga(1)
            .addOption(this.optionList)
            .addDefaultClasspath()
            .addClasspath(libraryPathList)
            .setClassName(Application.class);

        this.modeSet.stream()
            .map(Enum::name)
            .forEach(javaProcessBuilder::addArgument);

        javaProcessBuilder.run();
    }
}
