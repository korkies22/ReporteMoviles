/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;
import java.util.Map;

class zzaad
implements PendingResult.zza {
    final /* synthetic */ zzzx zzazE;

    zzaad(zzzx zzzx2) {
        this.zzazE = zzzx2;
    }

    @Override
    public void zzx(Status status) {
        zzaad.this.zzazC.remove(this.zzazE);
    }
}
