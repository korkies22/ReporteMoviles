/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.nsd.NsdManager
 *  android.net.nsd.NsdManager$RegistrationListener
 *  android.net.nsd.NsdServiceInfo
 */
package com.facebook.devicerequests.internal;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

static final class DeviceRequestsHelper
implements NsdManager.RegistrationListener {
    final /* synthetic */ String val$nsdServiceName;
    final /* synthetic */ String val$userCode;

    DeviceRequestsHelper(String string, String string2) {
        this.val$nsdServiceName = string;
        this.val$userCode = string2;
    }

    public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int n) {
        com.facebook.devicerequests.internal.DeviceRequestsHelper.cleanUpAdvertisementService(this.val$userCode);
    }

    public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
        if (!this.val$nsdServiceName.equals(nsdServiceInfo.getServiceName())) {
            com.facebook.devicerequests.internal.DeviceRequestsHelper.cleanUpAdvertisementService(this.val$userCode);
        }
    }

    public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
    }

    public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int n) {
    }
}
