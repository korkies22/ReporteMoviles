/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbuo;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzbgi {
    private static zzaj.zza zza(int n, zzai.zzf object, zzaj.zza[] arrzza, Set<Integer> set) throws zzg {
        Object object2;
        Object object3;
        if (set.contains(n)) {
            object2 = String.valueOf(set);
            object3 = new StringBuilder(90 + String.valueOf(object2).length());
            object3.append("Value cycle detected.  Current value reference: ");
            object3.append(n);
            object3.append(".  Previous value references: ");
            object3.append((String)object2);
            object3.append(".");
            zzbgi.zzhV(object3.toString());
        }
        object3 = zzbgi.zza(object.zzkI, n, "values");
        if (arrzza[n] != null) {
            return arrzza[n];
        }
        object2 = null;
        set.add(n);
        int n2 = object3.type;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        block0 : switch (n2) {
            default: {
                break;
            }
            case 7: {
                zzaj.zza zza2 = zzbgi.zzm((zzaj.zza)object3);
                object2 = zzbgi.zzn((zzaj.zza)object3);
                zza2.zzlE = new zzaj.zza[object2.zzln.length];
                int[] arrn = object2.zzln;
                n3 = arrn.length;
                n2 = 0;
                do {
                    object2 = zza2;
                    if (n5 >= n3) break block0;
                    n4 = arrn[n5];
                    zza2.zzlE[n2] = zzbgi.zza(n4, (zzai.zzf)object, arrzza, set);
                    ++n5;
                    ++n2;
                } while (true);
            }
            case 4: {
                object2 = zzbgi.zzm((zzaj.zza)object3);
                object2.zzlA = zzdm.zze(zzbgi.zza(zzbgi.zzn((zzaj.zza)object3).zzlo, (zzai.zzf)object, arrzza, set));
                break;
            }
            case 3: {
                int[] arrn;
                zzaj.zza zza3 = zzbgi.zzm((zzaj.zza)object3);
                object2 = zzbgi.zzn((zzaj.zza)object3);
                if (object2.zzlk.length != object2.zzll.length) {
                    n2 = object2.zzlk.length;
                    n5 = object2.zzll.length;
                    arrn = new int[](58);
                    arrn.append("Uneven map keys (");
                    arrn.append(n2);
                    arrn.append(") and map values (");
                    arrn.append(n5);
                    arrn.append(")");
                    zzbgi.zzhV(arrn.toString());
                }
                zza3.zzly = new zzaj.zza[object2.zzlk.length];
                zza3.zzlz = new zzaj.zza[object2.zzlk.length];
                arrn = object2.zzlk;
                n4 = arrn.length;
                n2 = n5 = 0;
                while (n5 < n4) {
                    int n6 = arrn[n5];
                    zza3.zzly[n2] = zzbgi.zza(n6, (zzai.zzf)object, arrzza, set);
                    ++n5;
                    ++n2;
                }
                arrn = object2.zzll;
                n4 = arrn.length;
                n2 = 0;
                n5 = n3;
                do {
                    object2 = zza3;
                    if (n5 >= n4) break block0;
                    n3 = arrn[n5];
                    zza3.zzlz[n2] = zzbgi.zza(n3, (zzai.zzf)object, arrzza, set);
                    ++n5;
                    ++n2;
                } while (true);
            }
            case 2: {
                int[] arrn = zzbgi.zzn((zzaj.zza)object3);
                object2 = zzbgi.zzm((zzaj.zza)object3);
                object2.zzlx = new zzaj.zza[arrn.zzlj.length];
                arrn = arrn.zzlj;
                n3 = arrn.length;
                n2 = 0;
                n5 = n4;
                while (n5 < n3) {
                    n4 = arrn[n5];
                    object2.zzlx[n2] = zzbgi.zza(n4, (zzai.zzf)object, arrzza, set);
                    ++n5;
                    ++n2;
                }
                break;
            }
            case 1: 
            case 5: 
            case 6: 
            case 8: {
                object2 = object3;
            }
        }
        if (object2 == null) {
            object = String.valueOf(object3);
            object3 = new StringBuilder(15 + String.valueOf(object).length());
            object3.append("Invalid value: ");
            object3.append((String)object);
            zzbgi.zzhV(object3.toString());
        }
        arrzza[n] = object2;
        set.remove(n);
        return object2;
    }

    private static zza zza(zzai.zzb arrn, zzai.zzf zzf2, zzaj.zza[] arrzza, int n) throws zzg {
        zzb zzb2 = zza.zzRU();
        for (int n2 : arrn.zzkt) {
            zzbun zzbun2 = zzbgi.zza(zzf2.zzkJ, n2, "properties");
            String string = zzbgi.zza(zzf2.zzkH, zzbun2.key, "keys");
            zzbun2 = zzbgi.zza(arrzza, zzbun2.value, "values");
            if (zzah.zziS.toString().equals(string)) {
                zzb2.zzo((zzaj.zza)zzbun2);
                continue;
            }
            zzb2.zzb(string, (zzaj.zza)zzbun2);
        }
        return zzb2.zzRV();
    }

    private static zze zza(zzai.zzg arrn, List<zza> arrn2, List<zza> list, List<zza> arrn3, zzai.zzf zzf2) {
        int n;
        zzf zzf3 = zze.zzRZ();
        int[] arrn4 = arrn.zzkX;
        int n2 = 0;
        int n3 = arrn4.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zzd(arrn3.get(arrn4[n]));
        }
        arrn4 = arrn.zzkY;
        n3 = arrn4.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zze((zza)arrn3.get(arrn4[n]));
        }
        arrn3 = arrn.zzkZ;
        n3 = arrn3.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zzf(arrn2.get(arrn3[n]));
        }
        for (int n4 : arrn.zzlb) {
            zzf3.zzil(zzf2.zzkI[Integer.valueOf((int)n4).intValue()].string);
        }
        arrn3 = arrn.zzla;
        n3 = arrn3.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zzg((zza)arrn2.get(arrn3[n]));
        }
        for (int n4 : arrn.zzlc) {
            zzf3.zzim(zzf2.zzkI[Integer.valueOf((int)n4).intValue()].string);
        }
        arrn2 = arrn.zzld;
        n3 = arrn2.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zzh(list.get(arrn2[n]));
        }
        for (int n4 : arrn.zzlf) {
            zzf3.zzin(zzf2.zzkI[Integer.valueOf((int)n4).intValue()].string);
        }
        arrn2 = arrn.zzle;
        n3 = arrn2.length;
        for (n = 0; n < n3; ++n) {
            zzf3.zzi(list.get(arrn2[n]));
        }
        arrn = arrn.zzlg;
        n3 = arrn.length;
        for (n = n2; n < n3; ++n) {
            n2 = arrn[n];
            zzf3.zzio(zzf2.zzkI[Integer.valueOf((int)n2).intValue()].string);
        }
        return zzf3.zzSc();
    }

    private static <T> T zza(T[] arrT, int n, String string) throws zzg {
        if (n < 0 || n >= arrT.length) {
            StringBuilder stringBuilder = new StringBuilder(45 + String.valueOf(string).length());
            stringBuilder.append("Index out of bounds detected: ");
            stringBuilder.append(n);
            stringBuilder.append(" in ");
            stringBuilder.append(string);
            zzbgi.zzhV(stringBuilder.toString());
        }
        return arrT[n];
    }

    public static zzc zzb(zzai.zzf zzf2) throws zzg {
        int n;
        Object object = zzf2.zzkI;
        int n2 = 0;
        zzbun[] arrzzbun = new zzaj.zza[((zzaj.zza[])object).length];
        for (n = 0; n < zzf2.zzkI.length; ++n) {
            zzbgi.zza(n, zzf2, (zzaj.zza[])arrzzbun, new HashSet<Integer>(0));
        }
        object = zzc.zzRW();
        ArrayList<zza> arrayList = new ArrayList<zza>();
        for (n = 0; n < zzf2.zzkL.length; ++n) {
            arrayList.add(zzbgi.zza(zzf2.zzkL[n], zzf2, (zzaj.zza[])arrzzbun, n));
        }
        ArrayList<zza> arrayList2 = new ArrayList<zza>();
        for (n = 0; n < zzf2.zzkM.length; ++n) {
            arrayList2.add(zzbgi.zza(zzf2.zzkM[n], zzf2, (zzaj.zza[])arrzzbun, n));
        }
        ArrayList<zza> arrayList3 = new ArrayList<zza>();
        for (n = 0; n < zzf2.zzkK.length; ++n) {
            zza zza2 = zzbgi.zza(zzf2.zzkK[n], zzf2, (zzaj.zza[])arrzzbun, n);
            object.zzc(zza2);
            arrayList3.add(zza2);
        }
        arrzzbun = zzf2.zzkN;
        int n3 = arrzzbun.length;
        for (n = n2; n < n3; ++n) {
            object.zzb(zzbgi.zza((zzai.zzg)arrzzbun[n], arrayList, arrayList3, arrayList2, zzf2));
        }
        object.zzik(zzf2.version);
        object.zznd(zzf2.zzkV);
        return object.zzRY();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] arrby = new byte[1024];
        int n;
        while ((n = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
        }
        return;
    }

    private static void zzhV(String string) throws zzg {
        zzbo.e(string);
        throw new zzg(string);
    }

    public static zzaj.zza zzm(zzaj.zza zza2) {
        zzaj.zza zza3 = new zzaj.zza();
        zza3.type = zza2.type;
        zza3.zzlF = (int[])zza2.zzlF.clone();
        if (zza2.zzlG) {
            zza3.zzlG = zza2.zzlG;
        }
        return zza3;
    }

    private static zzai.zzh zzn(zzaj.zza zza2) throws zzg {
        if (zza2.zza(zzai.zzh.zzlh) == null) {
            String string = String.valueOf(zza2);
            StringBuilder stringBuilder = new StringBuilder(54 + String.valueOf(string).length());
            stringBuilder.append("Expected a ServingValue and didn't get one. Value is: ");
            stringBuilder.append(string);
            zzbgi.zzhV(stringBuilder.toString());
        }
        return zza2.zza(zzai.zzh.zzlh);
    }

    public static class zza {
        private final zzaj.zza zzbFS;
        private final Map<String, zzaj.zza> zzbKA;

        private zza(Map<String, zzaj.zza> map, zzaj.zza zza2) {
            this.zzbKA = map;
            this.zzbFS = zza2;
        }

        public static zzb zzRU() {
            return new zzb();
        }

        public String toString() {
            String string = String.valueOf(this.zzRt());
            String string2 = String.valueOf(this.zzbFS);
            StringBuilder stringBuilder = new StringBuilder(32 + String.valueOf(string).length() + String.valueOf(string2).length());
            stringBuilder.append("Properties: ");
            stringBuilder.append(string);
            stringBuilder.append(" pushAfterEvaluate: ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public zzaj.zza zzPM() {
            return this.zzbFS;
        }

        public Map<String, zzaj.zza> zzRt() {
            return Collections.unmodifiableMap(this.zzbKA);
        }

        public void zza(String string, zzaj.zza zza2) {
            this.zzbKA.put(string, zza2);
        }
    }

    public static class zzb {
        private zzaj.zza zzbFS;
        private final Map<String, zzaj.zza> zzbKA = new HashMap<String, zzaj.zza>();

        private zzb() {
        }

        public zza zzRV() {
            return new zza(this.zzbKA, this.zzbFS);
        }

        public zzb zzb(String string, zzaj.zza zza2) {
            this.zzbKA.put(string, zza2);
            return this;
        }

        public zzb zzo(zzaj.zza zza2) {
            this.zzbFS = zza2;
            return this;
        }
    }

    public static class zzc {
        private final String zzaux;
        private final List<zze> zzbKx;
        private final Map<String, List<zza>> zzbKy;
        private final int zzbKz;

        private zzc(List<zze> list, Map<String, List<zza>> map, String string, int n) {
            this.zzbKx = Collections.unmodifiableList(list);
            this.zzbKy = Collections.unmodifiableMap(map);
            this.zzaux = string;
            this.zzbKz = n;
        }

        public static zzd zzRW() {
            return new zzd();
        }

        public String getVersion() {
            return this.zzaux;
        }

        public String toString() {
            String string = String.valueOf(this.zzRr());
            String string2 = String.valueOf(this.zzbKy);
            StringBuilder stringBuilder = new StringBuilder(17 + String.valueOf(string).length() + String.valueOf(string2).length());
            stringBuilder.append("Rules: ");
            stringBuilder.append(string);
            stringBuilder.append("  Macros: ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public Map<String, List<zza>> zzRX() {
            return this.zzbKy;
        }

        public List<zze> zzRr() {
            return this.zzbKx;
        }
    }

    public static class zzd {
        private String zzaux = "";
        private final List<zze> zzbKx = new ArrayList<zze>();
        private final Map<String, List<zza>> zzbKy = new HashMap<String, List<zza>>();
        private int zzbKz = 0;

        private zzd() {
        }

        public zzc zzRY() {
            return new zzc(this.zzbKx, this.zzbKy, this.zzaux, this.zzbKz);
        }

        public zzd zzb(zze zze2) {
            this.zzbKx.add(zze2);
            return this;
        }

        public zzd zzc(zza zza2) {
            List<zza> list;
            String string = zzdm.zze(zza2.zzRt().get(zzah.zzhL.toString()));
            List<zza> list2 = list = this.zzbKy.get(string);
            if (list == null) {
                list2 = new ArrayList<zza>();
                this.zzbKy.put(string, list2);
            }
            list2.add(zza2);
            return this;
        }

        public zzd zzik(String string) {
            this.zzaux = string;
            return this;
        }

        public zzd zznd(int n) {
            this.zzbKz = n;
            return this;
        }
    }

    public static class zze {
        private final List<zza> zzbKC;
        private final List<zza> zzbKD;
        private final List<zza> zzbKE;
        private final List<zza> zzbKF;
        private final List<zza> zzbLk;
        private final List<zza> zzbLl;
        private final List<String> zzbLm;
        private final List<String> zzbLn;
        private final List<String> zzbLo;
        private final List<String> zzbLp;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.zzbKC = Collections.unmodifiableList(list);
            this.zzbKD = Collections.unmodifiableList(list2);
            this.zzbKE = Collections.unmodifiableList(list3);
            this.zzbKF = Collections.unmodifiableList(list4);
            this.zzbLk = Collections.unmodifiableList(list5);
            this.zzbLl = Collections.unmodifiableList(list6);
            this.zzbLm = Collections.unmodifiableList(list7);
            this.zzbLn = Collections.unmodifiableList(list8);
            this.zzbLo = Collections.unmodifiableList(list9);
            this.zzbLp = Collections.unmodifiableList(list10);
        }

        public static zzf zzRZ() {
            return new zzf();
        }

        public String toString() {
            String string = String.valueOf(this.zzRv());
            String string2 = String.valueOf(this.zzRw());
            String string3 = String.valueOf(this.zzRx());
            String string4 = String.valueOf(this.zzRy());
            String string5 = String.valueOf(this.zzSa());
            String string6 = String.valueOf(this.zzSb());
            StringBuilder stringBuilder = new StringBuilder(102 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length() + String.valueOf(string5).length() + String.valueOf(string6).length());
            stringBuilder.append("Positive predicates: ");
            stringBuilder.append(string);
            stringBuilder.append("  Negative predicates: ");
            stringBuilder.append(string2);
            stringBuilder.append("  Add tags: ");
            stringBuilder.append(string3);
            stringBuilder.append("  Remove tags: ");
            stringBuilder.append(string4);
            stringBuilder.append("  Add macros: ");
            stringBuilder.append(string5);
            stringBuilder.append("  Remove macros: ");
            stringBuilder.append(string6);
            return stringBuilder.toString();
        }

        public List<zza> zzRv() {
            return this.zzbKC;
        }

        public List<zza> zzRw() {
            return this.zzbKD;
        }

        public List<zza> zzRx() {
            return this.zzbKE;
        }

        public List<zza> zzRy() {
            return this.zzbKF;
        }

        public List<zza> zzSa() {
            return this.zzbLk;
        }

        public List<zza> zzSb() {
            return this.zzbLl;
        }
    }

    public static class zzf {
        private final List<zza> zzbKC = new ArrayList<zza>();
        private final List<zza> zzbKD = new ArrayList<zza>();
        private final List<zza> zzbKE = new ArrayList<zza>();
        private final List<zza> zzbKF = new ArrayList<zza>();
        private final List<zza> zzbLk = new ArrayList<zza>();
        private final List<zza> zzbLl = new ArrayList<zza>();
        private final List<String> zzbLm = new ArrayList<String>();
        private final List<String> zzbLn = new ArrayList<String>();
        private final List<String> zzbLo = new ArrayList<String>();
        private final List<String> zzbLp = new ArrayList<String>();

        private zzf() {
        }

        public zze zzSc() {
            return new zze(this.zzbKC, this.zzbKD, this.zzbKE, this.zzbKF, this.zzbLk, this.zzbLl, this.zzbLm, this.zzbLn, this.zzbLo, this.zzbLp);
        }

        public zzf zzd(zza zza2) {
            this.zzbKC.add(zza2);
            return this;
        }

        public zzf zze(zza zza2) {
            this.zzbKD.add(zza2);
            return this;
        }

        public zzf zzf(zza zza2) {
            this.zzbKE.add(zza2);
            return this;
        }

        public zzf zzg(zza zza2) {
            this.zzbKF.add(zza2);
            return this;
        }

        public zzf zzh(zza zza2) {
            this.zzbLk.add(zza2);
            return this;
        }

        public zzf zzi(zza zza2) {
            this.zzbLl.add(zza2);
            return this;
        }

        public zzf zzil(String string) {
            this.zzbLo.add(string);
            return this;
        }

        public zzf zzim(String string) {
            this.zzbLp.add(string);
            return this;
        }

        public zzf zzin(String string) {
            this.zzbLm.add(string);
            return this;
        }

        public zzf zzio(String string) {
            this.zzbLn.add(string);
            return this;
        }
    }

    public static class zzg
    extends Exception {
        public zzg(String string) {
            super(string);
        }
    }

}
