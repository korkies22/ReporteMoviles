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
    final /* synthetic */ String val$message;

    CustomTabsClient(String string, Bundle bundle) {
        this.val$message = string;
        this.val$extras = bundle;
    }

    @Override
    public void run() {
        2.this.val$callback.onPostMessage(this.val$message, this.val$extras);
    }
}
