/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.session.MediaSession
 */
package android.support.v4.media.session;

import android.media.session.MediaSession;
import android.support.annotation.RequiresApi;

@RequiresApi(value=22)
class MediaSessionCompatApi22 {
    MediaSessionCompatApi22() {
    }

    public static void setRatingType(Object object, int n) {
        ((MediaSession)object).setRatingType(n);
    }
}
