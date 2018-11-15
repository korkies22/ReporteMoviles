/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;

class CustomTabsClient
implements Runnable {
    final /* synthetic */ Bundle val$extras;
    final /* synthetic */ int val$relation;
    final /* synthetic */ Uri val$requestedOrigin;
    final /* synthetic */ boolean val$result;

    CustomTabsClient(int n, Uri uri, boolean bl, Bundle bundle) {
        this.val$relation = n;
        this.val$requestedOrigin = uri;
        this.val$result = bl;
        this.val$extras = bundle;
    }

    @Override
    public void run() {
        2.this.val$callback.onRelationshipValidationResult(this.val$relation, this.val$requestedOrigin, this.val$result, this.val$extras);
    }
}
