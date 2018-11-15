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
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.support.v4.media.session.MediaSessionCompatApi24;

public static interface MediaSessionCompatApi24.Callback
extends MediaSessionCompatApi23.Callback {
    public void onPrepare();

    public void onPrepareFromMediaId(String var1, Bundle var2);

    public void onPrepareFromSearch(String var1, Bundle var2);

    public void onPrepareFromUri(Uri var1, Bundle var2);
}
