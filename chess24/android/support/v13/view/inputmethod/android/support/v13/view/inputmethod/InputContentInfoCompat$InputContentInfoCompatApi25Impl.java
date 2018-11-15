/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ClipDescription
 *  android.net.Uri
 *  android.view.inputmethod.InputContentInfo
 */
package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.view.inputmethod.InputContentInfo;

@RequiresApi(value=25)
private static final class InputContentInfoCompat.InputContentInfoCompatApi25Impl
implements InputContentInfoCompat.InputContentInfoCompatImpl {
    @NonNull
    final InputContentInfo mObject;

    InputContentInfoCompat.InputContentInfoCompatApi25Impl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        this.mObject = new InputContentInfo(uri, clipDescription, uri2);
    }

    InputContentInfoCompat.InputContentInfoCompatApi25Impl(@NonNull Object object) {
        this.mObject = (InputContentInfo)object;
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return this.mObject.getContentUri();
    }

    @NonNull
    @Override
    public ClipDescription getDescription() {
        return this.mObject.getDescription();
    }

    @Nullable
    @Override
    public Object getInputContentInfo() {
        return this.mObject;
    }

    @Nullable
    @Override
    public Uri getLinkUri() {
        return this.mObject.getLinkUri();
    }

    @Override
    public void releasePermission() {
        this.mObject.releasePermission();
    }

    @Override
    public void requestPermission() {
        this.mObject.requestPermission();
    }
}
