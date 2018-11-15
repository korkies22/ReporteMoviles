/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.ICustomTabsCallback;
import android.util.Log;

class CustomTabsSessionToken
extends CustomTabsCallback {
    CustomTabsSessionToken() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void extraCallback(String string, Bundle bundle) {
        try {
            CustomTabsSessionToken.this.mCallbackBinder.extraCallback(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.e((String)android.support.customtabs.CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onMessageChannelReady(Bundle bundle) {
        try {
            CustomTabsSessionToken.this.mCallbackBinder.onMessageChannelReady(bundle);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.e((String)android.support.customtabs.CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onNavigationEvent(int n, Bundle bundle) {
        try {
            CustomTabsSessionToken.this.mCallbackBinder.onNavigationEvent(n, bundle);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.e((String)android.support.customtabs.CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onPostMessage(String string, Bundle bundle) {
        try {
            CustomTabsSessionToken.this.mCallbackBinder.onPostMessage(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.e((String)android.support.customtabs.CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRelationshipValidationResult(int n, Uri uri, boolean bl, Bundle bundle) {
        try {
            CustomTabsSessionToken.this.mCallbackBinder.onRelationshipValidationResult(n, uri, bl, bundle);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.e((String)android.support.customtabs.CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
    }
}
