/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.crashlytics.android.core;

import android.os.Looper;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

class CrashlyticsBackgroundWorker {
    private final ExecutorService executorService;

    public CrashlyticsBackgroundWorker(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Future<?> submit(Runnable future) {
        try {
            return this.executorService.submit(new Runnable((Runnable)((Object)future)){
                final /* synthetic */ Runnable val$runnable;
                {
                    this.val$runnable = runnable;
                }

                @Override
                public void run() {
                    try {
                        this.val$runnable.run();
                        return;
                    }
                    catch (Exception exception) {
                        Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
                        return;
                    }
                }
            });
        }
        catch (RejectedExecutionException rejectedExecutionException) {}
        Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    <T> Future<T> submit(Callable<T> future) {
        try {
            return this.executorService.submit(new Callable<T>((Callable)future){
                final /* synthetic */ Callable val$callable;
                {
                    this.val$callable = callable;
                }

                @Override
                public T call() throws Exception {
                    Object v;
                    try {
                        v = this.val$callable.call();
                    }
                    catch (Exception exception) {
                        Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
                        return null;
                    }
                    return (T)v;
                }
            });
        }
        catch (RejectedExecutionException rejectedExecutionException) {}
        Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    <T> T submitAndWait(Callable<T> callable) {
        T t;
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                return this.executorService.submit(callable).get(4L, TimeUnit.SECONDS);
            }
            t = this.executorService.submit(callable).get();
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
            return null;
        }
        catch (RejectedExecutionException rejectedExecutionException) {}
        return t;
        Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
        return null;
    }

}
