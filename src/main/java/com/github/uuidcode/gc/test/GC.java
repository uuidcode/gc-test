package com.github.uuidcode.gc.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static org.slf4j.LoggerFactory.getLogger;

public class GC {
    protected static Logger logger = getLogger(GC.class);

    private List<String> vmOptionList = new ArrayList<>();
    private String logFileName;
    private boolean fullGC = false;
    private boolean callSystemGC = false;
    private String vmOption;
    private String mainClassAndParameter;

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
        this.setDefaultVMOption();
        Tail.of().setFileName(this.logFileName).start();
    }

    private void setDefaultVMOption() {
        this.vmOptionList.add("-XX:+PrintGCDetails");
        this.vmOptionList.add("-XX:+PrintGCApplicationStoppedTime");
        this.vmOptionList.add("-XX:+PrintGCApplicationConcurrentTime");
        this.vmOptionList.add("-XX:+PrintGCDateStamps");
        this.vmOptionList.add("-Xloggc:" + this.logFileName);
        this.vmOptionList.add("-Xmn256M");
        this.vmOptionList.add("-Xmx1G");
    }

    public void addVmOption(String vmOption) {
        this.vmOptionList.add(vmOption);
    }

    public String createJavaCommand() {
        this.vmOption = this.createVmOption();
        this.mainClassAndParameter = this.createMainClassAndParameter();
        String command = this.createInternalJavaCommand();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> createJavaCommand command: {}", CoreUtil.toJson(command));
        }

        return command;
    }

    private String createInternalJavaCommand() {
        return StringStream.of()
                .add("java")
                .add(this.vmOption)
                .add("-classpath")
                .add("target/classes")
                .add(this.mainClassAndParameter)
                .joiningWithSpace();
    }

    private String createVmOption() {
        return StringStream.of(this.vmOptionList).joiningWithSpace();
    }

    private String createMainClassAndParameter() {
        return StringStream.of()
                .add(Application.class.getName())
                .add("fullGC", this.fullGC)
                .add("callSystemGC", this.callSystemGC)
                .joiningWithSpace();
    }

    public void runApplication() {
        try {
            Runtime.getRuntime().exec(this.createJavaCommand());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
