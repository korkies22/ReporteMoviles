/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzawc;
import java.util.Comparator;

public static class zzawc.zza
implements Comparator<zzawc> {
    @Override
    public /* synthetic */ int compare(Object object, Object object2) {
        return this.zza((zzawc)object, (zzawc)object2);
    }

    public int zza(zzawc zzawc2, zzawc zzawc3) {
        if (zzawc2.zzbzw == zzawc3.zzbzw) {
            return zzawc2.name.compareTo(zzawc3.name);
        }
        return zzawc2.zzbzw - zzawc3.zzbzw;
    }
}
