/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class DynamiteModule
implements DynamiteModule.zzb {
    DynamiteModule() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DynamiteModule.zzb.zzb zza(Context context, String string, DynamiteModule.zzb.zza zza2) throws DynamiteModule.zza {
        DynamiteModule.zzb.zzb zzb2 = new DynamiteModule.zzb.zzb();
        zzb2.zzaQD = zza2.zzA(context, string);
        int n = zzb2.zzaQD != 0 ? zza2.zzb(context, string, false) : zza2.zzb(context, string, true);
        zzb2.zzaQE = n;
        if (zzb2.zzaQD == 0 && zzb2.zzaQE == 0) {
            zzb2.zzaQF = 0;
            return zzb2;
        }
        if (zzb2.zzaQE >= zzb2.zzaQD) {
            zzb2.zzaQF = 1;
            return zzb2;
        }
        zzb2.zzaQF = -1;
        return zzb2;
    }
}
