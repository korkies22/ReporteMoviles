/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

class zze
implements Executor {
    final /* synthetic */ Handler zzs;

    zze(com.google.android.gms.internal.zze zze2, Handler handler) {
        this.zzs = handler;
    }

    @Override
    public void execute(Runnable runnable) {
        this.zzs.post(runnable);
    }
}
