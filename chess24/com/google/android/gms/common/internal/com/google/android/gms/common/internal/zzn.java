/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import com.google.android.gms.common.internal.zzo;

public abstract class zzn {
    private static final Object zzaED = new Object();
    private static zzn zzaEE;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzn zzaC(Context context) {
        Object object = zzaED;
        synchronized (object) {
            if (zzaEE == null) {
                zzaEE = new zzo(context.getApplicationContext());
            }
            return zzaEE;
        }
    }

    public abstract boolean zza(ComponentName var1, ServiceConnection var2, String var3);

    public abstract boolean zza(String var1, String var2, ServiceConnection var3, String var4);

    public abstract void zzb(ComponentName var1, ServiceConnection var2, String var3);

    public abstract void zzb(String var1, String var2, ServiceConnection var3, String var4);
}
