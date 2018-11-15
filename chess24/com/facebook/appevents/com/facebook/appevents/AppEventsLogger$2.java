/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

static final class AppEventsLogger
implements Runnable {
    final /* synthetic */ long val$eventTime;
    final /* synthetic */ com.facebook.appevents.AppEventsLogger val$logger;

    AppEventsLogger(com.facebook.appevents.AppEventsLogger appEventsLogger, long l) {
        this.val$logger = appEventsLogger;
        this.val$eventTime = l;
    }

    @Override
    public void run() {
        this.val$logger.logAppSessionSuspendEvent(this.val$eventTime);
    }
}
