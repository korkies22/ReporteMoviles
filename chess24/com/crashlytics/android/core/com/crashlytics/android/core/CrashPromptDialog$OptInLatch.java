/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashPromptDialog;
import java.util.concurrent.CountDownLatch;

private static class CrashPromptDialog.OptInLatch {
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean send = false;

    private CrashPromptDialog.OptInLatch() {
    }

    void await() {
        try {
            this.latch.await();
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    boolean getOptIn() {
        return this.send;
    }

    void setOptIn(boolean bl) {
        this.send = bl;
        this.latch.countDown();
    }
}
