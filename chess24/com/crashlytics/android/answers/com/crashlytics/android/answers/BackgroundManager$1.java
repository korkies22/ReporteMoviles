/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

class BackgroundManager
implements Runnable {
    BackgroundManager() {
    }

    @Override
    public void run() {
        BackgroundManager.this.backgroundFutureRef.set(null);
        BackgroundManager.this.notifyBackground();
    }
}
