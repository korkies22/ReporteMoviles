/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi23;

static class MediaSessionCompatApi23.CallbackProxy<T extends MediaSessionCompatApi23.Callback>
extends MediaSessionCompatApi21.CallbackProxy<T> {
    public MediaSessionCompatApi23.CallbackProxy(T t) {
        super(t);
    }

    public void onPlayFromUri(Uri uri, Bundle bundle) {
        ((MediaSessionCompatApi23.Callback)this.mCallback).onPlayFromUri(uri, bundle);
    }
}
