/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.session.MediaController
 *  android.media.session.MediaController$TransportControls
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaControllerCompatApi23;

public static class MediaControllerCompatApi23.TransportControls
extends MediaControllerCompatApi21.TransportControls {
    public static void playFromUri(Object object, Uri uri, Bundle bundle) {
        ((MediaController.TransportControls)object).playFromUri(uri, bundle);
    }
}
