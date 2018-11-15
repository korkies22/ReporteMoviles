/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.zzh;
import java.util.concurrent.FutureTask;

class zzh.zza
extends FutureTask<T> {
    zzh.zza(Runnable runnable, Object object) {
        super(runnable, object);
    }

    @Override
    protected void setException(Throwable throwable) {
        Object object = zza.this.zzabn.zzabl;
        if (object != null) {
            object.uncaughtException(Thread.currentThread(), throwable);
        } else if (Log.isLoggable((String)"GAv4", (int)6)) {
            object = String.valueOf(throwable);
            StringBuilder stringBuilder = new StringBuilder(37 + String.valueOf(object).length());
            stringBuilder.append("MeasurementExecutor: job failed with ");
            stringBuilder.append((String)object);
            Log.e((String)"GAv4", (String)stringBuilder.toString());
        }
        super.setException(throwable);
    }
}
