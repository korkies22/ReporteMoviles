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
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi23;

@RequiresApi(value=23)
private class MediaSessionCompat.Callback.StubApi23
extends MediaSessionCompat.Callback.StubApi21
implements MediaSessionCompatApi23.Callback {
    MediaSessionCompat.Callback.StubApi23() {
        super(Callback.this);
    }

    @Override
    public void onPlayFromUri(Uri uri, Bundle bundle) {
        Callback.this.onPlayFromUri(uri, bundle);
    }
}
