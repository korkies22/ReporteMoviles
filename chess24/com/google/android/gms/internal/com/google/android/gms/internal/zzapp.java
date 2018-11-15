/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.zzapn;
import com.google.android.gms.internal.zzapq;

public class zzapp {
    private zzapq zzaWI = null;
    private boolean zztW = false;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void initialize(Context context) {
        synchronized (this) {
            if (this.zztW) {
                return;
            }
            try {
                this.zzaWI = zzapq.zza.asInterface(DynamiteModule.zza(context, DynamiteModule.zzaQw, "com.google.android.gms.flags").zzdX("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.zzaWI.init(zze.zzA(context));
                this.zztW = true;
            }
            catch (RemoteException | DynamiteModule.zza object) {
                Log.w((String)"FlagValueProvider", (String)"Failed to initialize flags module.", (Throwable)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> T zzb(zzapn<T> zzapn2) {
        synchronized (this) {
            if (!this.zztW) {
                zzapn2 = zzapn2.zzfm();
                return (T)zzapn2;
            }
            return zzapn2.zza(this.zzaWI);
        }
    }
}
