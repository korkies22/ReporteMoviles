/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;

protected final class zzf.zzj
extends zzf.zza {
    public final IBinder zzaDZ;

    @BinderThread
    public zzf.zzj(int n, IBinder iBinder, Bundle bundle) {
        super(zzf.this, n, bundle);
        this.zzaDZ = iBinder;
    }

    @Override
    protected void zzn(ConnectionResult connectionResult) {
        if (zzf.this.zzaDP != null) {
            zzf.this.zzaDP.onConnectionFailed(connectionResult);
        }
        zzf.this.onConnectionFailed(connectionResult);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean zzwZ() {
        String string;
        boolean bl = false;
        try {
            string = this.zzaDZ.getInterfaceDescriptor();
        }
        catch (RemoteException remoteException) {}
        if (!zzf.this.zzev().equals(string)) {
            String string2 = String.valueOf(zzf.this.zzev());
            StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string2).length() + String.valueOf(string).length());
            stringBuilder.append("service descriptor mismatch: ");
            stringBuilder.append(string2);
            stringBuilder.append(" vs. ");
            stringBuilder.append(string);
            Log.e((String)"GmsClient", (String)stringBuilder.toString());
            return false;
        }
        Object t = zzf.this.zzh(this.zzaDZ);
        boolean bl2 = bl;
        if (t == null) return bl2;
        bl2 = bl;
        if (!zzf.this.zza(2, 3, (T)t)) return bl2;
        Bundle bundle = zzf.this.zzud();
        if (zzf.this.zzaDO == null) return true;
        zzf.this.zzaDO.onConnected(bundle);
        return true;
        Log.w((String)"GmsClient", (String)"service probably died");
        return false;
    }
}
