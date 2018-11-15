/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsc;

class zzrs
implements Runnable {
    final /* synthetic */ int zzacG;

    zzrs(int n) {
        this.zzacG = n;
    }

    @Override
    public void run() {
        zzrs.this.zzacF.zzw((long)this.zzacG * 1000L);
    }
}
