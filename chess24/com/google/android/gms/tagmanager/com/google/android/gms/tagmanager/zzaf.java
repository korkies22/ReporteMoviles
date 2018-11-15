/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzk;
import java.util.Map;

class zzaf
extends zzam {
    private static final String ID = zzag.zzdL.toString();
    private static final String zzbEd = zzah.zzfL.toString();
    private static final String zzbEe = zzah.zzir.toString();
    private static final String zzbEf = zzah.zzhK.toString();
    private static final String zzbEg = zzah.zziB.toString();

    public zzaf() {
        super(ID, zzbEd);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        block14 : {
            String string;
            block13 : {
                Object object2;
                int n;
                block12 : {
                    object2 = (zzaj.zza)object.get(zzbEd);
                    if (object2 == null) return zzdm.zzQm();
                    if (object2 == zzdm.zzQm()) {
                        return zzdm.zzQm();
                    }
                    String string2 = zzdm.zze((zzaj.zza)object2);
                    object2 = (zzaj.zza)object.get(zzbEf);
                    string = object2 == null ? "text" : zzdm.zze((zzaj.zza)object2);
                    object2 = (zzaj.zza)object.get(zzbEg);
                    object2 = object2 == null ? "base16" : zzdm.zze((zzaj.zza)object2);
                    int n2 = 2;
                    object = (zzaj.zza)object.get(zzbEe);
                    n = n2;
                    if (object != null) {
                        n = n2;
                        if (zzdm.zzi((zzaj.zza)object).booleanValue()) {
                            n = 3;
                        }
                    }
                    try {
                        if ("text".equals(string)) {
                            object = string2.getBytes();
                            break block12;
                        }
                        if ("base16".equals(string)) {
                            object = zzk.zzgU(string2);
                            break block12;
                        }
                        if ("base64".equals(string)) {
                            object = Base64.decode((String)string2, (int)n);
                            break block12;
                        }
                        if (!"base64url".equals(string)) break block13;
                        object = Base64.decode((String)string2, (int)(n | 8));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {}
                }
                if ("base16".equals(object2)) {
                    object = zzk.zzq((byte[])object);
                    return zzdm.zzR(object);
                }
                if ("base64".equals(object2)) {
                    object = Base64.encodeToString((byte[])object, (int)n);
                    return zzdm.zzR(object);
                }
                if ("base64url".equals(object2)) {
                    object = Base64.encodeToString((byte[])object, (int)(n | 8));
                    return zzdm.zzR(object);
                }
                object = String.valueOf(object2);
                object = object.length() != 0 ? "Encode: unknown output format: ".concat((String)object) : new String("Encode: unknown output format: ");
                break block14;
            }
            object = String.valueOf(string);
            object = object.length() != 0 ? "Encode: unknown input format: ".concat((String)object) : new String("Encode: unknown input format: ");
            zzbo.e((String)object);
            return zzdm.zzQm();
            object = "Encode: invalid input:";
        }
        zzbo.e((String)object);
        return zzdm.zzQm();
    }
}
