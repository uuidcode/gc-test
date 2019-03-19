package com.github.uuidcode.gc.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.uuidcode.builder.process.JavaProcessBuilder;
import com.github.uuidcode.builder.process.MavenProcessBuilder;
import com.github.uuidcode.builder.process.Tail;

public class GC extends JavaProcessBuilder {
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

    public Process run() {
        List<String> libraryPathList = MavenProcessBuilder.of()
            .getLibraryPathList();

        this.printGCDetails()
            .printGCDateStamps()
            .loggc(this.logFileName)
            .XmnMega(256)
            .XmxGiga(1)
            .addDefaultClasspath()
            .addClasspath(libraryPathList)
            .setClassName(Application.class);

        this.modeSet.stream()
            .map(Enum::name)
            .forEach(this::addArgument);

        return super.run();
    }
}
