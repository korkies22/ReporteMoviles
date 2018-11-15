/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.List;

public static final class zzaa.zza {
    private final Object zzXN;
    private final List<String> zzaEY;

    private zzaa.zza(Object object) {
        this.zzXN = zzac.zzw(object);
        this.zzaEY = new ArrayList<String>();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append(this.zzXN.getClass().getSimpleName());
        stringBuilder.append('{');
        int n = this.zzaEY.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.zzaEY.get(i));
            if (i >= n - 1) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public zzaa.zza zzg(String string, Object object) {
        List<String> list = this.zzaEY;
        string = zzac.zzw(string);
        object = String.valueOf(String.valueOf(object));
        StringBuilder stringBuilder = new StringBuilder(1 + String.valueOf(string).length() + String.valueOf(object).length());
        stringBuilder.append(string);
        stringBuilder.append("=");
        stringBuilder.append((String)object);
        list.add(stringBuilder.toString());
        return this;
    }
}
