/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabz;
import com.google.android.gms.internal.zzaca;
import com.google.android.gms.internal.zzacb;
import com.google.android.gms.internal.zzacc;
import com.google.android.gms.internal.zzacd;
import com.google.android.gms.internal.zzzv;

class zzabz
extends zzaca.zza {
    zzabz(com.google.android.gms.internal.zzabz zzabz2, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected void zza(zzacb zzacb2) throws RemoteException {
        ((zzacd)zzacb2.zzwW()).zza(new zzabz.zza(this));
    }
}
