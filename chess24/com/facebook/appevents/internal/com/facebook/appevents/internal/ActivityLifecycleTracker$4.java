/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.appevents.internal;

import android.content.Context;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SessionLogger;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (currentSession == null) {
            currentSession = new SessionInfo(this.val$currentTime, null);
        }
        currentSession.setSessionLastEventTime(this.val$currentTime);
        if (foregroundActivityCount.get() <= 0) {
            Runnable runnable = new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (foregroundActivityCount.get() <= 0) {
                        SessionLogger.logDeactivateApp(4.this.val$applicationContext, 4.this.val$activityName, currentSession, appId);
                        SessionInfo.clearSavedSessionFromDisk();
                        currentSession = null;
                    }
                    Object object = currentFutureLock;
                    synchronized (object) {
                        currentFuture = null;
                        return;
                    }
                }
            };
            Object object = currentFutureLock;
            synchronized (object) {
                currentFuture = singleThreadExecutor.schedule(runnable, (long)com.facebook.appevents.internal.ActivityLifecycleTracker.getSessionTimeoutInSeconds(), TimeUnit.SECONDS);
            }
        }
        long l = currentActivityAppearTime;
        long l2 = 0L;
        if (l > 0L) {
            l2 = (this.val$currentTime - l) / 1000L;
        }
        AutomaticAnalyticsLogger.logActivityTimeSpentEvent(this.val$activityName, l2);
        currentSession.writeSessionToDisk();
    }

}
