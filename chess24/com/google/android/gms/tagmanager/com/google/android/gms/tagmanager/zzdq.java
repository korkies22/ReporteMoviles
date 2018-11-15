/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzdm;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class zzdq {
    private static zzce<zzaj.zza> zza(zzce<zzaj.zza> zzce2) {
        try {
            zzce<zzaj.zza> zzce3 = new zzce<zzaj.zza>(zzdm.zzR(zzdq.zzhG(zzdm.zze(zzce2.getObject()))), zzce2.zzPu());
            return zzce3;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            zzbo.zzb("Escape URI: unsupported encoding", unsupportedEncodingException);
            return zzce2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static zzce<zzaj.zza> zza(zzce<zzaj.zza> zzce2, int n) {
        CharSequence charSequence;
        if (!zzdq.zzl(zzce2.getObject())) {
            charSequence = "Escaping can only be applied to strings.";
        } else {
            if (n == 12) {
                return zzdq.zza(zzce2);
            }
            charSequence = new StringBuilder(39);
            charSequence.append("Unsupported Value Escaping: ");
            charSequence.append(n);
            charSequence = charSequence.toString();
        }
        zzbo.e((String)charSequence);
        return zzce2;
    }

    static /* varargs */ zzce<zzaj.zza> zza(zzce<zzaj.zza> zzce2, int ... arrn) {
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            zzce2 = zzdq.zza(zzce2, arrn[i]);
        }
        return zzce2;
    }

    static String zzhG(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8").replaceAll("\\+", "%20");
    }

    private static boolean zzl(zzaj.zza zza2) {
        return zzdm.zzj(zza2) instanceof String;
    }
}
