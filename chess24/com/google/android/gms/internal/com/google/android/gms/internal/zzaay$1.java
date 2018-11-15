/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.zzaaw;

class zzaay
implements Runnable {
    final /* synthetic */ String zzO;
    final /* synthetic */ zzaaw zzaBw;

    zzaay(zzaaw zzaaw2, String string) {
        this.zzaBw = zzaaw2;
        this.zzO = string;
    }

    @Override
    public void run() {
        if (zzaay.this.zzJh >= 1) {
            zzaaw zzaaw2 = this.zzaBw;
            Bundle bundle = zzaay.this.zzaBv != null ? zzaay.this.zzaBv.getBundle(this.zzO) : null;
            zzaaw2.onCreate(bundle);
        }
        if (zzaay.this.zzJh >= 2) {
            this.zzaBw.onStart();
        }
        if (zzaay.this.zzJh >= 3) {
            this.zzaBw.onStop();
        }
        if (zzaay.this.zzJh >= 4) {
            this.zzaBw.onDestroy();
        }
    }
}
