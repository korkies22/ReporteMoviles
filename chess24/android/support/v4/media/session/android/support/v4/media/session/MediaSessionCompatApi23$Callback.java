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

public static interface MediaSessionCompatApi23.Callback
extends MediaSessionCompatApi21.Callback {
    public void onPlayFromUri(Uri var1, Bundle var2);
}
