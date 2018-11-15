/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabw;
import com.google.android.gms.internal.zzabz;
import com.google.android.gms.internal.zzzv;

private static class zzabz.zza
extends zzabw {
    private final zzzv.zzb<Status> zzaFq;

    public zzabz.zza(zzzv.zzb<Status> zzb2) {
        this.zzaFq = zzb2;
    }

    @Override
    public void zzcX(int n) throws RemoteException {
        this.zzaFq.setResult(new Status(n));
    }
}
