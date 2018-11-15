/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzri
extends zzf<zzri> {
    private final Map<String, Object> zzFs = new HashMap<String, Object>();

    private String zzbz(String string) {
        zzac.zzdv(string);
        String string2 = string;
        if (string != null) {
            string2 = string;
            if (string.startsWith("&")) {
                string2 = string.substring(1);
            }
        }
        zzac.zzh(string2, "Name can not be empty or \"&\"");
        return string2;
    }

    public void set(String string, String string2) {
        string = this.zzbz(string);
        this.zzFs.put(string, string2);
    }

    public String toString() {
        return zzri.zzj(this.zzFs);
    }

    public void zza(zzri zzri2) {
        zzac.zzw(zzri2);
        zzri2.zzFs.putAll(this.zzFs);
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzri)zzf2);
    }

    public Map<String, Object> zzmI() {
        return Collections.unmodifiableMap(this.zzFs);
    }
}
