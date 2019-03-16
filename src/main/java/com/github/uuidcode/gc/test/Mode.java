package com.github.uuidcode.gc.test;

public enum Mode {
    FULL_GC, CALL_SYSTEM_GC;

    public static Mode parse(String value) {
        try {
            return Mode.valueOf(value);
        } catch (Throwable t) {
        }

        return null;
    }
}
