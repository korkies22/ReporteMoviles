/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 */
package io.fabric.sdk.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import io.fabric.sdk.android.ActivityLifecycleManager;

class ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper
implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ ActivityLifecycleManager.Callbacks val$callbacks;

    ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper(ActivityLifecycleManager.Callbacks callbacks) {
        this.val$callbacks = callbacks;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.val$callbacks.onActivityCreated(activity, bundle);
    }

    public void onActivityDestroyed(Activity activity) {
        this.val$callbacks.onActivityDestroyed(activity);
    }

    public void onActivityPaused(Activity activity) {
        this.val$callbacks.onActivityPaused(activity);
    }

    public void onActivityResumed(Activity activity) {
        this.val$callbacks.onActivityResumed(activity);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.val$callbacks.onActivitySaveInstanceState(activity, bundle);
    }

    public void onActivityStarted(Activity activity) {
        this.val$callbacks.onActivityStarted(activity);
    }

    public void onActivityStopped(Activity activity) {
        this.val$callbacks.onActivityStopped(activity);
    }
}
