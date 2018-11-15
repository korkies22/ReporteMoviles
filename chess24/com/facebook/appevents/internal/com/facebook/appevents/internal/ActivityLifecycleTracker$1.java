/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 */
package com.facebook.appevents.internal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.Logger;

static final class ActivityLifecycleTracker
implements Application.ActivityLifecycleCallbacks {
    ActivityLifecycleTracker() {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityCreated");
        AppEventUtility.assertIsMainThread();
        com.facebook.appevents.internal.ActivityLifecycleTracker.onActivityCreated(activity);
    }

    public void onActivityDestroyed(Activity activity) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityDestroyed");
    }

    public void onActivityPaused(Activity activity) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityPaused");
        AppEventUtility.assertIsMainThread();
        com.facebook.appevents.internal.ActivityLifecycleTracker.onActivityPaused(activity);
    }

    public void onActivityResumed(Activity activity) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityResumed");
        AppEventUtility.assertIsMainThread();
        com.facebook.appevents.internal.ActivityLifecycleTracker.onActivityResumed(activity);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivitySaveInstanceState");
    }

    public void onActivityStarted(Activity activity) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityStarted");
    }

    public void onActivityStopped(Activity activity) {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityStopped");
        AppEventsLogger.onContextStop();
    }
}
