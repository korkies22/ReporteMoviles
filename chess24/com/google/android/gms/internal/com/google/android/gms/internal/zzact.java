/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

public class zzact
implements Executor {
    private final Handler mHandler;

    public zzact(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
