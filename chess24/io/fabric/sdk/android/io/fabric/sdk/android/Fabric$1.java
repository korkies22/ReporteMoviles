/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package io.fabric.sdk.android;

import android.app.Activity;
import android.os.Bundle;
import io.fabric.sdk.android.ActivityLifecycleManager;

class Fabric
extends ActivityLifecycleManager.Callbacks {
    Fabric() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Fabric.this.setCurrentActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Fabric.this.setCurrentActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Fabric.this.setCurrentActivity(activity);
    }
}
