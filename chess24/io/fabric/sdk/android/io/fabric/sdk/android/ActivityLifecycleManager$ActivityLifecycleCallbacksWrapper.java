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
package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import io.fabric.sdk.android.ActivityLifecycleManager;
import java.util.HashSet;
import java.util.Set;

private static class ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper {
    private final Application application;
    private final Set<Application.ActivityLifecycleCallbacks> registeredCallbacks = new HashSet<Application.ActivityLifecycleCallbacks>();

    ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper(Application application) {
        this.application = application;
    }

    static /* synthetic */ boolean access$000(ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper activityLifecycleCallbacksWrapper, ActivityLifecycleManager.Callbacks callbacks) {
        return activityLifecycleCallbacksWrapper.registerLifecycleCallbacks(callbacks);
    }

    static /* synthetic */ void access$100(ActivityLifecycleManager.ActivityLifecycleCallbacksWrapper activityLifecycleCallbacksWrapper) {
        activityLifecycleCallbacksWrapper.clearCallbacks();
    }

    @TargetApi(value=14)
    private void clearCallbacks() {
        for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : this.registeredCallbacks) {
            this.application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    @TargetApi(value=14)
    private boolean registerLifecycleCallbacks(ActivityLifecycleManager.Callbacks object) {
        if (this.application != null) {
            object = new Application.ActivityLifecycleCallbacks((ActivityLifecycleManager.Callbacks)object){
                final /* synthetic */ ActivityLifecycleManager.Callbacks val$callbacks;
                {
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
            };
            this.application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)object);
            this.registeredCallbacks.add((Application.ActivityLifecycleCallbacks)object);
            return true;
        }
        return false;
    }

}
