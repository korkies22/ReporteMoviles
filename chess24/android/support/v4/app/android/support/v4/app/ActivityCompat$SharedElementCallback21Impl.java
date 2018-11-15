/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.SharedElementCallback
 *  android.content.Context
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.os.Parcelable
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.view.View;
import java.util.List;
import java.util.Map;

@RequiresApi(value=21)
private static class ActivityCompat.SharedElementCallback21Impl
extends android.app.SharedElementCallback {
    protected SharedElementCallback mCallback;

    ActivityCompat.SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
        this.mCallback = sharedElementCallback;
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        return this.mCallback.onCaptureSharedElementSnapshot(view, matrix, rectF);
    }

    public View onCreateSnapshotView(Context context, Parcelable parcelable) {
        return this.mCallback.onCreateSnapshotView(context, parcelable);
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
        this.mCallback.onMapSharedElements(list, map);
    }

    public void onRejectSharedElements(List<View> list) {
        this.mCallback.onRejectSharedElements(list);
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
        this.mCallback.onSharedElementEnd(list, list2, list3);
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
        this.mCallback.onSharedElementStart(list, list2, list3);
    }
}
