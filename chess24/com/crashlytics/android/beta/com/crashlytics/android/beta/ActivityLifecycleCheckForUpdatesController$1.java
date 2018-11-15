/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.crashlytics.android.beta;

import android.app.Activity;
import io.fabric.sdk.android.ActivityLifecycleManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class ActivityLifecycleCheckForUpdatesController
extends ActivityLifecycleManager.Callbacks {
    ActivityLifecycleCheckForUpdatesController() {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (ActivityLifecycleCheckForUpdatesController.this.signalExternallyReady()) {
            ActivityLifecycleCheckForUpdatesController.this.executorService.submit(new Runnable(){

                @Override
                public void run() {
                    ActivityLifecycleCheckForUpdatesController.this.checkForUpdates();
                }
            });
        }
    }

}
