/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzf;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class zzrh
extends zzf<zzrh> {
    private Map<Integer, Double> zzabV = new HashMap<Integer, Double>(4);

    public String toString() {
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        for (Map.Entry<Integer, Double> entry : this.zzabV.entrySet()) {
            String string = String.valueOf(entry.getKey());
            StringBuilder stringBuilder = new StringBuilder(6 + String.valueOf(string).length());
            stringBuilder.append("metric");
            stringBuilder.append(string);
            hashMap.put(stringBuilder.toString(), entry.getValue());
        }
        return zzrh.zzj(hashMap);
    }

    public void zza(zzrh zzrh2) {
        zzrh2.zzabV.putAll(this.zzabV);
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrh)zzf2);
    }

    public Map<Integer, Double> zzmH() {
        return Collections.unmodifiableMap(this.zzabV);
    }
}
