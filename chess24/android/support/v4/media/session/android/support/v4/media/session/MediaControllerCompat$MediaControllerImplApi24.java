/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package android.support.v4.media.session;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;

@RequiresApi(value=24)
static class MediaControllerCompat.MediaControllerImplApi24
extends MediaControllerCompat.MediaControllerImplApi23 {
    public MediaControllerCompat.MediaControllerImplApi24(Context context, MediaSessionCompat.Token token) throws RemoteException {
        super(context, token);
    }

    public MediaControllerCompat.MediaControllerImplApi24(Context context, MediaSessionCompat mediaSessionCompat) {
        super(context, mediaSessionCompat);
    }

    @Override
    public MediaControllerCompat.TransportControls getTransportControls() {
        Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
        if (object != null) {
            return new MediaControllerCompat.TransportControlsApi24(object);
        }
        return null;
    }
}
