/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;

class MediaBrowserCompat.MediaBrowserImplApi21
implements Runnable {
    final /* synthetic */ String val$action;
    final /* synthetic */ MediaBrowserCompat.CustomActionCallback val$callback;
    final /* synthetic */ Bundle val$extras;

    MediaBrowserCompat.MediaBrowserImplApi21(MediaBrowserCompat.CustomActionCallback customActionCallback, String string, Bundle bundle) {
        this.val$callback = customActionCallback;
        this.val$action = string;
        this.val$extras = bundle;
    }

    @Override
    public void run() {
        this.val$callback.onError(this.val$action, this.val$extras, null);
    }
}
