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
import com.google.android.gms.tagmanager.zzk;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class zzar
extends zzam {
    private static final String ID = zzag.zzdN.toString();
    private static final String zzbEd = zzah.zzfL.toString();
    private static final String zzbEf;
    private static final String zzbEj;

    static {
        zzbEj = zzah.zzfB.toString();
        zzbEf = zzah.zzhK.toString();
    }

    public zzar() {
        super(ID, zzbEd);
    }

    private byte[] zzf(String object, byte[] arrby) throws NoSuchAlgorithmException {
        object = MessageDigest.getInstance((String)object);
        object.update(arrby);
        return object.digest();
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
        block9 : {
            Object object2;
            block8 : {
                block7 : {
                    String string;
                    block6 : {
                        object2 = (zzaj.zza)object.get(zzbEd);
                        if (object2 == null) return zzdm.zzQm();
                        if (object2 == zzdm.zzQm()) {
                            return zzdm.zzQm();
                        }
                        string = zzdm.zze((zzaj.zza)object2);
                        object2 = (zzaj.zza)object.get(zzbEj);
                        object2 = object2 == null ? "MD5" : zzdm.zze((zzaj.zza)object2);
                        object = (zzaj.zza)object.get(zzbEf);
                        object = object == null ? "text" : zzdm.zze((zzaj.zza)object);
                        if (!"text".equals(object)) break block6;
                        object = string.getBytes();
                        break block7;
                    }
                    if (!"base16".equals(object)) break block8;
                    object = zzk.zzgU(string);
                }
                try {
                    return zzdm.zzR(zzk.zzq(this.zzf((String)object2, (byte[])object)));
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
            }
            object = (object = String.valueOf(object)).length() != 0 ? "Hash: unknown input format: ".concat((String)object) : new String("Hash: unknown input format: ");
            break block9;
            object = String.valueOf(object2);
            object = object.length() != 0 ? "Hash: unknown algorithm: ".concat((String)object) : new String("Hash: unknown algorithm: ");
        }
        zzbo.e((String)object);
        return zzdm.zzQm();
    }
}
