package com.github.uuidcode.gc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.vmOptionList.add("-XX:+PrintGCDetails");
        this.vmOptionList.add("-XX:+PrintGCApplicationStoppedTime");
        this.vmOptionList.add("-XX:+PrintGCApplicationConcurrentTime");
        this.vmOptionList.add("-XX:+PrintGCDateStamps");
        this.vmOptionList.add("-Xloggc:" + this.logFileName);
        this.vmOptionList.add("-Xmn256M");
        this.vmOptionList.add("-Xmx1G");

        printLog();
    }

    public void addVmOption(String vmOption) {
        this.vmOptionList.add(vmOption);
    }

    public String getJavaCommand() {
        String vmOption = this.vmOptionList.stream().collect(Collectors.joining(" "));
        String mainClassName = Application.class.getName();

        String mainClassAndParameter = StringStream.of()
            .add(mainClassName)
            .add("fullGC", this.fullGC)
            .add("callSystemGC", this.callSystemGC)
            .joiningWithSpace();

        String command = StringStream.of()
            .add("java")
            .add(vmOption)
            .add("-classpath")
            .add("target/classes")
            .add(mainClassAndParameter)
            .joiningWithSpace();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> getJavaCommand command: {}", CoreUtil.toJson(command));
        }

        return command;
    }

    public void run() {
        try {
            Runtime.getRuntime().exec(this.getJavaCommand());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void printLog() {
        File file = new File(this.logFileName);
        file.delete();

        new Thread(() -> {
            BufferedReader reader = null;

            while (true) {
                try {
                    Thread.sleep(1000 * 5);
                } catch (Throwable t) {
                }

                try {
                    if (file.exists()) {
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                } catch (Throwable t) {
                    if (logger.isErrorEnabled()) {
                        logger.error(">>> GC printLog error", t);
                    }
                } finally {
                    close(reader);
                }
            }
        }).start();
    }

    private void close(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Throwable t) {
                if (logger.isErrorEnabled()) {
                    this.logger.error("error GC printLog", t);
                }
            }
        }
    }
}
