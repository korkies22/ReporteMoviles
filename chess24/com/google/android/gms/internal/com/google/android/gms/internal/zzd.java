/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzo;
import com.google.android.gms.internal.zzr;

public class zzd
implements zzo {
    private int zzn;
    private int zzo;
    private final int zzp;
    private final float zzq;

    public zzd() {
        this(2500, 1, 1.0f);
    }

    public zzd(int n, int n2, float f) {
        this.zzn = n;
        this.zzp = n2;
        this.zzq = f;
    }

    @Override
    public void zza(zzr zzr2) throws zzr {
        ++this.zzo;
        this.zzn = (int)((float)this.zzn + (float)this.zzn * this.zzq);
        if (!this.zze()) {
            throw zzr2;
        }
    }

    @Override
    public int zzc() {
        return this.zzn;
    }

    @Override
    public int zzd() {
        return this.zzo;
    }

    protected boolean zze() {
        if (this.zzo <= this.zzp) {
            return true;
        }
        return false;
    }
}
