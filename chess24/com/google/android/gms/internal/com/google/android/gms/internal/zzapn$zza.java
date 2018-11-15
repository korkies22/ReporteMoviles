/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzapn;
import com.google.android.gms.internal.zzapq;

public static class zzapn.zza
extends zzapn<Boolean> {
    public zzapn.zza(int n, String string, Boolean bl) {
        super(n, string, bl, null);
    }

    @Override
    public /* synthetic */ Object zza(zzapq zzapq2) {
        return this.zzb(zzapq2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Boolean zzb(zzapq zzapq2) {
        boolean bl;
        try {
            bl = zzapq2.getBooleanFlagValue(this.getKey(), (Boolean)this.zzfm(), this.getSource());
        }
        catch (RemoteException remoteException) {
            return (Boolean)this.zzfm();
        }
        return bl;
    }
}
