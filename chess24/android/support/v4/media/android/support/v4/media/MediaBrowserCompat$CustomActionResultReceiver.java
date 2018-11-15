/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

private static class MediaBrowserCompat.CustomActionResultReceiver
extends ResultReceiver {
    private final String mAction;
    private final MediaBrowserCompat.CustomActionCallback mCallback;
    private final Bundle mExtras;

    MediaBrowserCompat.CustomActionResultReceiver(String string, Bundle bundle, MediaBrowserCompat.CustomActionCallback customActionCallback, Handler handler) {
        super(handler);
        this.mAction = string;
        this.mExtras = bundle;
        this.mCallback = customActionCallback;
    }

    @Override
    protected void onReceiveResult(int n, Bundle bundle) {
        if (this.mCallback == null) {
            return;
        }
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown result code: ");
                stringBuilder.append(n);
                stringBuilder.append(" (extras=");
                stringBuilder.append((Object)this.mExtras);
                stringBuilder.append(", resultData=");
                stringBuilder.append((Object)bundle);
                stringBuilder.append(")");
                Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                return;
            }
            case 1: {
                this.mCallback.onProgressUpdate(this.mAction, this.mExtras, bundle);
                return;
            }
            case 0: {
                this.mCallback.onResult(this.mAction, this.mExtras, bundle);
                return;
            }
            case -1: 
        }
        this.mCallback.onError(this.mAction, this.mExtras, bundle);
    }
}
