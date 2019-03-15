package com.github.uuidcode.gc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Tail extends Thread {
    protected static Logger logger = getLogger(Tail.class);

    private int sleepSecond = 3;
    private File file;

    public Tail setFileName(String fileName) {
        this.file = new File(fileName);
        return this;
    }

    public Tail setSleepSecond(int sleepSecond) {
        this.sleepSecond = sleepSecond;
        return this;
    }

    public static Tail of() {
        return new Tail();
    }

    public void run() {
        this.file.delete();

        BufferedReader reader = null;

        while (true) {
            try {
                Thread.sleep(1000 * this.sleepSecond);
            } catch (Throwable t) {
            }

            try {
                if (this.file.exists()) {
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
