/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaar;
import java.lang.ref.WeakReference;

static class zzaal.zzb
extends zzaar.zza {
    private WeakReference<zzaal> zzaAD;

    zzaal.zzb(zzaal zzaal2) {
        this.zzaAD = new WeakReference<zzaal>(zzaal2);
    }

    @Override
    public void zzvb() {
        zzaal zzaal2 = (zzaal)this.zzaAD.get();
        if (zzaal2 == null) {
            return;
        }
        zzaal2.resume();
    }
}
