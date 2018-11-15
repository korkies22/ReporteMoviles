/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

static final class AppEventsLogger
implements Runnable {
    final /* synthetic */ long val$eventTime;
    final /* synthetic */ com.facebook.appevents.AppEventsLogger val$logger;
    final /* synthetic */ String val$sourceApplicationInfo;

    AppEventsLogger(com.facebook.appevents.AppEventsLogger appEventsLogger, long l, String string) {
        this.val$logger = appEventsLogger;
        this.val$eventTime = l;
        this.val$sourceApplicationInfo = string;
    }

    @Override
    public void run() {
        this.val$logger.logAppSessionResumeEvent(this.val$eventTime, this.val$sourceApplicationInfo);
    }
}
