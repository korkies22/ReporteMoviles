/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzaaz;

public abstract class zzaaf<L>
implements zzaaz.zzc<L> {
    private final DataHolder zzazI;

    protected zzaaf(DataHolder dataHolder) {
        this.zzazI = dataHolder;
    }

    protected abstract void zza(L var1, DataHolder var2);

    @Override
    public final void zzs(L l) {
        this.zza(l, this.zzazI);
    }

    @Override
    public void zzvy() {
        if (this.zzazI != null) {
            this.zzazI.close();
        }
    }
}
