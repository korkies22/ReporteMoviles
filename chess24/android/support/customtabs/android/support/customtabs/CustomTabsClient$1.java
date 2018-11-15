/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 */
package android.support.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.support.customtabs.CustomTabsServiceConnection;

static final class CustomTabsClient
extends CustomTabsServiceConnection {
    final /* synthetic */ Context val$applicationContext;

    CustomTabsClient(Context context) {
        this.val$applicationContext = context;
    }

    @Override
    public final void onCustomTabsServiceConnected(ComponentName componentName, android.support.customtabs.CustomTabsClient customTabsClient) {
        customTabsClient.warmup(0L);
        this.val$applicationContext.unbindService((ServiceConnection)this);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
    }
}
