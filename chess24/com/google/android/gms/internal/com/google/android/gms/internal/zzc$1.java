/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzk;
import java.util.concurrent.BlockingQueue;

class zzc
implements Runnable {
    final /* synthetic */ zzk zzl;

    zzc(zzk zzk2) {
        this.zzl = zzk2;
    }

    @Override
    public void run() {
        try {
            zzc.this.zzh.put(this.zzl);
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }
}
