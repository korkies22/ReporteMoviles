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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabw;
import com.google.android.gms.internal.zzaby;
import com.google.android.gms.internal.zzaca;
import com.google.android.gms.internal.zzacb;
import com.google.android.gms.internal.zzacc;
import com.google.android.gms.internal.zzacd;
import com.google.android.gms.internal.zzzv;

public final class zzabz
implements zzaby {
    @Override
    public PendingResult<Status> zzg(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new zzaca.zza(this, googleApiClient){

            @Override
            protected void zza(zzacb zzacb2) throws RemoteException {
                ((zzacd)zzacb2.zzwW()).zza(new zza(this));
            }
        });
    }

    private static class zza
    extends zzabw {
        private final zzzv.zzb<Status> zzaFq;

        public zza(zzzv.zzb<Status> zzb2) {
            this.zzaFq = zzb2;
        }

        @Override
        public void zzcX(int n) throws RemoteException {
            this.zzaFq.setResult(new Status(n));
        }
    }

}
