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
import android.support.v4.media.session.MediaSessionCompatApi24;

@RequiresApi(value=24)
private class MediaSessionCompat.Callback.StubApi24
extends MediaSessionCompat.Callback.StubApi23
implements MediaSessionCompatApi24.Callback {
    MediaSessionCompat.Callback.StubApi24() {
        super(Callback.this);
    }

    @Override
    public void onPrepare() {
        Callback.this.onPrepare();
    }

    @Override
    public void onPrepareFromMediaId(String string, Bundle bundle) {
        Callback.this.onPrepareFromMediaId(string, bundle);
    }

    @Override
    public void onPrepareFromSearch(String string, Bundle bundle) {
        Callback.this.onPrepareFromSearch(string, bundle);
    }

    @Override
    public void onPrepareFromUri(Uri uri, Bundle bundle) {
        Callback.this.onPrepareFromUri(uri, bundle);
    }
}
