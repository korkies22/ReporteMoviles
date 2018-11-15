/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;

public static abstract class zzzv.zza<R extends Result, A extends Api.zzb>
extends zzzx<R>
implements zzzv.zzb<R> {
    private final Api<?> zzawb;
    private final Api.zzc<A> zzayF;

    @Deprecated
    protected zzzv.zza(Api.zzc<A> zzc2, GoogleApiClient googleApiClient) {
        super(zzac.zzb(googleApiClient, (Object)"GoogleApiClient must not be null"));
        this.zzayF = zzac.zzw(zzc2);
        this.zzawb = null;
    }

    protected zzzv.zza(Api<?> api, GoogleApiClient googleApiClient) {
        super(zzac.zzb(googleApiClient, (Object)"GoogleApiClient must not be null"));
        this.zzayF = api.zzuH();
        this.zzawb = api;
    }

    private void zzc(RemoteException remoteException) {
        this.zzA(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    public final Api<?> getApi() {
        return this.zzawb;
    }

    @Override
    public /* synthetic */ void setResult(Object object) {
        super.zzb((Result)object);
    }

    @Override
    public final void zzA(Status status) {
        zzac.zzb(status.isSuccess() ^ true, (Object)"Failed result must not be success");
        this.zzb(this.zzc(status));
    }

    protected abstract void zza(A var1) throws RemoteException;

    @Override
    public final void zzb(A a) throws DeadObjectException {
        try {
            this.zza(a);
            return;
        }
        catch (RemoteException remoteException) {
            this.zzc(remoteException);
            return;
        }
        catch (DeadObjectException deadObjectException) {
            this.zzc((RemoteException)deadObjectException);
            throw deadObjectException;
        }
    }

    public final Api.zzc<A> zzuH() {
        return this.zzayF;
    }
}
