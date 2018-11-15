/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsc;

class zzrs
implements Runnable {
    final /* synthetic */ String zzacJ;
    final /* synthetic */ Runnable zzacK;

    zzrs(String string, Runnable runnable) {
        this.zzacJ = string;
        this.zzacK = runnable;
    }

    @Override
    public void run() {
        zzrs.this.zzacF.zzbW(this.zzacJ);
        if (this.zzacK != null) {
            this.zzacK.run();
        }
    }
}
