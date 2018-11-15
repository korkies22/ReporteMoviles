/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.tagmanager.zzas;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcg;
import com.google.android.gms.tagmanager.zzdf;

class zzcg.zza
implements zzdf.zza {
    zzcg.zza() {
    }

    @Override
    public void zza(zzas zzas2) {
        zzcg.this.zzu(zzas2.zzPi());
    }

    @Override
    public void zzb(zzas object) {
        zzcg.this.zzu(object.zzPi());
        long l = object.zzPi();
        object = new StringBuilder(57);
        object.append("Permanent failure dispatching hitId: ");
        object.append(l);
        zzbo.v(object.toString());
    }

    @Override
    public void zzc(zzas object) {
        long l = object.zzPj();
        if (l == 0L) {
            zzcg.this.zzh(object.zzPi(), zzcg.this.zzuI.currentTimeMillis());
            return;
        }
        if (l + 14400000L < zzcg.this.zzuI.currentTimeMillis()) {
            zzcg.this.zzu(object.zzPi());
            l = object.zzPi();
            object = new StringBuilder(47);
            object.append("Giving up on failed hitId: ");
            object.append(l);
            zzbo.v(object.toString());
        }
    }
}
