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
    final /* synthetic */ Bundle val$args;
    final /* synthetic */ String val$callbackName;

    CustomTabsClient(String string, Bundle bundle) {
        this.val$callbackName = string;
        this.val$args = bundle;
    }

    @Override
    public void run() {
        2.this.val$callback.extraCallback(this.val$callbackName, this.val$args);
    }
}
