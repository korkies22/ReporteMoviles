/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.example.android.trivialdrivesample.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;

class IabHelper
implements ServiceConnection {
    final /* synthetic */ IabHelper.OnIabSetupFinishedListener val$listener;

    IabHelper(IabHelper.OnIabSetupFinishedListener onIabSetupFinishedListener) {
        this.val$listener = onIabSetupFinishedListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceConnected(ComponentName object, IBinder object2) {
        block8 : {
            if (IabHelper.this.mDisposed) {
                return;
            }
            IabHelper.this.logDebug("Billing service connected.");
            IabHelper.this.mService = IInAppBillingService.Stub.asInterface((IBinder)object2);
            object = IabHelper.this.mContext.getPackageName();
            try {
                IabHelper.this.logDebug("Checking for in-app billing 3 support.");
                int n = IabHelper.this.mService.isBillingSupported(3, (String)object, com.example.android.trivialdrivesample.util.IabHelper.ITEM_TYPE_INAPP);
                if (n != 0) {
                    if (this.val$listener != null) {
                        this.val$listener.onIabSetupFinished(new IabResult(n, "Error checking for billing v3 support."));
                    }
                    IabHelper.this.mSubscriptionsSupported = false;
                    return;
                }
                object2 = IabHelper.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("In-app billing version 3 supported for ");
                stringBuilder.append((String)object);
                object2.logDebug(stringBuilder.toString());
                n = IabHelper.this.mService.isBillingSupported(3, (String)object, com.example.android.trivialdrivesample.util.IabHelper.ITEM_TYPE_SUBS);
                if (n == 0) {
                    IabHelper.this.logDebug("Subscriptions AVAILABLE.");
                    IabHelper.this.mSubscriptionsSupported = true;
                } else {
                    object = IabHelper.this;
                    object2 = new StringBuilder();
                    object2.append("Subscriptions NOT AVAILABLE. Response: ");
                    object2.append(n);
                    object.logDebug(object2.toString());
                }
                IabHelper.this.mSetupDone = true;
                if (this.val$listener == null) break block8;
            }
            catch (RemoteException remoteException) {
                if (this.val$listener != null) {
                    this.val$listener.onIabSetupFinished(new IabResult(-1001, "RemoteException while setting up in-app billing."));
                }
                remoteException.printStackTrace();
                return;
            }
            this.val$listener.onIabSetupFinished(new IabResult(0, "Setup successful."));
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        IabHelper.this.logDebug("Billing service disconnected.");
        IabHelper.this.mService = null;
    }
}
