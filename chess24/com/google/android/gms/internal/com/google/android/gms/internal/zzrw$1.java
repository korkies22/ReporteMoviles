/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsx;

class zzrw
implements Thread.UncaughtExceptionHandler {
    zzrw() {
    }

    @Override
    public void uncaughtException(Thread object, Throwable throwable) {
        object = zzrw.this.zznD();
        if (object != null) {
            object.zze("Job execution failed", throwable);
        }
    }
}
