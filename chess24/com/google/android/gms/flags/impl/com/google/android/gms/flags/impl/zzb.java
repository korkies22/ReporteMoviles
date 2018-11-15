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
import com.google.android.gms.internal.zzaps;
import java.util.concurrent.Callable;

public class zzb {
    private static SharedPreferences zzaWS;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SharedPreferences zzm(Context context) {
        synchronized (SharedPreferences.class) {
            if (zzaWS != null) return zzaWS;
            zzaWS = (SharedPreferences)zzaps.zzb(new Callable<SharedPreferences>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzCU();
                }

                public SharedPreferences zzCU() {
                    return Context.this.getSharedPreferences("google_sdk_flags", 1);
                }
            });
            return zzaWS;
        }
    }

}
