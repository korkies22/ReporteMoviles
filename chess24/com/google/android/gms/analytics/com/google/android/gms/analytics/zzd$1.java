/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzf;
import java.util.Comparator;

class zzd
implements Comparator<zzf> {
    zzd(com.google.android.gms.analytics.zzd zzd2) {
    }

    @Override
    public /* synthetic */ int compare(Object object, Object object2) {
        return this.zza((zzf)object, (zzf)object2);
    }

    public int zza(zzf zzf2, zzf zzf3) {
        return zzf2.getClass().getCanonicalName().compareTo(zzf3.getClass().getCanonicalName());
    }
}
