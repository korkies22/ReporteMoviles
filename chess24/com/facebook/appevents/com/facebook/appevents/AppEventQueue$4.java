/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventCollection;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.FlushReason;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

static final class AppEventQueue
implements Runnable {
    final /* synthetic */ AccessTokenAppIdPair val$accessTokenAppId;
    final /* synthetic */ AppEvent val$appEvent;

    AppEventQueue(AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
        this.val$accessTokenAppId = accessTokenAppIdPair;
        this.val$appEvent = appEvent;
    }

    @Override
    public void run() {
        appEventCollection.addEvent(this.val$accessTokenAppId, this.val$appEvent);
        if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY && appEventCollection.getEventCount() > 100) {
            com.facebook.appevents.AppEventQueue.flushAndWait(FlushReason.EVENT_THRESHOLD);
            return;
        }
        if (scheduledFuture == null) {
            com.facebook.appevents.AppEventQueue.scheduledFuture = singleThreadExecutor.schedule(flushRunnable, 15L, TimeUnit.SECONDS);
        }
    }
}
