/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzabj;
import com.google.android.gms.internal.zzzs;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

private class zzaap.zzb
implements zzf.zzf,
zzabj.zza {
    private boolean zzaBj = false;
    private Set<Scope> zzajm = null;
    private final zzzs<?> zzaxH;
    private zzr zzazW = null;
    private final Api.zze zzazq;

    public zzaap.zzb(Api.zze zze2, zzzs<?> zzzs2) {
        this.zzazq = zze2;
        this.zzaxH = zzzs2;
    }

    @WorkerThread
    private void zzwi() {
        if (this.zzaBj && this.zzazW != null) {
            this.zzazq.zza(this.zzazW, this.zzajm);
        }
    }

    @WorkerThread
    @Override
    public void zzb(zzr zzr2, Set<Scope> set) {
        if (zzr2 != null && set != null) {
            this.zzazW = zzr2;
            this.zzajm = set;
            this.zzwi();
            return;
        }
        Log.wtf((String)"GoogleApiManager", (String)"Received null response from onSignInSuccess", (Throwable)new Exception());
        this.zzi(new ConnectionResult(4));
    }

    @Override
    public void zzg(final @NonNull ConnectionResult connectionResult) {
        zzaap.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                if (connectionResult.isSuccess()) {
                    zzb.this.zzaBj = true;
                    if (zzb.this.zzazq.zzqD()) {
                        zzb.this.zzwi();
                        return;
                    }
                    zzb.this.zzazq.zza(null, Collections.<Scope>emptySet());
                    return;
                }
                ((zzaap.zza)zzaap.this.zzazt.get(zzb.this.zzaxH)).onConnectionFailed(connectionResult);
            }
        });
    }

    @WorkerThread
    @Override
    public void zzi(ConnectionResult connectionResult) {
        ((zzaap.zza)zzaap.this.zzazt.get(this.zzaxH)).zzi(connectionResult);
    }

}
