/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbgg;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzp;

private class zzp.zzb
implements zzbn<zzbgg.zza> {
    private zzp.zzb() {
    }

    @Override
    public /* synthetic */ void onSuccess(Object object) {
        this.zza((zzbgg.zza)object);
    }

    public void zza(zzbgg.zza zza2) {
        zzai.zzj zzj2;
        if (zza2.zzbLi != null) {
            zzj2 = zza2.zzbLi;
        } else {
            zzai.zzf zzf2 = zza2.zzlu;
            zzj2 = new zzai.zzj();
            zzj2.zzlu = zzf2;
            zzj2.zzlt = null;
            zzj2.zzlv = zzf2.version;
        }
        zzp.this.zza(zzj2, zza2.zzbLh, true);
    }

    @Override
    public void zza(zzbn.zza zza2) {
        if (!zzp.this.zzbDo) {
            zzp.this.zzav(0L);
        }
    }
}
