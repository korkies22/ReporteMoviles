/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzcx;

private static class zzcx.zzb {
    private zzce<zzaj.zza> zzbFR;
    private zzaj.zza zzbFS;

    public zzcx.zzb(zzce<zzaj.zza> zzce2, zzaj.zza zza2) {
        this.zzbFR = zzce2;
        this.zzbFS = zza2;
    }

    public int getSize() {
        int n = this.zzbFR.getObject().zzacY();
        int n2 = this.zzbFS == null ? 0 : this.zzbFS.zzacY();
        return n + n2;
    }

    public zzce<zzaj.zza> zzPL() {
        return this.zzbFR;
    }

    public zzaj.zza zzPM() {
        return this.zzbFS;
    }
}
