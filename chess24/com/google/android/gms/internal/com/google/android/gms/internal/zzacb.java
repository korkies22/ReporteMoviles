/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.internal.zzacd;

public class zzacb
extends zzl<zzacd> {
    public zzacb(Context context, Looper looper, zzg zzg2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 39, zzg2, connectionCallbacks, onConnectionFailedListener);
    }

    protected zzacd zzbz(IBinder iBinder) {
        return zzacd.zza.zzbB(iBinder);
    }

    @Override
    public String zzeu() {
        return "com.google.android.gms.common.service.START";
    }

    @Override
    protected String zzev() {
        return "com.google.android.gms.common.internal.service.ICommonService";
    }

    @Override
    protected /* synthetic */ IInterface zzh(IBinder iBinder) {
        return this.zzbz(iBinder);
    }
}
