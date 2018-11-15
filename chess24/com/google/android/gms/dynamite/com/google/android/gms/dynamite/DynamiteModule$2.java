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
implements DynamiteModule.zzb.zza {
    DynamiteModule() {
    }

    @Override
    public int zzA(Context context, String string) {
        return com.google.android.gms.dynamite.DynamiteModule.zzA(context, string);
    }

    @Override
    public com.google.android.gms.dynamite.DynamiteModule zza(Context context, String string, int n) throws DynamiteModule.zza {
        return com.google.android.gms.dynamite.DynamiteModule.zzb(context, string, n);
    }

    @Override
    public int zzb(Context context, String string, boolean bl) throws DynamiteModule.zza {
        return com.google.android.gms.dynamite.DynamiteModule.zzc(context, string, bl);
    }
}
