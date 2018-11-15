/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package io.fabric.sdk.android;

import android.util.Log;
import io.fabric.sdk.android.Logger;

public class DefaultLogger
implements Logger {
    private int logLevel;

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public DefaultLogger(int n) {
        this.logLevel = n;
    }

    @Override
    public void d(String string, String string2) {
        this.d(string, string2, null);
    }

    @Override
    public void d(String string, String string2, Throwable throwable) {
        if (this.isLoggable(string, 3)) {
            Log.d((String)string, (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public void e(String string, String string2) {
        this.e(string, string2, null);
    }

    @Override
    public void e(String string, String string2, Throwable throwable) {
        if (this.isLoggable(string, 6)) {
            Log.e((String)string, (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public int getLogLevel() {
        return this.logLevel;
    }

    @Override
    public void i(String string, String string2) {
        this.i(string, string2, null);
    }

    @Override
    public void i(String string, String string2, Throwable throwable) {
        if (this.isLoggable(string, 4)) {
            Log.i((String)string, (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public boolean isLoggable(String string, int n) {
        if (this.logLevel <= n) {
            return true;
        }
        return false;
    }

    @Override
    public void log(int n, String string, String string2) {
        this.log(n, string, string2, false);
    }

    @Override
    public void log(int n, String string, String string2, boolean bl) {
        if (bl || this.isLoggable(string, n)) {
            Log.println((int)n, (String)string, (String)string2);
        }
    }

    @Override
    public void setLogLevel(int n) {
        this.logLevel = n;
    }

    @Override
    public void v(String string, String string2) {
        this.v(string, string2, null);
    }

    @Override
    public void v(String string, String string2, Throwable throwable) {
        if (this.isLoggable(string, 2)) {
            Log.v((String)string, (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public void w(String string, String string2) {
        this.w(string, string2, null);
    }

    @Override
    public void w(String string, String string2, Throwable throwable) {
        if (this.isLoggable(string, 5)) {
            Log.w((String)string, (String)string2, (Throwable)throwable);
        }
    }
}
