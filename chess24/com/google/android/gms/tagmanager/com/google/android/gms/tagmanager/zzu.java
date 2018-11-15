/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class zzu
extends zzam {
    private static final String ID = zzag.zzdu.toString();
    private static final String zzbCM;
    private static final String zzbDx;
    private final zza zzbDy;

    static {
        zzbDx = zzah.zzhA.toString();
        zzbCM = zzah.zzfy.toString();
    }

    public zzu(zza zza2) {
        super(ID, zzbDx);
        this.zzbDy = zza2;
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> var1_1) {
        var2_3 = zzdm.zze((zzaj.zza)var1_1.get(zzu.zzbDx));
        var3_4 = new HashMap<String, Object>();
        if ((var1_1 = (zzaj.zza)var1_1.get(zzu.zzbCM)) == null) ** GOTO lbl10
        if (!((var1_1 = zzdm.zzj((zzaj.zza)var1_1)) instanceof Map)) {
            var1_1 = "FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.";
        } else {
            for (Map.Entry<K, V> var4_5 : ((Map)var1_1).entrySet()) {
                var3_4.put((String)var4_5.getKey().toString(), var4_5.getValue());
            }
lbl10: // 2 sources:
            try {
                return zzdm.zzR(this.zzbDy.zze(var2_3, (Map<String, Object>)var3_4));
            }
            catch (Exception var1_2) {
                var1_1 = String.valueOf(var1_2.getMessage());
                var3_4 = new StringBuilder(34 + String.valueOf(var2_3).length() + String.valueOf(var1_1).length());
                var3_4.append("Custom macro/tag ");
                var3_4.append(var2_3);
                var3_4.append(" threw exception ");
                var3_4.append((String)var1_1);
                var1_1 = var3_4.toString();
            }
        }
        zzbo.zzbe((String)var1_1);
        return zzdm.zzQm();
    }

    public static interface zza {
        public Object zze(String var1, Map<String, Object> var2);
    }

}
