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

public static class zzapn.zzc
extends zzapn<Long> {
    public zzapn.zzc(int n, String string, Long l) {
        super(n, string, l, null);
    }

    @Override
    public /* synthetic */ Object zza(zzapq zzapq2) {
        return this.zzd(zzapq2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Long zzd(zzapq zzapq2) {
        long l;
        try {
            l = zzapq2.getLongFlagValue(this.getKey(), (Long)this.zzfm(), this.getSource());
        }
        catch (RemoteException remoteException) {
            return (Long)this.zzfm();
        }
        return l;
    }
}
