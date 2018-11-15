/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;

static class CustomTabsSessionToken.MockCallback
extends ICustomTabsCallback.Stub {
    CustomTabsSessionToken.MockCallback() {
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public void extraCallback(String string, Bundle bundle) {
    }

    @Override
    public void onMessageChannelReady(Bundle bundle) {
    }

    @Override
    public void onNavigationEvent(int n, Bundle bundle) {
    }

    @Override
    public void onPostMessage(String string, Bundle bundle) {
    }

    @Override
    public void onRelationshipValidationResult(int n, Uri uri, boolean bl, Bundle bundle) {
    }
}
