/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import java.util.Set;

public final class zzu {
    public static String[] zza(Scope[] arrscope) {
        zzac.zzb(arrscope, (Object)"scopes can't be null.");
        String[] arrstring = new String[arrscope.length];
        for (int i = 0; i < arrscope.length; ++i) {
            arrstring[i] = arrscope[i].zzuS();
        }
        return arrstring;
    }

    public static String[] zzd(Set<Scope> set) {
        zzac.zzb(set, (Object)"scopes can't be null.");
        return zzu.zza(set.toArray(new Scope[set.size()]));
    }
}
