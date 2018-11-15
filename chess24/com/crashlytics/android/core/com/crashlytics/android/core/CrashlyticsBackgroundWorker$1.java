/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;

class CrashlyticsBackgroundWorker
implements Runnable {
    final /* synthetic */ Runnable val$runnable;

    CrashlyticsBackgroundWorker(Runnable runnable) {
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
}
