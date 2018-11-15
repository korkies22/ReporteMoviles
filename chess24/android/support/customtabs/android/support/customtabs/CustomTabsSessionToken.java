/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.customtabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.ICustomTabsCallback;
import android.support.v4.app.BundleCompat;
import android.util.Log;

public class CustomTabsSessionToken {
    private static final String TAG = "CustomTabsSessionToken";
    private final CustomTabsCallback mCallback;
    private final ICustomTabsCallback mCallbackBinder;

    CustomTabsSessionToken(ICustomTabsCallback iCustomTabsCallback) {
        this.mCallbackBinder = iCustomTabsCallback;
        this.mCallback = new CustomTabsCallback(){

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
                Log.e((String)CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
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
                Log.e((String)CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
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
                Log.e((String)CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
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
                Log.e((String)CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
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
                Log.e((String)CustomTabsSessionToken.TAG, (String)"RemoteException during ICustomTabsCallback transaction");
            }
        };
    }

    @NonNull
    public static CustomTabsSessionToken createMockSessionTokenForTesting() {
        return new CustomTabsSessionToken(new MockCallback());
    }

    public static CustomTabsSessionToken getSessionTokenFromIntent(Intent intent) {
        if ((intent = BundleCompat.getBinder(intent.getExtras(), "android.support.customtabs.extra.SESSION")) == null) {
            return null;
        }
        return new CustomTabsSessionToken(ICustomTabsCallback.Stub.asInterface((IBinder)intent));
    }

    public boolean equals(Object object) {
        if (!(object instanceof CustomTabsSessionToken)) {
            return false;
        }
        return ((CustomTabsSessionToken)object).getCallbackBinder().equals((Object)this.mCallbackBinder.asBinder());
    }

    public CustomTabsCallback getCallback() {
        return this.mCallback;
    }

    IBinder getCallbackBinder() {
        return this.mCallbackBinder.asBinder();
    }

    public int hashCode() {
        return this.getCallbackBinder().hashCode();
    }

    public boolean isAssociatedWith(CustomTabsSession customTabsSession) {
        return customTabsSession.getBinder().equals(this.mCallbackBinder);
    }

    static class MockCallback
    extends ICustomTabsCallback.Stub {
        MockCallback() {
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

}
