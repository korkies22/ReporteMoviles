/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzcm
extends zzam {
    private static final String ID = zzag.zzdR.toString();
    private static final String zzbFl = zzah.zzfL.toString();
    private static final String zzbFm = zzah.zzfM.toString();
    private static final String zzbFn = zzah.zzhI.toString();
    private static final String zzbFo = zzah.zzhB.toString();

    public zzcm() {
        super(ID, zzbFl, zzbFm);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        block11 : {
            Object object2 = (zzaj.zza)object.get(zzbFl);
            zzaj.zza zza2 = (zzaj.zza)object.get(zzbFm);
            if (object2 == null) return zzdm.zzQm();
            if (object2 == zzdm.zzQm()) return zzdm.zzQm();
            if (zza2 == null) return zzdm.zzQm();
            if (zza2 == zzdm.zzQm()) return zzdm.zzQm();
            int n = 64;
            if (zzdm.zzi((zzaj.zza)object.get(zzbFn)).booleanValue()) {
                n = 66;
            }
            int n2 = 1;
            if ((object = (zzaj.zza)object.get(zzbFo)) != null) {
                int n3;
                if ((object = zzdm.zzg((zzaj.zza)object)) == zzdm.zzQh()) {
                    return zzdm.zzQm();
                }
                n2 = n3 = object.intValue();
                if (n3 < 0) {
                    return zzdm.zzQm();
                }
            }
            try {
                object = zzdm.zze((zzaj.zza)object2);
                object2 = zzdm.zze(zza2);
                zza2 = null;
            }
            catch (PatternSyntaxException patternSyntaxException) {
                return zzdm.zzQm();
            }
            object2 = Pattern.compile((String)object2, n).matcher((CharSequence)object);
            object = zza2;
            if (!object2.find()) break block11;
            object = zza2;
            if (object2.groupCount() < n2) break block11;
            object = object2.group(n2);
        }
        if (object != null) return zzdm.zzR(object);
        return zzdm.zzQm();
    }
}
