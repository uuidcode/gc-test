package com.github.uuidcode.gc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GC {
    private List<String> vmOptionList = new ArrayList<>();

    public GC() {
        this.vmOptionList.add("-XX:+PrintGCDetails");
        this.vmOptionList.add("-XX:+PrintGCApplicationStoppedTime");
        this.vmOptionList.add("-XX:+PrintGCApplicationConcurrentTime");
        this.vmOptionList.add("-XX:+PrintGCDateStamps");
        this.vmOptionList.add("-Xloggc:gc.log");
        this.vmOptionList.add("-Xmn256M");
        this.vmOptionList.add("-Xmx1G");

        printLog();
    }

    public void addVmOption(String vmOption) {
        this.vmOptionList.add(vmOption);
    }

    public String getJavaCommand() {
        String vmOption = this.vmOptionList.stream().collect(Collectors.joining(" "));
        return "java " + vmOption + " -classpath target/classes com.github.uuidcode.gc.test.Application";
    }

    public void run() {
        try {
            Runtime.getRuntime().exec(this.getJavaCommand());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void printLog() {
        File file = new File("gc.log");
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
                }
            }
        }).start();
    }
}
