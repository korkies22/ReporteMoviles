/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.ResultReceiver
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompat;
import java.lang.ref.WeakReference;

private static class MediaControllerCompat.MediaControllerImplApi21.ExtraBinderRequestResultReceiver
extends ResultReceiver {
    private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;

    public MediaControllerCompat.MediaControllerImplApi21.ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
        super(handler);
        this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(mediaControllerImplApi21);
    }

    protected void onReceiveResult(int n, Bundle bundle) {
        MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerCompat.MediaControllerImplApi21)this.mMediaControllerImpl.get();
        if (mediaControllerImplApi21 != null) {
            if (bundle == null) {
                return;
            }
            mediaControllerImplApi21.mExtraBinder = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
            mediaControllerImplApi21.processPendingCallbacks();
            return;
        }
    }
}
