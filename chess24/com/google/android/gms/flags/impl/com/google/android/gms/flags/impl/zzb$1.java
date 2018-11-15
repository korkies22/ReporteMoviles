/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzb
implements Callable<SharedPreferences> {
    zzb() {
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.zzCU();
    }

    public SharedPreferences zzCU() {
        return Context.this.getSharedPreferences("google_sdk_flags", 1);
    }
}
