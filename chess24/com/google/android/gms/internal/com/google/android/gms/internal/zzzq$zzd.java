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
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzabk;
import com.google.android.gms.internal.zzabn;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.tasks.TaskCompletionSource;

public static final class zzzq.zzd<TResult>
extends zzzq {
    private final TaskCompletionSource<TResult> zzayo;
    private final zzabn<Api.zzb, TResult> zzays;
    private final zzabk zzayt;

    public zzzq.zzd(int n, zzabn<Api.zzb, TResult> zzabn2, TaskCompletionSource<TResult> taskCompletionSource, zzabk zzabk2) {
        super(n);
        this.zzayo = taskCompletionSource;
        this.zzays = zzabn2;
        this.zzayt = zzabk2;
    }

    @Override
    public void zza(@NonNull zzaad zzaad2, boolean bl) {
        zzaad2.zza(this.zzayo, bl);
    }

    @Override
    public void zza(zzaap.zza<?> zza2) throws DeadObjectException {
        try {
            this.zzays.zza(zza2.zzvr(), this.zzayo);
            return;
        }
        catch (RemoteException remoteException) {
            this.zzy(zzzq.zza(remoteException));
            return;
        }
        catch (DeadObjectException deadObjectException) {
            throw deadObjectException;
        }
    }

    @Override
    public void zzy(@NonNull Status status) {
        this.zzayo.trySetException(this.zzayt.zzz(status));
    }
}
