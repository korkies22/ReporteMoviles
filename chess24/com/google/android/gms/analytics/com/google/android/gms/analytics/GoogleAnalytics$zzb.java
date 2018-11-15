/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 */
package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.google.android.gms.analytics.GoogleAnalytics;

@TargetApi(value=14)
class GoogleAnalytics.zzb
implements Application.ActivityLifecycleCallbacks {
    GoogleAnalytics.zzb() {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        GoogleAnalytics.this.zzm(activity);
    }

    public void onActivityStopped(Activity activity) {
        GoogleAnalytics.this.zzn(activity);
    }
}
