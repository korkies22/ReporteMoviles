/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzf;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class zzrg
extends zzf<zzrg> {
    private Map<Integer, String> zzabU = new HashMap<Integer, String>(4);

    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry<Integer, String> entry : this.zzabU.entrySet()) {
            String string = String.valueOf(entry.getKey());
            StringBuilder stringBuilder = new StringBuilder(9 + String.valueOf(string).length());
            stringBuilder.append("dimension");
            stringBuilder.append(string);
            hashMap.put(stringBuilder.toString(), entry.getValue());
        }
        return zzrg.zzj(hashMap);
    }

    public void zza(zzrg zzrg2) {
        zzrg2.zzabU.putAll(this.zzabU);
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrg)zzf2);
    }

    public Map<Integer, String> zzmG() {
        return Collections.unmodifiableMap(this.zzabU);
    }
}
