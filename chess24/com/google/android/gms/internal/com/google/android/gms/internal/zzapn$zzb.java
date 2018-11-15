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

public static class zzapn.zzb
extends zzapn<Integer> {
    public zzapn.zzb(int n, String string, Integer n2) {
        super(n, string, n2, null);
    }

    @Override
    public /* synthetic */ Object zza(zzapq zzapq2) {
        return this.zzc(zzapq2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Integer zzc(zzapq zzapq2) {
        int n;
        try {
            n = zzapq2.getIntFlagValue(this.getKey(), (Integer)this.zzfm(), this.getSource());
        }
        catch (RemoteException remoteException) {
            return (Integer)this.zzfm();
        }
        return n;
    }
}
