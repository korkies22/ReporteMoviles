/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.Iterator;
import java.util.List;

class MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
implements Runnable {
    final /* synthetic */ MediaSessionCompat.Token val$token;

    MediaBrowserServiceCompat.MediaBrowserServiceImplApi21(MediaSessionCompat.Token token) {
        this.val$token = token;
    }

    @Override
    public void run() {
        if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
            IMediaSession iMediaSession = this.val$token.getExtraBinder();
            if (iMediaSession != null) {
                Iterator<Bundle> iterator = MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator();
                while (iterator.hasNext()) {
                    BundleCompat.putBinder(iterator.next(), "extra_session_binder", iMediaSession.asBinder());
                }
            }
            MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
        }
        MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceImplApi21.this.mServiceObj, this.val$token.getToken());
    }
}
