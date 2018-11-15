/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.FlushReason;
import java.util.concurrent.ScheduledFuture;

static final class AppEventQueue
implements Runnable {
    AppEventQueue() {
    }

    @Override
    public void run() {
        com.facebook.appevents.AppEventQueue.scheduledFuture = null;
        if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
            com.facebook.appevents.AppEventQueue.flushAndWait(FlushReason.TIMER);
        }
    }
}
