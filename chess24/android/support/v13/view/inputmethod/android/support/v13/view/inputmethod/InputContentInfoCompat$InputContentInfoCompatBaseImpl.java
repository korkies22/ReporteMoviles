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

private static final class InputContentInfoCompat.InputContentInfoCompatBaseImpl
implements InputContentInfoCompat.InputContentInfoCompatImpl {
    @NonNull
    private final Uri mContentUri;
    @NonNull
    private final ClipDescription mDescription;
    @Nullable
    private final Uri mLinkUri;

    InputContentInfoCompat.InputContentInfoCompatBaseImpl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        this.mContentUri = uri;
        this.mDescription = clipDescription;
        this.mLinkUri = uri2;
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return this.mContentUri;
    }

    @NonNull
    @Override
    public ClipDescription getDescription() {
        return this.mDescription;
    }

    @Nullable
    @Override
    public Object getInputContentInfo() {
        return null;
    }

    @Nullable
    @Override
    public Uri getLinkUri() {
        return this.mLinkUri;
    }

    @Override
    public void releasePermission() {
    }

    @Override
    public void requestPermission() {
    }
}
