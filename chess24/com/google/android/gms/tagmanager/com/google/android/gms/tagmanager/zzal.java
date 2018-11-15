/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzal {
    private static void zza(DataLayer dataLayer, zzai.zzd arrzza) {
        arrzza = arrzza.zzkD;
        int n = arrzza.length;
        for (int i = 0; i < n; ++i) {
            dataLayer.zzhd(zzdm.zze(arrzza[i]));
        }
    }

    public static void zza(DataLayer dataLayer, zzai.zzi zzi2) {
        if (zzi2.zzls == null) {
            zzbo.zzbe("supplemental missing experimentSupplemental");
            return;
        }
        zzal.zza(dataLayer, zzi2.zzls);
        zzal.zzb(dataLayer, zzi2.zzls);
        zzal.zzc(dataLayer, zzi2.zzls);
    }

    private static void zzb(DataLayer dataLayer, zzai.zzd arrzza) {
        arrzza = arrzza.zzkC;
        int n = arrzza.length;
        for (int i = 0; i < n; ++i) {
            Map<String, Object> map = zzal.zzc(arrzza[i]);
            if (map == null) continue;
            dataLayer.push(map);
        }
    }

    private static Map<String, Object> zzc(zzaj.zza object) {
        if (!((object = zzdm.zzj((zzaj.zza)object)) instanceof Map)) {
            object = String.valueOf(object);
            StringBuilder stringBuilder = new StringBuilder(36 + String.valueOf(object).length());
            stringBuilder.append("value: ");
            stringBuilder.append((String)object);
            stringBuilder.append(" is not a map value, ignored.");
            zzbo.zzbe(stringBuilder.toString());
            return null;
        }
        return (Map)object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void zzc(DataLayer var0, zzai.zzd var1_1) {
        var9_2 = var1_1.zzkE;
        var3_3 = var9_2.length;
        var2_4 = 0;
        while (var2_4 < var3_3) {
            block8 : {
                var10_8 = var9_2[var2_4];
                if (var10_8.zzaA != null) break block8;
                var1_1 = "GaExperimentRandom: No key";
                ** GOTO lbl31
            }
            var8_7 = var0.get(var10_8.zzaA);
            var1_1 = var8_7 instanceof Number == false ? null : Long.valueOf(((Number)var8_7).longValue());
            var4_5 = var10_8.zzky;
            var6_6 = var10_8.zzkz;
            if (var10_8.zzkA && var1_1 != null && var1_1.longValue() >= var4_5 && var1_1.longValue() <= var6_6) ** GOTO lbl17
            if (var4_5 <= var6_6) {
                var8_7 = Math.round(Math.random() * (double)(var6_6 - var4_5) + (double)var4_5);
lbl17: // 2 sources:
                var0.zzhd(var10_8.zzaA);
                var1_1 = var0.zzo(var10_8.zzaA, var8_7);
                if (var10_8.zzkB > 0L) {
                    if (!var1_1.containsKey("gtm")) {
                        var1_1.put((String)"gtm", DataLayer.mapOf(new Object[]{"lifetime", var10_8.zzkB}));
                    } else {
                        var8_7 = var1_1.get("gtm");
                        if (var8_7 instanceof Map) {
                            ((Map)var8_7).put("lifetime", var10_8.zzkB);
                        } else {
                            zzbo.zzbe("GaExperimentRandom: gtm not a map");
                        }
                    }
                }
                var0.push((Map<String, Object>)var1_1);
            } else {
                var1_1 = "GaExperimentRandom: random range invalid";
lbl31: // 2 sources:
                zzbo.zzbe((String)var1_1);
            }
            ++var2_4;
        }
    }
}
