/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzo;
import com.google.android.gms.tagmanager.zzp;
import com.google.android.gms.tagmanager.zzq;

private class zzp.zzc
implements zzbn<zzai.zzj> {
    private zzp.zzc() {
    }

    @Override
    public /* synthetic */ void onSuccess(Object object) {
        this.zzb((zzai.zzj)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zza(zzbn.zza object) {
        if (object == zzbn.zza.zzbEJ) {
            zzp.this.zzbDk.zzON();
        }
        zzp zzp2 = zzp.this;
        synchronized (zzp2) {
            if (!zzp.this.isReady()) {
                ContainerHolder containerHolder;
                if (zzp.this.zzbDn != null) {
                    object = zzp.this;
                    containerHolder = zzp.this.zzbDn;
                } else {
                    object = zzp.this;
                    containerHolder = zzp.this.zzbK(Status.zzayk);
                }
                object.zzb(containerHolder);
            }
        }
        long l = zzp.this.zzbDk.zzOM();
        zzp.this.zzav(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzb(zzai.zzj zzj2) {
        zzp.this.zzbDk.zzOO();
        zzp zzp2 = zzp.this;
        synchronized (zzp2) {
            if (zzj2.zzlu == null) {
                if (zzp.zzf((zzp)zzp.this).zzlu == null) {
                    zzbo.e("Current resource is null; network resource is also null");
                    long l = zzp.this.zzbDk.zzOM();
                    zzp.this.zzav(l);
                    return;
                }
                zzj2.zzlu = zzp.zzf((zzp)zzp.this).zzlu;
            }
            zzp.this.zza(zzj2, zzp.this.zzuI.currentTimeMillis(), false);
            long l = zzp.this.zzbCX;
            StringBuilder stringBuilder = new StringBuilder(58);
            stringBuilder.append("setting refresh time to current time: ");
            stringBuilder.append(l);
            zzbo.v(stringBuilder.toString());
            if (!zzp.this.zzOI()) {
                zzp.this.zza(zzj2);
            }
            return;
        }
    }
}
