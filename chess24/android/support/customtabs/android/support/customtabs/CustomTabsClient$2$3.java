/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.customtabs;

import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;

class CustomTabsClient
implements Runnable {
    final /* synthetic */ Bundle val$extras;

    CustomTabsClient(Bundle bundle) {
        this.val$extras = bundle;
    }

    @Override
    public void run() {
        2.this.val$callback.onMessageChannelReady(this.val$extras);
    }
}
