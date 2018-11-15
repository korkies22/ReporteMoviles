/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.List;

class zzx
implements Runnable {
    final /* synthetic */ DataLayer.zzc.zza zzbDQ;

    zzx(DataLayer.zzc.zza zza2) {
        this.zzbDQ = zza2;
    }

    @Override
    public void run() {
        this.zzbDQ.zzJ(zzx.this.zzOV());
    }
}
