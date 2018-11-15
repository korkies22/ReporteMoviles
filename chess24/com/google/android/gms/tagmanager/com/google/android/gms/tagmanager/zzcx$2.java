/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzcx;
import com.google.android.gms.tagmanager.zzm;

class zzcx
implements zzm.zza<String, zzcx.zzb> {
    zzcx(com.google.android.gms.tagmanager.zzcx zzcx2) {
    }

    @Override
    public /* synthetic */ int sizeOf(Object object, Object object2) {
        return this.zza((String)object, (zzcx.zzb)object2);
    }

    public int zza(String string, zzcx.zzb zzb2) {
        return string.length() + zzb2.getSize();
    }
}
