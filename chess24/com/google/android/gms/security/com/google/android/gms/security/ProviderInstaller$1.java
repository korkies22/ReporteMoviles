/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 */
package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zzc;
import com.google.android.gms.security.ProviderInstaller;

final class ProviderInstaller
extends AsyncTask<Void, Void, Integer> {
    final /* synthetic */ ProviderInstaller.ProviderInstallListener zzbCc;

    ProviderInstaller(ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.zzbCc = providerInstallListener;
    }

    protected /* synthetic */ Object doInBackground(Object[] arrobject) {
        return this.zzc((Void[])arrobject);
    }

    protected /* synthetic */ void onPostExecute(Object object) {
        this.zzg((Integer)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Integer zzc(Void ... arrvoid) {
        int n;
        try {
            com.google.android.gms.security.ProviderInstaller.installIfNeeded(Context.this);
            n = 0;
        }
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            n = googlePlayServicesNotAvailableException.errorCode;
            return n;
        }
        catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
            n = googlePlayServicesRepairableException.getConnectionStatusCode();
            return n;
        }
        do {
            return n;
            break;
        } while (true);
    }

    protected void zzg(Integer n) {
        if (n == 0) {
            this.zzbCc.onProviderInstalled();
            return;
        }
        Intent intent = zzbCa.zzb(Context.this, n, "pi");
        this.zzbCc.onProviderInstallFailed(n, intent);
    }
}
