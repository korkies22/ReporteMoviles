/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import com.google.android.gms.internal.zzrz;

class zzrz.zza
implements Runnable {
    final /* synthetic */ ComponentName val$name;

    zzrz.zza(ComponentName componentName) {
        this.val$name = componentName;
    }

    @Override
    public void run() {
        zza.this.zzado.onServiceDisconnected(this.val$name);
    }
}
