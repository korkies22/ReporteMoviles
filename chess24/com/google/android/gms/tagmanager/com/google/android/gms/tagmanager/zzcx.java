/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzab;
import com.google.android.gms.tagmanager.zzac;
import com.google.android.gms.tagmanager.zzaf;
import com.google.android.gms.tagmanager.zzag;
import com.google.android.gms.tagmanager.zzah;
import com.google.android.gms.tagmanager.zzai;
import com.google.android.gms.tagmanager.zzaj;
import com.google.android.gms.tagmanager.zzak;
import com.google.android.gms.tagmanager.zzal;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzao;
import com.google.android.gms.tagmanager.zzap;
import com.google.android.gms.tagmanager.zzaq;
import com.google.android.gms.tagmanager.zzar;
import com.google.android.gms.tagmanager.zzbe;
import com.google.android.gms.tagmanager.zzbg;
import com.google.android.gms.tagmanager.zzbj;
import com.google.android.gms.tagmanager.zzbk;
import com.google.android.gms.tagmanager.zzbl;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzbq;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzbs;
import com.google.android.gms.tagmanager.zzcc;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzcf;
import com.google.android.gms.tagmanager.zzch;
import com.google.android.gms.tagmanager.zzck;
import com.google.android.gms.tagmanager.zzcm;
import com.google.android.gms.tagmanager.zzcn;
import com.google.android.gms.tagmanager.zzco;
import com.google.android.gms.tagmanager.zzcp;
import com.google.android.gms.tagmanager.zzcq;
import com.google.android.gms.tagmanager.zzcr;
import com.google.android.gms.tagmanager.zzcs;
import com.google.android.gms.tagmanager.zzcw;
import com.google.android.gms.tagmanager.zzcy;
import com.google.android.gms.tagmanager.zzcz;
import com.google.android.gms.tagmanager.zzdg;
import com.google.android.gms.tagmanager.zzdi;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzdn;
import com.google.android.gms.tagmanager.zzdo;
import com.google.android.gms.tagmanager.zzdp;
import com.google.android.gms.tagmanager.zzdq;
import com.google.android.gms.tagmanager.zze;
import com.google.android.gms.tagmanager.zzf;
import com.google.android.gms.tagmanager.zzg;
import com.google.android.gms.tagmanager.zzh;
import com.google.android.gms.tagmanager.zzi;
import com.google.android.gms.tagmanager.zzj;
import com.google.android.gms.tagmanager.zzl;
import com.google.android.gms.tagmanager.zzm;
import com.google.android.gms.tagmanager.zzn;
import com.google.android.gms.tagmanager.zzr;
import com.google.android.gms.tagmanager.zzs;
import com.google.android.gms.tagmanager.zzu;
import com.google.android.gms.tagmanager.zzv;
import com.google.android.gms.tagmanager.zzw;
import com.google.android.gms.tagmanager.zzy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzcx {
    private static final zzce<zzaj.zza> zzbFB = new zzce<zzaj.zza>(zzdm.zzQm(), true);
    private final DataLayer zzbCT;
    private final zzbgi.zzc zzbFC;
    private final zzaj zzbFD;
    private final Map<String, zzam> zzbFE;
    private final Map<String, zzam> zzbFF;
    private final Map<String, zzam> zzbFG;
    private final zzl<zzbgi.zza, zzce<zzaj.zza>> zzbFH;
    private final zzl<String, zzb> zzbFI;
    private final Set<zzbgi.zze> zzbFJ;
    private final Map<String, zzc> zzbFK;
    private volatile String zzbFL;
    private int zzbFM;

    public zzcx(Context object, zzbgi.zzc object22, DataLayer object3, zzu.zza object42, zzu.zza zza2, zzaj zzaj2) {
        Object object23;
        void var6_12;
        void var5_11;
        Object object4;
        if (object22 == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzbFC = object22;
        this.zzbFJ = new HashSet<zzbgi.zze>(object22.zzRr());
        this.zzbCT = object4;
        this.zzbFD = var6_12;
        zzm.zza<zzbgi.zza, zzce<zzaj.zza>> zza3 = new zzm.zza<zzbgi.zza, zzce<zzaj.zza>>(this){

            @Override
            public /* synthetic */ int sizeOf(Object object, Object object2) {
                return this.zza((zzbgi.zza)object, (zzce)object2);
            }

            public int zza(zzbgi.zza zza2, zzce<zzaj.zza> zzce2) {
                return zzce2.getObject().zzacY();
            }
        };
        this.zzbFH = new zzm().zza(1048576, zza3);
        zzm.zza<String, zzb> zza4 = new zzm.zza<String, zzb>(this){

            @Override
            public /* synthetic */ int sizeOf(Object object, Object object2) {
                return this.zza((String)object, (zzb)object2);
            }

            public int zza(String string, zzb zzb2) {
                return string.length() + zzb2.getSize();
            }
        };
        this.zzbFI = new zzm().zza(1048576, zza4);
        this.zzbFE = new HashMap<String, zzam>();
        this.zzb(new zzj((Context)object));
        this.zzb(new zzu((zzu.zza)var5_11));
        this.zzb(new zzy((DataLayer)object4));
        this.zzb(new zzdn((Context)object, (DataLayer)object4));
        this.zzbFF = new HashMap<String, zzam>();
        this.zzc(new zzs());
        this.zzc(new zzag());
        this.zzc(new zzah());
        this.zzc(new zzao());
        this.zzc(new zzap());
        this.zzc(new zzbk());
        this.zzc(new zzbl());
        this.zzc(new zzcn());
        this.zzc(new zzdg());
        this.zzbFG = new HashMap<String, zzam>();
        this.zza(new com.google.android.gms.tagmanager.zzb((Context)object));
        this.zza(new com.google.android.gms.tagmanager.zzc((Context)object));
        this.zza(new zze((Context)object));
        this.zza(new zzf((Context)object));
        this.zza(new zzg((Context)object));
        this.zza(new zzh((Context)object));
        this.zza(new zzi((Context)object));
        this.zza(new zzn());
        this.zza(new zzr(this.zzbFC.getVersion()));
        this.zza(new zzu((zzu.zza)object23));
        this.zza(new zzw((DataLayer)object4));
        this.zza(new zzab((Context)object));
        this.zza(new zzac());
        this.zza(new zzaf());
        this.zza(new zzak(this));
        this.zza(new zzaq());
        this.zza(new zzar());
        this.zza(new zzbe((Context)object));
        this.zza(new zzbg());
        this.zza(new zzbj());
        this.zza(new zzbq());
        this.zza(new zzbs((Context)object));
        this.zza(new zzcf());
        this.zza(new zzch());
        this.zza(new zzck());
        this.zza(new zzcm());
        this.zza(new zzco((Context)object));
        this.zza(new zzcy());
        this.zza(new zzcz());
        this.zza(new zzdi());
        this.zza(new zzdo());
        this.zzbFK = new HashMap<String, zzc>();
        for (zzbgi.zze zze2 : this.zzbFJ) {
            int n = 0;
            int n2 = 0;
            do {
                if (n2 >= zze2.zzSa().size()) break;
                object4 = zze2.zzSa().get(n2);
                object23 = zzcx.zzh(this.zzbFK, zzcx.zza((zzbgi.zza)object4));
                object23.zza(zze2);
                object23.zza(zze2, (zzbgi.zza)object4);
                object23.zza(zze2, "Unknown");
                ++n2;
            } while (true);
            for (int i = n; i < zze2.zzSb().size(); ++i) {
                object4 = zze2.zzSb().get(i);
                object23 = zzcx.zzh(this.zzbFK, zzcx.zza((zzbgi.zza)object4));
                object23.zza(zze2);
                object23.zzb(zze2, (zzbgi.zza)object4);
                object23.zzb(zze2, "Unknown");
            }
        }
        for (Map.Entry entry : this.zzbFC.zzRX().entrySet()) {
            for (Object object23 : (List)entry.getValue()) {
                if (zzdm.zzi(object23.zzRt().get(com.google.android.gms.internal.zzah.zziq.toString())).booleanValue()) continue;
                zzcx.zzh(this.zzbFK, (String)entry.getKey()).zzb((zzbgi.zza)object23);
            }
        }
    }

    private String zzPK() {
        if (this.zzbFM <= 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.zzbFM));
        for (int i = 2; i < this.zzbFM; ++i) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    private zzce<zzaj.zza> zza(zzaj.zza object, Set<String> object2, zzdp object3) {
        if (!object.zzlG) {
            return new zzce<zzaj.zza>((zzaj.zza)object, true);
        }
        int n = object.type;
        if (n != 7) {
            switch (n) {
                default: {
                    n = object.type;
                    object = new StringBuilder(25);
                    object.append("Unknown type: ");
                    object.append(n);
                    zzbo.e(object.toString());
                    return zzbFB;
                }
                case 4: {
                    if (object2.contains(object.zzlA)) {
                        object = String.valueOf(object.zzlA);
                        object2 = String.valueOf(object2.toString());
                        object3 = new StringBuilder(79 + String.valueOf(object).length() + String.valueOf(object2).length());
                        object3.append("Macro cycle detected.  Current macro reference: ");
                        object3.append((String)object);
                        object3.append(".  Previous macro references: ");
                        object3.append((String)object2);
                        object3.append(".");
                        zzbo.e(object3.toString());
                        return zzbFB;
                    }
                    object2.add((String)object.zzlA);
                    object3 = zzdq.zza(this.zza(object.zzlA, (Set<String>)object2, object3.zzPt()), object.zzlF);
                    object2.remove(object.zzlA);
                    return object3;
                }
                case 3: {
                    zzaj.zza zza2 = zzbgi.zzm((zzaj.zza)object);
                    if (object.zzly.length != object.zzlz.length) {
                        object = (object = String.valueOf(object.toString())).length() != 0 ? "Invalid serving value: ".concat((String)object) : new String("Invalid serving value: ");
                        zzbo.e((String)object);
                        return zzbFB;
                    }
                    zza2.zzly = new zzaj.zza[object.zzly.length];
                    zza2.zzlz = new zzaj.zza[object.zzly.length];
                    for (n = 0; n < object.zzly.length; ++n) {
                        zzce<zzaj.zza> zzce2 = this.zza(object.zzly[n], (Set<String>)object2, object3.zzmS(n));
                        zzce<zzaj.zza> zzce3 = this.zza(object.zzlz[n], (Set<String>)object2, object3.zzmT(n));
                        if (zzce2 != zzbFB && zzce3 != zzbFB) {
                            zza2.zzly[n] = zzce2.getObject();
                            zza2.zzlz[n] = zzce3.getObject();
                            continue;
                        }
                        return zzbFB;
                    }
                    return new zzce<zzaj.zza>(zza2, false);
                }
                case 2: 
            }
            zzaj.zza zza3 = zzbgi.zzm((zzaj.zza)object);
            zza3.zzlx = new zzaj.zza[object.zzlx.length];
            for (n = 0; n < object.zzlx.length; ++n) {
                zzce<zzaj.zza> zzce4 = this.zza(object.zzlx[n], (Set<String>)object2, object3.zzmR(n));
                if (zzce4 == zzbFB) {
                    return zzbFB;
                }
                zza3.zzlx[n] = zzce4.getObject();
            }
            return new zzce<zzaj.zza>(zza3, false);
        }
        zzaj.zza zza4 = zzbgi.zzm((zzaj.zza)object);
        zza4.zzlE = new zzaj.zza[object.zzlE.length];
        for (n = 0; n < object.zzlE.length; ++n) {
            zzce<zzaj.zza> zzce5 = this.zza(object.zzlE[n], (Set<String>)object2, object3.zzmU(n));
            if (zzce5 == zzbFB) {
                return zzbFB;
            }
            zza4.zzlE[n] = zzce5.getObject();
        }
        return new zzce<zzaj.zza>(zza4, false);
    }

    private zzce<zzaj.zza> zza(String string, Set<String> object, zzbr zzce2) {
        ++this.zzbFM;
        Object object2 = this.zzbFI.get(string);
        if (object2 != null) {
            this.zza(object2.zzPM(), (Set<String>)object);
            --this.zzbFM;
            return object2.zzPL();
        }
        object2 = this.zzbFK.get(string);
        if (object2 == null) {
            object = String.valueOf(this.zzPK());
            zzce2 = new StringBuilder(15 + String.valueOf(object).length() + String.valueOf(string).length());
            zzce2.append((String)object);
            zzce2.append("Invalid macro: ");
            zzce2.append(string);
            zzbo.e(zzce2.toString());
            --this.zzbFM;
            return zzbFB;
        }
        zzce<Set<zzbgi.zza>> zzce3 = this.zza(string, object2.zzPN(), object2.zzPO(), object2.zzPP(), object2.zzPR(), object2.zzPQ(), (Set<String>)object, zzce2.zzOU());
        if (zzce3.getObject().isEmpty()) {
            object2 = object2.zzPS();
        } else {
            if (zzce3.getObject().size() > 1) {
                object2 = String.valueOf(this.zzPK());
                StringBuilder stringBuilder = new StringBuilder(37 + String.valueOf(object2).length() + String.valueOf(string).length());
                stringBuilder.append((String)object2);
                stringBuilder.append("Multiple macros active for macroName ");
                stringBuilder.append(string);
                zzbo.zzbe(stringBuilder.toString());
            }
            object2 = zzce3.getObject().iterator().next();
        }
        if (object2 == null) {
            --this.zzbFM;
            return zzbFB;
        }
        zzce2 = this.zza(this.zzbFG, (zzbgi.zza)object2, (Set<String>)object, zzce2.zzPl());
        boolean bl = zzce3.zzPu() && zzce2.zzPu();
        zzce2 = zzce2 == zzbFB ? zzbFB : new zzce<zzaj.zza>(zzce2.getObject(), bl);
        object2 = object2.zzPM();
        if (zzce2.zzPu()) {
            this.zzbFI.zzi(string, new zzb(zzce2, (zzaj.zza)object2));
        }
        this.zza((zzaj.zza)object2, (Set<String>)object);
        --this.zzbFM;
        return zzce2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private zzce<zzaj.zza> zza(Map<String, zzam> var1_1, zzbgi.zza var2_2, Set<String> var3_3, zzcp var4_4) {
        block10 : {
            var7_5 = var2_2.zzRt().get(com.google.android.gms.internal.zzah.zzhz.toString());
            if (var7_5 != null) break block10;
            var1_1 = "No function id in properties";
            ** GOTO lbl17
        }
        var7_5 = var7_5.zzlB;
        if ((var1_1 = (zzam)var1_1.get(var7_5)) != null) {
            var8_6 = this.zzbFH.get((zzbgi.zza)var2_2);
            if (var8_6 != null) {
                return var8_6;
            }
            var8_6 = new HashMap<K, V>();
            var9_7 = var2_2.zzRt().entrySet().iterator();
            var6_8 = true;
            var5_9 = true;
        } else {
            var1_1 = String.valueOf(var7_5).concat(" has no backing implementation.");
lbl17: // 3 sources:
            do {
                zzbo.e((String)var1_1);
                return zzcx.zzbFB;
                break;
            } while (true);
        }
        while (var9_7.hasNext()) {
            var10_10 = var9_7.next();
            var11_11 = var4_4.zzhp(var10_10.getKey());
            var11_11 = this.zza(var10_10.getValue(), (Set<String>)var3_3, var11_11.zzd(var10_10.getValue()));
            if (var11_11 == zzcx.zzbFB) {
                return zzcx.zzbFB;
            }
            if (var11_11.zzPu()) {
                var2_2.zza(var10_10.getKey(), (zzaj.zza)var11_11.getObject());
            } else {
                var5_9 = false;
            }
            var8_6.put(var10_10.getKey(), (zzaj.zza)var11_11.getObject());
        }
        if (!var1_1.zzf(var8_6.keySet())) {
            var1_1 = String.valueOf(var1_1.zzPh());
            var2_2 = String.valueOf(var8_6.keySet());
            var3_3 = new StringBuilder(43 + String.valueOf(var7_5).length() + String.valueOf(var1_1).length() + String.valueOf(var2_2).length());
            var3_3.append("Incorrect keys for function ");
            var3_3.append((String)var7_5);
            var3_3.append(" required ");
            var3_3.append((String)var1_1);
            var3_3.append(" had ");
            var3_3.append((String)var2_2);
            var1_1 = var3_3.toString();
            ** continue;
        }
        if (!var5_9 || !var1_1.zzOw()) {
            var6_8 = false;
        }
        var1_1 = new zzce<zzaj.zza>(var1_1.zzY((Map<String, zzaj.zza>)var8_6), var6_8);
        if (var6_8 == false) return var1_1;
        this.zzbFH.zzi((zzbgi.zza)var2_2, (zzce<zzaj.zza>)var1_1);
        return var1_1;
    }

    private zzce<Set<zzbgi.zza>> zza(Set<zzbgi.zze> object, Set<String> set, zza zza2, zzcw zzcw2) {
        boolean bl;
        HashSet<zzbgi.zza> hashSet = new HashSet<zzbgi.zza>();
        HashSet<zzbgi.zza> hashSet2 = new HashSet<zzbgi.zza>();
        object = object.iterator();
        block0 : do {
            bl = true;
            while (object.hasNext()) {
                zzcs zzcs2;
                zzbgi.zze zze2 = (zzbgi.zze)object.next();
                zzce<Boolean> zzce2 = this.zza(zze2, set, zzcs2 = zzcw2.zzPs());
                if (zzce2.getObject().booleanValue()) {
                    zza2.zza(zze2, hashSet, hashSet2, zzcs2);
                }
                if (bl && zzce2.zzPu()) continue block0;
                bl = false;
            }
            break;
        } while (true);
        hashSet.removeAll(hashSet2);
        return new zzce<Set<zzbgi.zza>>(hashSet, bl);
    }

    private static String zza(zzbgi.zza zza2) {
        return zzdm.zze(zza2.zzRt().get(com.google.android.gms.internal.zzah.zzhL.toString()));
    }

    private void zza(zzaj.zza iterator, Set<String> object2) {
        if (iterator == null) {
            return;
        }
        if ((iterator = this.zza((zzaj.zza)((Object)iterator), (Set<String>)object2, new zzcc())) == zzbFB) {
            return;
        }
        if ((iterator = zzdm.zzj((zzaj.zza)iterator.getObject())) instanceof Map) {
            iterator = (Map)((Object)iterator);
            this.zzbCT.push((Map<String, Object>)((Object)iterator));
            return;
        }
        if (iterator instanceof List) {
            for (Object object2 : (List)((Object)iterator)) {
                if (object2 instanceof Map) {
                    object2 = (Map)object2;
                    this.zzbCT.push((Map<String, Object>)object2);
                    continue;
                }
                zzbo.zzbe("pushAfterEvaluate: value not a Map");
            }
        } else {
            zzbo.zzbe("pushAfterEvaluate: value not a Map or List");
        }
    }

    private static void zza(Map<String, zzam> object, zzam zzam2) {
        if (object.containsKey(zzam2.zzPg())) {
            object = String.valueOf(zzam2.zzPg());
            object = object.length() != 0 ? "Duplicate function type name: ".concat((String)object) : new String("Duplicate function type name: ");
            throw new IllegalArgumentException((String)object);
        }
        object.put((String)zzam2.zzPg(), (zzam)zzam2);
    }

    private static zzc zzh(Map<String, zzc> map, String string) {
        zzc zzc2;
        zzc zzc3 = zzc2 = map.get(string);
        if (zzc2 == null) {
            zzc3 = new zzc();
            map.put(string, zzc3);
        }
        return zzc3;
    }

    public void zzN(List<zzai.zzi> object) {
        synchronized (this) {
            object = object.iterator();
            while (object.hasNext()) {
                Object object2 = (zzai.zzi)object.next();
                if (object2.name != null && object2.name.startsWith("gaExperiment:")) {
                    zzal.zza(this.zzbCT, (zzai.zzi)object2);
                    continue;
                }
                object2 = String.valueOf(object2);
                StringBuilder stringBuilder = new StringBuilder(22 + String.valueOf(object2).length());
                stringBuilder.append("Ignored supplemental: ");
                stringBuilder.append((String)object2);
                zzbo.v(stringBuilder.toString());
            }
            return;
        }
    }

    String zzPJ() {
        synchronized (this) {
            String string = this.zzbFL;
            return string;
        }
    }

    zzce<Boolean> zza(zzbgi.zza object, Set<String> object2, zzcp zzcp2) {
        object = this.zza(this.zzbFF, (zzbgi.zza)object, (Set<String>)object2, zzcp2);
        object2 = zzdm.zzi((zzaj.zza)object.getObject());
        zzdm.zzR(object2);
        return new zzce<Object>(object2, object.zzPu());
    }

    zzce<Boolean> zza(zzbgi.zze object, Set<String> set, zzcs zzcs2) {
        boolean bl;
        zzce<Boolean> zzce2 = object.zzRw().iterator();
        block0 : do {
            bl = true;
            while (zzce2.hasNext()) {
                zzce<Boolean> zzce3 = this.zza(zzce2.next(), set, zzcs2.zzPm());
                if (zzce3.getObject().booleanValue()) {
                    zzdm.zzR(false);
                    return new zzce<Boolean>(false, zzce3.zzPu());
                }
                if (bl && zzce3.zzPu()) continue block0;
                bl = false;
            }
            break;
        } while (true);
        object = object.zzRv().iterator();
        while (object.hasNext()) {
            zzce2 = this.zza((zzbgi.zza)object.next(), set, zzcs2.zzPn());
            if (!((Boolean)zzce2.getObject()).booleanValue()) {
                zzdm.zzR(false);
                return new zzce<Boolean>(false, zzce2.zzPu());
            }
            if (bl && zzce2.zzPu()) {
                bl = true;
                continue;
            }
            bl = false;
        }
        zzdm.zzR(true);
        return new zzce<Boolean>(true, bl);
    }

    zzce<Set<zzbgi.zza>> zza(String string, Set<zzbgi.zze> set, final Map<zzbgi.zze, List<zzbgi.zza>> map, final Map<zzbgi.zze, List<String>> map2, final Map<zzbgi.zze, List<zzbgi.zza>> map3, final Map<zzbgi.zze, List<String>> map4, Set<String> set2, zzcw zzcw2) {
        return this.zza(set, set2, new zza(this){

            @Override
            public void zza(zzbgi.zze zze2, Set<zzbgi.zza> collection, Set<zzbgi.zza> set, zzcs zzcs2) {
                List list = (List)map.get(zze2);
                map2.get(zze2);
                if (list != null) {
                    collection.addAll(list);
                    zzcs2.zzPo();
                }
                collection = (List)map3.get(zze2);
                map4.get(zze2);
                if (collection != null) {
                    set.addAll(collection);
                    zzcs2.zzPp();
                }
            }
        }, zzcw2);
    }

    zzce<Set<zzbgi.zza>> zza(Set<zzbgi.zze> set, zzcw zzcw2) {
        return this.zza(set, new HashSet<String>(), new zza(this){

            @Override
            public void zza(zzbgi.zze zze2, Set<zzbgi.zza> set, Set<zzbgi.zza> set2, zzcs zzcs2) {
                set.addAll(zze2.zzRx());
                set2.addAll(zze2.zzRy());
                zzcs2.zzPq();
                zzcs2.zzPr();
            }
        }, zzcw2);
    }

    void zza(zzam zzam2) {
        zzcx.zza(this.zzbFG, zzam2);
    }

    void zzb(zzam zzam2) {
        zzcx.zza(this.zzbFE, zzam2);
    }

    void zzc(zzam zzam2) {
        zzcx.zza(this.zzbFF, zzam2);
    }

    public void zzgX(String object) {
        synchronized (this) {
            this.zzhu((String)object);
            object = this.zzbFD.zzhk((String)object).zzPf();
            for (zzbgi.zza zza2 : this.zza(this.zzbFJ, object.zzOU()).getObject()) {
                this.zza(this.zzbFE, zza2, new HashSet<String>(), object.zzOT());
            }
            this.zzhu(null);
            return;
        }
    }

    public zzce<zzaj.zza> zzht(String string) {
        this.zzbFM = 0;
        zzai zzai2 = this.zzbFD.zzhj(string);
        return this.zza(string, new HashSet<String>(), zzai2.zzPe());
    }

    void zzhu(String string) {
        synchronized (this) {
            this.zzbFL = string;
            return;
        }
    }

    static interface zza {
        public void zza(zzbgi.zze var1, Set<zzbgi.zza> var2, Set<zzbgi.zza> var3, zzcs var4);
    }

    private static class zzb {
        private zzce<zzaj.zza> zzbFR;
        private zzaj.zza zzbFS;

        public zzb(zzce<zzaj.zza> zzce2, zzaj.zza zza2) {
            this.zzbFR = zzce2;
            this.zzbFS = zza2;
        }

        public int getSize() {
            int n = this.zzbFR.getObject().zzacY();
            int n2 = this.zzbFS == null ? 0 : this.zzbFS.zzacY();
            return n + n2;
        }

        public zzce<zzaj.zza> zzPL() {
            return this.zzbFR;
        }

        public zzaj.zza zzPM() {
            return this.zzbFS;
        }
    }

    private static class zzc {
        private final Set<zzbgi.zze> zzbFJ = new HashSet<zzbgi.zze>();
        private final Map<zzbgi.zze, List<zzbgi.zza>> zzbFT = new HashMap<zzbgi.zze, List<zzbgi.zza>>();
        private final Map<zzbgi.zze, List<zzbgi.zza>> zzbFU = new HashMap<zzbgi.zze, List<zzbgi.zza>>();
        private final Map<zzbgi.zze, List<String>> zzbFV = new HashMap<zzbgi.zze, List<String>>();
        private final Map<zzbgi.zze, List<String>> zzbFW = new HashMap<zzbgi.zze, List<String>>();
        private zzbgi.zza zzbFX;

        public Set<zzbgi.zze> zzPN() {
            return this.zzbFJ;
        }

        public Map<zzbgi.zze, List<zzbgi.zza>> zzPO() {
            return this.zzbFT;
        }

        public Map<zzbgi.zze, List<String>> zzPP() {
            return this.zzbFV;
        }

        public Map<zzbgi.zze, List<String>> zzPQ() {
            return this.zzbFW;
        }

        public Map<zzbgi.zze, List<zzbgi.zza>> zzPR() {
            return this.zzbFU;
        }

        public zzbgi.zza zzPS() {
            return this.zzbFX;
        }

        public void zza(zzbgi.zze zze2) {
            this.zzbFJ.add(zze2);
        }

        public void zza(zzbgi.zze zze2, zzbgi.zza zza2) {
            List<zzbgi.zza> list;
            List<zzbgi.zza> list2 = list = this.zzbFT.get(zze2);
            if (list == null) {
                list2 = new ArrayList<zzbgi.zza>();
                this.zzbFT.put(zze2, list2);
            }
            list2.add(zza2);
        }

        public void zza(zzbgi.zze zze2, String string) {
            List<String> list;
            List<String> list2 = list = this.zzbFV.get(zze2);
            if (list == null) {
                list2 = new ArrayList<String>();
                this.zzbFV.put(zze2, list2);
            }
            list2.add(string);
        }

        public void zzb(zzbgi.zza zza2) {
            this.zzbFX = zza2;
        }

        public void zzb(zzbgi.zze zze2, zzbgi.zza zza2) {
            List<zzbgi.zza> list;
            List<zzbgi.zza> list2 = list = this.zzbFU.get(zze2);
            if (list == null) {
                list2 = new ArrayList<zzbgi.zza>();
                this.zzbFU.put(zze2, list2);
            }
            list2.add(zza2);
        }

        public void zzb(zzbgi.zze zze2, String string) {
            List<String> list;
            List<String> list2 = list = this.zzbFW.get(zze2);
            if (list == null) {
                list2 = new ArrayList<String>();
                this.zzbFW.put(zze2, list2);
            }
            list2.add(string);
        }
    }

}
