/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android;

import io.fabric.sdk.android.Logger;

public class SilentLogger
implements Logger {
    private int logLevel = 7;

    @Override
    public void d(String string, String string2) {
    }

    @Override
    public void d(String string, String string2, Throwable throwable) {
    }

    @Override
    public void e(String string, String string2) {
    }

    @Override
    public void e(String string, String string2, Throwable throwable) {
    }

    @Override
    public int getLogLevel() {
        return this.logLevel;
    }

    @Override
    public void i(String string, String string2) {
    }

    @Override
    public void i(String string, String string2, Throwable throwable) {
    }

    @Override
    public boolean isLoggable(String string, int n) {
        return false;
    }

    @Override
    public void log(int n, String string, String string2) {
    }

    @Override
    public void log(int n, String string, String string2, boolean bl) {
    }

    @Override
    public void setLogLevel(int n) {
    }

    @Override
    public void v(String string, String string2) {
    }

    @Override
    public void v(String string, String string2, Throwable throwable) {
    }

    @Override
    public void w(String string, String string2) {
    }

    @Override
    public void w(String string, String string2, Throwable throwable) {
    }
}
