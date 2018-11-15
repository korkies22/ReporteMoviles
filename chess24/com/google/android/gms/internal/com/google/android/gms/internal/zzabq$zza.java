/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import com.google.android.gms.common.api.zzf;
import com.google.android.gms.internal.zzabq;
import com.google.android.gms.internal.zzzx;
import java.lang.ref.WeakReference;

private static class zzabq.zza
implements IBinder.DeathRecipient,
zzabq.zzb {
    private final WeakReference<zzzx<?>> zzaCa;
    private final WeakReference<zzf> zzaCb;
    private final WeakReference<IBinder> zzaCc;

    private zzabq.zza(zzzx<?> zzzx2, zzf zzf2, IBinder iBinder) {
        this.zzaCb = new WeakReference<zzf>(zzf2);
        this.zzaCa = new WeakReference(zzzx2);
        this.zzaCc = new WeakReference<IBinder>(iBinder);
    }

    private void zzwx() {
        zzzx zzzx2 = (zzzx)this.zzaCa.get();
        zzf zzf2 = (zzf)this.zzaCb.get();
        if (zzf2 != null && zzzx2 != null) {
            zzf2.remove(zzzx2.zzuR());
        }
        if ((zzzx2 = (IBinder)this.zzaCc.get()) != null) {
            zzzx2.unlinkToDeath((IBinder.DeathRecipient)this, 0);
        }
    }

    public void binderDied() {
        this.zzwx();
    }

    @Override
    public void zzc(zzzx<?> zzzx2) {
        this.zzwx();
    }
}
