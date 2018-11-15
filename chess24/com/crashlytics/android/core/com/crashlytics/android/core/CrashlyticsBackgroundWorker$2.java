/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;

class CrashlyticsBackgroundWorker
implements Callable<T> {
    final /* synthetic */ Callable val$callable;

    CrashlyticsBackgroundWorker(Callable callable) {
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
}
