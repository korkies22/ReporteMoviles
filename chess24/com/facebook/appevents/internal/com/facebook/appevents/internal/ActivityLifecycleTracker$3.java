/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.appevents.internal;

import android.content.Context;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SessionLogger;

static final class ActivityLifecycleTracker
implements Runnable {
    final /* synthetic */ String val$activityName;
    final /* synthetic */ Context val$applicationContext;
    final /* synthetic */ long val$currentTime;

    ActivityLifecycleTracker(long l, Context context, String string) {
        this.val$currentTime = l;
        this.val$applicationContext = context;
        this.val$activityName = string;
    }

    @Override
    public void run() {
        if (currentSession == null) {
            currentSession = new SessionInfo(this.val$currentTime, null);
            SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, null, appId);
        } else if (currentSession.getSessionLastEventTime() != null) {
            long l = this.val$currentTime - currentSession.getSessionLastEventTime();
            if (l > (long)(com.facebook.appevents.internal.ActivityLifecycleTracker.getSessionTimeoutInSeconds() * 1000)) {
                SessionLogger.logDeactivateApp(this.val$applicationContext, this.val$activityName, currentSession, appId);
                SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, null, appId);
                currentSession = new SessionInfo(this.val$currentTime, null);
            } else if (l > 1000L) {
                currentSession.incrementInterruptionCount();
            }
        }
        currentSession.setSessionLastEventTime(this.val$currentTime);
        currentSession.writeSessionToDisk();
    }
}
