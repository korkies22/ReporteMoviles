/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.ICustomTabsCallback;

class CustomTabsClient
extends ICustomTabsCallback.Stub {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    final /* synthetic */ CustomTabsCallback val$callback;

    CustomTabsClient(CustomTabsCallback customTabsCallback) {
        this.val$callback = customTabsCallback;
    }

    @Override
    public void extraCallback(final String string, final Bundle bundle) throws RemoteException {
        if (this.val$callback == null) {
            return;
        }
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$callback.extraCallback(string, bundle);
            }
        });
    }

    @Override
    public void onMessageChannelReady(final Bundle bundle) throws RemoteException {
        if (this.val$callback == null) {
            return;
        }
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$callback.onMessageChannelReady(bundle);
            }
        });
    }

    @Override
    public void onNavigationEvent(final int n, final Bundle bundle) {
        if (this.val$callback == null) {
            return;
        }
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$callback.onNavigationEvent(n, bundle);
            }
        });
    }

    @Override
    public void onPostMessage(final String string, final Bundle bundle) throws RemoteException {
        if (this.val$callback == null) {
            return;
        }
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$callback.onPostMessage(string, bundle);
            }
        });
    }

    @Override
    public void onRelationshipValidationResult(final int n, final Uri uri, final boolean bl, final @Nullable Bundle bundle) throws RemoteException {
        if (this.val$callback == null) {
            return;
        }
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$callback.onRelationshipValidationResult(n, uri, bl, bundle);
            }
        });
    }

}
