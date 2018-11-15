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

public static class zzapn.zzd
extends zzapn<String> {
    public zzapn.zzd(int n, String string, String string2) {
        super(n, string, string2, null);
    }

    @Override
    public /* synthetic */ Object zza(zzapq zzapq2) {
        return this.zze(zzapq2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String zze(zzapq object) {
        try {
            return object.getStringFlagValue(this.getKey(), (String)this.zzfm(), this.getSource());
        }
        catch (RemoteException remoteException) {
            return (String)this.zzfm();
        }
    }
}
