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
import android.support.v4.media.session.MediaControllerCompatApi23;

@RequiresApi(value=23)
static class MediaControllerCompat.TransportControlsApi23
extends MediaControllerCompat.TransportControlsApi21 {
    public MediaControllerCompat.TransportControlsApi23(Object object) {
        super(object);
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) {
        MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, uri, bundle);
    }
}
