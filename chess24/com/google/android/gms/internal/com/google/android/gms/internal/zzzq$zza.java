/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.tasks.TaskCompletionSource;

private static abstract class zzzq.zza
extends zzzq {
    protected final TaskCompletionSource<Void> zzayo;

    public zzzq.zza(int n, TaskCompletionSource<Void> taskCompletionSource) {
        super(n);
        this.zzayo = taskCompletionSource;
    }

    @Override
    public void zza(@NonNull zzaad zzaad2, boolean bl) {
    }

    @Override
    public final void zza(zzaap.zza<?> zza2) throws DeadObjectException {
        try {
            this.zzb(zza2);
            return;
        }
        catch (RemoteException remoteException) {
            this.zzy(zzzq.zza(remoteException));
            return;
        }
        catch (DeadObjectException deadObjectException) {
            this.zzy(zzzq.zza((RemoteException)deadObjectException));
            throw deadObjectException;
        }
    }

    protected abstract void zzb(zzaap.zza<?> var1) throws RemoteException;

    @Override
    public void zzy(@NonNull Status status) {
        this.zzayo.trySetException(new zza(status));
    }
}
