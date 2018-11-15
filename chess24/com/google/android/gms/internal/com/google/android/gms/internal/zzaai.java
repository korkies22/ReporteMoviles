/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.DeadObjectException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzal;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzabp;
import com.google.android.gms.internal.zzabq;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class zzaai
implements zzaam {
    private final zzaan zzazK;
    private boolean zzazL = false;

    public zzaai(zzaan zzaan2) {
        this.zzazK = zzaan2;
    }

    static /* synthetic */ zzaan zza(zzaai zzaai2) {
        return zzaai2.zzazK;
    }

    private <A extends Api.zzb> void zzd(zzzv.zza<? extends Result, A> zza2) throws DeadObjectException {
        this.zzazK.zzazd.zzaAx.zzb(zza2);
        Api.zzc<A> zzc2 = this.zzazK.zzazd.zzb(zza2.zzuH());
        if (!zzc2.isConnected() && this.zzazK.zzaAG.containsKey(zza2.zzuH())) {
            zza2.zzA(new Status(17));
            return;
        }
        Object object = zzc2;
        if (zzc2 instanceof zzal) {
            object = ((zzal)((Object)zzc2)).zzxG();
        }
        zza2.zzb((Api.zzc<A>)object);
    }

    @Override
    public void begin() {
    }

    @Override
    public void connect() {
        if (this.zzazL) {
            this.zzazL = false;
            this.zzazK.zza(new zzaan.zza(this){

                @Override
                public void zzvA() {
                    zzaai.zza((zzaai)zzaai.this).zzaAK.zzo(null);
                }
            });
        }
    }

    @Override
    public boolean disconnect() {
        if (this.zzazL) {
            return false;
        }
        if (this.zzazK.zzazd.zzvM()) {
            this.zzazL = true;
            Iterator<zzabp> iterator = this.zzazK.zzazd.zzaAw.iterator();
            while (iterator.hasNext()) {
                iterator.next().zzwu();
            }
            return false;
        }
        this.zzazK.zzh(null);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int n) {
        this.zzazK.zzh(null);
        this.zzazK.zzaAK.zzc(n, this.zzazL);
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(T t) {
        return this.zzb(t);
    }

    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(T t) {
        try {
            this.zzd(t);
        }
        catch (DeadObjectException deadObjectException) {}
        return t;
        this.zzazK.zza(new zzaan.zza(this){

            @Override
            public void zzvA() {
                zzaai.this.onConnectionSuspended(1);
            }
        });
        return t;
    }

    void zzvz() {
        if (this.zzazL) {
            this.zzazL = false;
            this.zzazK.zzazd.zzaAx.release();
            this.disconnect();
        }
    }

}
