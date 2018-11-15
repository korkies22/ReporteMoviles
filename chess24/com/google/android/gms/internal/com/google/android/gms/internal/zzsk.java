/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.internal.zzsq;

class zzsk
implements Logger {
    private boolean zzaaK;
    private int zzaeb = 2;

    zzsk() {
    }

    @Override
    public void error(Exception exception) {
    }

    @Override
    public void error(String string) {
    }

    @Override
    public int getLogLevel() {
        return this.zzaeb;
    }

    @Override
    public void info(String string) {
    }

    @Override
    public void setLogLevel(int n) {
        this.zzaeb = n;
        if (!this.zzaaK) {
            String string = zzsq.zzaek.get();
            String string2 = zzsq.zzaek.get();
            StringBuilder stringBuilder = new StringBuilder(91 + String.valueOf(string2).length());
            stringBuilder.append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            stringBuilder.append(string2);
            stringBuilder.append(" DEBUG");
            Log.i((String)string, (String)stringBuilder.toString());
            this.zzaaK = true;
        }
    }

    @Override
    public void verbose(String string) {
    }

    @Override
    public void warn(String string) {
    }
}
