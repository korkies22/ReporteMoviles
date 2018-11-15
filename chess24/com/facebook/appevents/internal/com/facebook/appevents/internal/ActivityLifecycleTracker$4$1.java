/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.appevents.internal;

import android.content.Context;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SessionLogger;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

class ActivityLifecycleTracker
implements Runnable {
    ActivityLifecycleTracker() {
    }

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
}
