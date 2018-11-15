/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.FlushReason;

static final class AppEventQueue
implements Runnable {
    final /* synthetic */ FlushReason val$reason;

    AppEventQueue(FlushReason flushReason) {
        this.val$reason = flushReason;
    }

    @Override
    public void run() {
        com.facebook.appevents.AppEventQueue.flushAndWait(this.val$reason);
    }
}
