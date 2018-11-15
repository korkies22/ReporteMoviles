/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzs;

class zzk
implements Runnable {
    final /* synthetic */ String zzO;
    final /* synthetic */ long zzP;

    zzk(String string, long l) {
        this.zzO = string;
        this.zzP = l;
    }

    @Override
    public void run() {
        zzk.this.zzB.zza(this.zzO, this.zzP);
        zzk.this.zzB.zzd(this.toString());
    }
}
