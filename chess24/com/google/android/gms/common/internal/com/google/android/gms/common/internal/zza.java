/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;

public class zza
extends zzr.zza {
    int zzaDw;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Account zza(zzr zzr2) {
        if (zzr2 == null) return null;
        long l = Binder.clearCallingIdentity();
        try {
            zzr2 = zzr2.getAccount();
        }
        catch (Throwable throwable) {}
        Binder.restoreCallingIdentity((long)l);
        return zzr2;
        Binder.restoreCallingIdentity((long)l);
        throw throwable;
        catch (RemoteException remoteException) {}
        {
            Log.w((String)"AccountAccessor", (String)"Remote account accessor probably died");
        }
        Binder.restoreCallingIdentity((long)l);
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zza)) {
            return false;
        }
        throw new NullPointerException();
    }

    @Override
    public Account getAccount() {
        int n = Binder.getCallingUid();
        if (n == this.zzaDw) {
            return null;
        }
        if (zze.zzf(null, n)) {
            this.zzaDw = n;
            return null;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }
}
