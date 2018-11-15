/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;

static interface MediaBrowserCompat.MediaBrowserImpl {
    public void connect();

    public void disconnect();

    @Nullable
    public Bundle getExtras();

    public void getItem(@NonNull String var1, @NonNull MediaBrowserCompat.ItemCallback var2);

    @NonNull
    public String getRoot();

    public ComponentName getServiceComponent();

    @NonNull
    public MediaSessionCompat.Token getSessionToken();

    public boolean isConnected();

    public void search(@NonNull String var1, Bundle var2, @NonNull MediaBrowserCompat.SearchCallback var3);

    public void sendCustomAction(@NonNull String var1, Bundle var2, @Nullable MediaBrowserCompat.CustomActionCallback var3);

    public void subscribe(@NonNull String var1, @Nullable Bundle var2, @NonNull MediaBrowserCompat.SubscriptionCallback var3);

    public void unsubscribe(@NonNull String var1, MediaBrowserCompat.SubscriptionCallback var2);
}
