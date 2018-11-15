/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.util.Log
 */
package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

public class TimingMetric {
    private final boolean disabled;
    private long duration;
    private final String eventName;
    private long start;
    private final String tag;

    public TimingMetric(String string, String string2) {
        this.eventName = string;
        this.tag = string2;
        this.disabled = Log.isLoggable((String)string2, (int)2) ^ true;
    }

    private void reportToLog() {
        String string = this.tag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.eventName);
        stringBuilder.append(": ");
        stringBuilder.append(this.duration);
        stringBuilder.append("ms");
        Log.v((String)string, (String)stringBuilder.toString());
    }

    public long getDuration() {
        return this.duration;
    }

    public void startMeasuring() {
        synchronized (this) {
            block4 : {
                boolean bl = this.disabled;
                if (!bl) break block4;
                return;
            }
            this.start = SystemClock.elapsedRealtime();
            this.duration = 0L;
            return;
        }
    }

    public void stopMeasuring() {
        synchronized (this) {
            block6 : {
                block5 : {
                    boolean bl = this.disabled;
                    if (!bl) break block5;
                    return;
                }
                long l = this.duration;
                if (l == 0L) break block6;
                return;
            }
            this.duration = SystemClock.elapsedRealtime() - this.start;
            this.reportToLog();
            return;
        }
    }
}
