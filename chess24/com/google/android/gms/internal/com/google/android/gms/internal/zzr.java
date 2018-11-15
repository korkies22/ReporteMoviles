/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzi;

public class zzr
extends Exception {
    private long zzA;
    public final zzi zzah;

    public zzr() {
        this.zzah = null;
    }

    public zzr(zzi zzi2) {
        this.zzah = zzi2;
    }

    public zzr(Throwable throwable) {
        super(throwable);
        this.zzah = null;
    }

    void zza(long l) {
        this.zzA = l;
    }
}
