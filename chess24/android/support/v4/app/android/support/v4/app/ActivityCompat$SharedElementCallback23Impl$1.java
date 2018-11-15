/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.SharedElementCallback
 *  android.app.SharedElementCallback$OnSharedElementsReadyListener
 */
package android.support.v4.app;

import android.app.SharedElementCallback;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;

class ActivityCompat.SharedElementCallback23Impl
implements SharedElementCallback.OnSharedElementsReadyListener {
    final /* synthetic */ SharedElementCallback.OnSharedElementsReadyListener val$listener;

    ActivityCompat.SharedElementCallback23Impl(SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        this.val$listener = onSharedElementsReadyListener;
    }

    @Override
    public void onSharedElementsReady() {
        this.val$listener.onSharedElementsReady();
    }
}
