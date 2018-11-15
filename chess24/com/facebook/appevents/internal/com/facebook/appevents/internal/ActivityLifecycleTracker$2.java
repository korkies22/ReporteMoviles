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
import com.facebook.appevents.internal.SourceApplicationInfo;

static final class ActivityLifecycleTracker
implements Runnable {
    final /* synthetic */ String val$activityName;
    final /* synthetic */ Context val$applicationContext;
    final /* synthetic */ long val$currentTime;
    final /* synthetic */ SourceApplicationInfo val$sourceApplicationInfo;

    ActivityLifecycleTracker(Context context, String string, long l, SourceApplicationInfo sourceApplicationInfo) {
        this.val$applicationContext = context;
        this.val$activityName = string;
        this.val$currentTime = l;
        this.val$sourceApplicationInfo = sourceApplicationInfo;
    }

    @Override
    public void run() {
        if (currentSession == null) {
            SessionInfo sessionInfo = SessionInfo.getStoredSessionInfo();
            if (sessionInfo != null) {
                SessionLogger.logDeactivateApp(this.val$applicationContext, this.val$activityName, sessionInfo, appId);
            }
            currentSession = new SessionInfo(this.val$currentTime, null);
            currentSession.setSourceApplicationInfo(this.val$sourceApplicationInfo);
            SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, this.val$sourceApplicationInfo, appId);
        }
    }
}
