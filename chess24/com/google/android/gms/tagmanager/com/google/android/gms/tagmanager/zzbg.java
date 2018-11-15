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
import com.google.android.gms.tagmanager.zzdq;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class zzbg
extends zzam {
    private static final String ID = zzag.zzdP.toString();
    private static final String zzbEd = zzah.zzfL.toString();
    private static final String zzbEv = zzah.zzhP.toString();
    private static final String zzbEw = zzah.zzhT.toString();
    private static final String zzbEx = zzah.zzhj.toString();

    public zzbg() {
        super(ID, zzbEd);
    }

    private String zza(String string, zza object, Set<Character> object2) {
        switch (.zzbEy[object.ordinal()]) {
            default: {
                return string;
            }
            case 2: {
                string = string.replace("\\", "\\\\");
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    String string2 = ((Character)object2.next()).toString();
                    object = String.valueOf(string2);
                    object = object.length() != 0 ? "\\".concat((String)object) : new String("\\");
                    string = string.replace(string2, (CharSequence)object);
                }
                return string;
            }
            case 1: 
        }
        try {
            object = zzdq.zzhG(string);
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            zzbo.zzb("Joiner: unsupported encoding", unsupportedEncodingException);
            return string;
        }
    }

    private void zza(StringBuilder stringBuilder, String string, zza zza2, Set<Character> set) {
        stringBuilder.append(this.zza(string, zza2, set));
    }

    private void zza(Set<Character> set, String string) {
        for (int i = 0; i < string.length(); ++i) {
            set.add(Character.valueOf(string.charAt(i)));
        }
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        zzaj.zza zza2 = object.get(zzbEd);
        if (zza2 == null) {
            return zzdm.zzQm();
        }
        Object object2 = object.get(zzbEv);
        object2 = object2 != null ? zzdm.zze((zzaj.zza)object2) : "";
        Object object3 = object.get(zzbEw);
        object3 = object3 != null ? zzdm.zze((zzaj.zza)object3) : "=";
        Object object4 = zza.zzbEz;
        Object object5 = object.get(zzbEx);
        StringBuilder stringBuilder = null;
        object = object4;
        object4 = stringBuilder;
        if (object5 != null) {
            object = zzdm.zze((zzaj.zza)object5);
            if ("url".equals(object)) {
                object = zza.zzbEA;
                object4 = stringBuilder;
            } else if ("backslash".equals(object)) {
                object = zza.zzbEB;
                object4 = new HashSet();
                this.zza((Set<Character>)object4, (String)object2);
                this.zza((Set<Character>)object4, (String)object3);
                object4.remove(Character.valueOf('\\'));
            } else {
                object = (object = String.valueOf(object)).length() != 0 ? "Joiner: unsupported escape type: ".concat((String)object) : new String("Joiner: unsupported escape type: ");
                zzbo.e((String)object);
                return zzdm.zzQm();
            }
        }
        stringBuilder = new StringBuilder();
        int n = zza2.type;
        switch (n) {
            int n2;
            default: {
                this.zza(stringBuilder, zzdm.zze(zza2), (zza)((Object)object), (Set<Character>)object4);
                break;
            }
            case 3: {
                for (n2 = 0; n2 < zza2.zzly.length; ++n2) {
                    if (n2 > 0) {
                        stringBuilder.append((String)object2);
                    }
                    object5 = zzdm.zze(zza2.zzly[n2]);
                    String string = zzdm.zze(zza2.zzlz[n2]);
                    this.zza(stringBuilder, (String)object5, (zza)((Object)object), (Set<Character>)object4);
                    stringBuilder.append((String)object3);
                    this.zza(stringBuilder, string, (zza)((Object)object), (Set<Character>)object4);
                }
                break;
            }
            case 2: {
                object3 = zza2.zzlx;
                int n3 = ((zzaj.zza[])object3).length;
                n = 1;
                for (n2 = 0; n2 < n3; ++n2) {
                    zza2 = object3[n2];
                    if (n == 0) {
                        stringBuilder.append((String)object2);
                    }
                    this.zza(stringBuilder, zzdm.zze(zza2), (zza)((Object)object), (Set<Character>)object4);
                    n = 0;
                }
            }
        }
        return zzdm.zzR(stringBuilder.toString());
    }

    private static final class zza
    extends Enum<zza> {
        public static final /* enum */ zza zzbEA;
        public static final /* enum */ zza zzbEB;
        private static final /* synthetic */ zza[] zzbEC;
        public static final /* enum */ zza zzbEz;

        static {
            zzbEz = new zza();
            zzbEA = new zza();
            zzbEB = new zza();
            zzbEC = new zza[]{zzbEz, zzbEA, zzbEB};
        }

        private zza() {
        }

        public static zza[] values() {
            return (zza[])zzbEC.clone();
        }
    }

}
