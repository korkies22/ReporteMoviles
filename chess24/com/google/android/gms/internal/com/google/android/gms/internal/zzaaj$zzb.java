/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaxn;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

private class zzaaj.zzb
extends zzaaj.zzf {
    private final Map<Api.zze, zzaaj.zza> zzaAc;

    public zzaaj.zzb(Map<Api.zze, zzaaj.zza> map) {
        super(zzaaj.this, null);
        this.zzaAc = map;
    }

    @WorkerThread
    @Override
    public void zzvA() {
        int n;
        boolean bl;
        boolean bl2;
        boolean bl3;
        Iterator<Api.zze> iterator;
        block8 : {
            iterator = this.zzaAc.keySet().iterator();
            bl3 = true;
            n = 0;
            bl = true;
            bl2 = false;
            while (iterator.hasNext()) {
                Api.zze zze2 = iterator.next();
                if (zze2.zzuI()) {
                    if (this.zzaAc.get(zze2).zzazb == 0) {
                        bl2 = true;
                        break block8;
                    }
                    bl2 = true;
                    continue;
                }
                bl = false;
            }
            boolean bl4 = false;
            bl3 = bl2;
            bl2 = bl4;
        }
        if (bl3) {
            n = zzaaj.this.zzazw.isGooglePlayServicesAvailable(zzaaj.this.mContext);
        }
        if (n != 0 && (bl2 || bl)) {
            iterator = new ConnectionResult(n, null);
            zzaaj.this.zzazK.zza(new zzaan.zza(zzaaj.this, (ConnectionResult)((Object)iterator)){
                final /* synthetic */ ConnectionResult zzaAd;
                {
                    this.zzaAd = connectionResult;
                    super(zzaam2);
                }

                @Override
                public void zzvA() {
                    zzaaj.this.zzf(this.zzaAd);
                }
            });
            return;
        }
        if (zzaaj.this.zzazU) {
            zzaaj.this.zzazS.connect();
        }
        for (Api.zze zze2 : this.zzaAc.keySet()) {
            final zzf.zzf zzf2 = this.zzaAc.get(zze2);
            if (zze2.zzuI() && n != 0) {
                zzaaj.this.zzazK.zza(new zzaan.zza(this, zzaaj.this){

                    @Override
                    public void zzvA() {
                        zzf2.zzg(new ConnectionResult(16, null));
                    }
                });
                continue;
            }
            zze2.zza(zzf2);
        }
    }

}
