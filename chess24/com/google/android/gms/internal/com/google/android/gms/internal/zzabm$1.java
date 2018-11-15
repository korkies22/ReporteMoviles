/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.zzaaw;

class zzabm
implements Runnable {
    final /* synthetic */ String zzO;
    final /* synthetic */ zzaaw zzaBw;

    zzabm(zzaaw zzaaw2, String string) {
        this.zzaBw = zzaaw2;
        this.zzO = string;
    }

    @Override
    public void run() {
        if (zzabm.this.zzJh >= 1) {
            zzaaw zzaaw2 = this.zzaBw;
            Bundle bundle = zzabm.this.zzaBv != null ? zzabm.this.zzaBv.getBundle(this.zzO) : null;
            zzaaw2.onCreate(bundle);
        }
        if (zzabm.this.zzJh >= 2) {
            this.zzaBw.onStart();
        }
        if (zzabm.this.zzJh >= 3) {
            this.zzaBw.onStop();
        }
        if (zzabm.this.zzJh >= 4) {
            this.zzaBw.onDestroy();
        }
    }
}
