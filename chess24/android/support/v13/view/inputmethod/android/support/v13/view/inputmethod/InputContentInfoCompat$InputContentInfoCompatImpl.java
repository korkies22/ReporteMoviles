/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ClipDescription
 *  android.net.Uri
 */
package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.view.inputmethod.InputContentInfoCompat;

private static interface InputContentInfoCompat.InputContentInfoCompatImpl {
    @NonNull
    public Uri getContentUri();

    @NonNull
    public ClipDescription getDescription();

    @Nullable
    public Object getInputContentInfo();

    @Nullable
    public Uri getLinkUri();

    public void releasePermission();

    public void requestPermission();
}
