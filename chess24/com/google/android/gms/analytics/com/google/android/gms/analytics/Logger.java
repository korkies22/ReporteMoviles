/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

@Deprecated
public interface Logger {
    @Deprecated
    public void error(Exception var1);

    @Deprecated
    public void error(String var1);

    @Deprecated
    public int getLogLevel();

    @Deprecated
    public void info(String var1);

    @Deprecated
    public void setLogLevel(int var1);

    @Deprecated
    public void verbose(String var1);

    @Deprecated
    public void warn(String var1);

    @Deprecated
    public static class LogLevel {
        public static final int ERROR = 3;
        public static final int INFO = 1;
        public static final int VERBOSE = 0;
        public static final int WARNING = 2;
    }

}
