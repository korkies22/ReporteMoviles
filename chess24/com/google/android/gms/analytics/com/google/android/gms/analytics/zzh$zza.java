/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.zzh;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

private class zzh.zza
extends ThreadPoolExecutor {
    public zzh.zza() {
        super(1, 1, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
        this.setThreadFactory(new zzh.zzb(null));
        this.allowCoreThreadTimeOut(true);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new FutureTask<T>(runnable, t){

            @Override
            protected void setException(Throwable throwable) {
                Object object = zzh.this.zzabl;
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
        };
    }

}
