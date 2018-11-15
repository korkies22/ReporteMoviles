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
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi24;

@RequiresApi(value=24)
static class MediaControllerCompat.TransportControlsApi24
extends MediaControllerCompat.TransportControlsApi23 {
    public MediaControllerCompat.TransportControlsApi24(Object object) {
        super(object);
    }

    @Override
    public void prepare() {
        MediaControllerCompatApi24.TransportControls.prepare(this.mControlsObj);
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) {
        MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.mControlsObj, string, bundle);
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) {
        MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.mControlsObj, string, bundle);
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) {
        MediaControllerCompatApi24.TransportControls.prepareFromUri(this.mControlsObj, uri, bundle);
    }
}
