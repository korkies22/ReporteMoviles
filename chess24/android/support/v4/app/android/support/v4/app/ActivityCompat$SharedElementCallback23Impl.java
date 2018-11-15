/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.SharedElementCallback
 *  android.app.SharedElementCallback$OnSharedElementsReadyListener
 *  android.view.View
 */
package android.support.v4.app;

import android.app.SharedElementCallback;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.view.View;
import java.util.List;

@RequiresApi(value=23)
private static class ActivityCompat.SharedElementCallback23Impl
extends ActivityCompat.SharedElementCallback21Impl {
    ActivityCompat.SharedElementCallback23Impl(SharedElementCallback sharedElementCallback) {
        super(sharedElementCallback);
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        this.mCallback.onSharedElementsArrived(list, list2, new SharedElementCallback.OnSharedElementsReadyListener(){

            @Override
            public void onSharedElementsReady() {
                onSharedElementsReadyListener.onSharedElementsReady();
            }
        });
    }

}
