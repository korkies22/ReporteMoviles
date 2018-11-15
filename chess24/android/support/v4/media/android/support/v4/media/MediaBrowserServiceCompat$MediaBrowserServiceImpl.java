/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 */
package android.support.v4.media;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;

static interface MediaBrowserServiceCompat.MediaBrowserServiceImpl {
    public Bundle getBrowserRootHints();

    public void notifyChildrenChanged(String var1, Bundle var2);

    public IBinder onBind(Intent var1);

    public void onCreate();

    public void setSessionToken(MediaSessionCompat.Token var1);
}
