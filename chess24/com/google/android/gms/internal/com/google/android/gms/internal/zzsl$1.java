/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.internal.zzrw;

class zzsl
implements Runnable {
    zzsl() {
    }

    @Override
    public void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzsl.this.zzacN.zznt().zzg(this);
            return;
        }
        boolean bl = zzsl.this.zzcv();
        zzsl.this.zzaed = 0L;
        if (bl) {
            zzsl.this.run();
        }
    }
}
