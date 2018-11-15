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
        return zzaQC;
    }

    @Override
    public com.google.android.gms.dynamite.DynamiteModule zza(Context context, String string, int n) throws DynamiteModule.zza {
        throw new DynamiteModule.zza("local only VersionPolicy should not load from remote", null);
    }

    @Override
    public int zzb(Context context, String string, boolean bl) {
        return 0;
    }
}
