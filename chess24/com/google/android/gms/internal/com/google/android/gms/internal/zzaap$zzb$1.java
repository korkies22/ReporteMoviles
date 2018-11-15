/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzzs;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

class zzaap.zzb
implements Runnable {
    final /* synthetic */ ConnectionResult zzaBi;

    zzaap.zzb(ConnectionResult connectionResult) {
        this.zzaBi = connectionResult;
    }

    @Override
    public void run() {
        if (this.zzaBi.isSuccess()) {
            zzb.this.zzaBj = true;
            if (zzb.this.zzazq.zzqD()) {
                zzb.this.zzwi();
                return;
            }
            zzb.this.zzazq.zza(null, Collections.<Scope>emptySet());
            return;
        }
        ((zzaap.zza)zzb.this.zzaBg.zzazt.get(zzb.this.zzaxH)).onConnectionFailed(this.zzaBi);
    }
}
