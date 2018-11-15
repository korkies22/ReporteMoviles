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
import android.support.v4.media.session.MediaControllerCompatApi23;
import android.support.v4.media.session.MediaControllerCompatApi24;

public static class MediaControllerCompatApi24.TransportControls
extends MediaControllerCompatApi23.TransportControls {
    public static void prepare(Object object) {
        ((MediaController.TransportControls)object).prepare();
    }

    public static void prepareFromMediaId(Object object, String string, Bundle bundle) {
        ((MediaController.TransportControls)object).prepareFromMediaId(string, bundle);
    }

    public static void prepareFromSearch(Object object, String string, Bundle bundle) {
        ((MediaController.TransportControls)object).prepareFromSearch(string, bundle);
    }

    public static void prepareFromUri(Object object, Uri uri, Bundle bundle) {
        ((MediaController.TransportControls)object).prepareFromUri(uri, bundle);
    }
}
