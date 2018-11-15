/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzdm {
    private static final Object zzbGG;
    private static Long zzbGH;
    private static Double zzbGI;
    private static zzdl zzbGJ;
    private static String zzbGK;
    private static Boolean zzbGL;
    private static List<Object> zzbGM;
    private static Map<Object, Object> zzbGN;
    private static zzaj.zza zzbGO;

    static {
        zzbGH = new Long(0L);
        zzbGI = new Double(0.0);
        zzbGJ = zzdl.zzax(0L);
        zzbGK = new String("");
        zzbGL = new Boolean(false);
        zzbGM = new ArrayList<Object>(0);
        zzbGN = new HashMap<Object, Object>();
        zzbGO = zzdm.zzR(zzbGK);
    }

    private static double getDouble(Object object) {
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        zzbo.e("getDouble received non-Number");
        return 0.0;
    }

    public static String zzM(Object object) {
        if (object == null) {
            return zzbGK;
        }
        return object.toString();
    }

    public static zzdl zzN(Object object) {
        if (object instanceof zzdl) {
            return (zzdl)object;
        }
        if (zzdm.zzT(object)) {
            return zzdl.zzax(zzdm.zzU(object));
        }
        if (zzdm.zzS(object)) {
            return zzdl.zza(zzdm.getDouble(object));
        }
        return zzdm.zzhA(zzdm.zzM(object));
    }

    public static Long zzO(Object object) {
        if (zzdm.zzT(object)) {
            return zzdm.zzU(object);
        }
        return zzdm.zzhB(zzdm.zzM(object));
    }

    public static Double zzP(Object object) {
        if (zzdm.zzS(object)) {
            return zzdm.getDouble(object);
        }
        return zzdm.zzhC(zzdm.zzM(object));
    }

    public static Boolean zzQ(Object object) {
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        return zzdm.zzhD(zzdm.zzM(object));
    }

    public static Object zzQg() {
        return null;
    }

    public static Long zzQh() {
        return zzbGH;
    }

    public static Double zzQi() {
        return zzbGI;
    }

    public static Boolean zzQj() {
        return zzbGL;
    }

    public static zzdl zzQk() {
        return zzbGJ;
    }

    public static String zzQl() {
        return zzbGK;
    }

    public static zzaj.zza zzQm() {
        return zzbGO;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static zzaj.zza zzR(Object var0) {
        block15 : {
            block14 : {
                block13 : {
                    block12 : {
                        block11 : {
                            var3_1 = new zzaj.zza();
                            if (var0 instanceof zzaj.zza) {
                                return (zzaj.zza)var0;
                            }
                            var2_2 = var0 instanceof String;
                            var1_3 = false;
                            if (!var2_2) break block11;
                            var3_1.type = 1;
                            var0 = (String)var0;
                            ** GOTO lbl31
                        }
                        if (!(var0 instanceof List)) break block12;
                        var3_1.type = 2;
                        var4_4 = (List)var0;
                        var0 = new ArrayList<E>(var4_4.size());
                        var4_4 = var4_4.iterator();
                        var1_3 = false;
                        ** GOTO lbl46
                    }
                    if (!(var0 instanceof Map)) break block13;
                    var3_1.type = 3;
                    var5_7 = ((Map)var0).entrySet();
                    var0 = new ArrayList<E>(var5_7.size());
                    var4_5 = new ArrayList<Object>(var5_7.size());
                    var5_7 = var5_7.iterator();
                    var1_3 = false;
                    break block14;
                }
                if (zzdm.zzS(var0)) {
                    var3_1.type = 1;
                    var0 = var0.toString();
lbl31: // 2 sources:
                    var3_1.string = var0;
                } else if (zzdm.zzT(var0)) {
                    var3_1.type = 6;
                    var3_1.zzlC = zzdm.zzU(var0);
                } else if (var0 instanceof Boolean) {
                    var3_1.type = 8;
                    var3_1.zzlD = (Boolean)var0;
                } else {
                    var0 = var0 == null ? "null" : var0.getClass().toString();
                    var0 = String.valueOf(var0);
                    var0 = var0.length() != 0 ? "Converting to Value from unknown object type: ".concat((String)var0) : new String("Converting to Value from unknown object type: ");
                    zzbo.e((String)var0);
                    return zzdm.zzbGO;
lbl46: // 2 sources:
                    while (var4_4.hasNext()) {
                        var5_6 = zzdm.zzR(var4_4.next());
                        if (var5_6 == zzdm.zzbGO) {
                            return zzdm.zzbGO;
                        }
                        var1_3 = var1_3 || var5_6.zzlG;
                        var0.add(var5_6);
                    }
                    var3_1.zzlx = var0.toArray(new zzaj.zza[0]);
                }
                break block15;
            }
            while (var5_7.hasNext()) {
                var7_9 = (Map.Entry)var5_7.next();
                var6_8 = zzdm.zzR(var7_9.getKey());
                var7_9 = zzdm.zzR(var7_9.getValue());
                if (var6_8 == zzdm.zzbGO) return zzdm.zzbGO;
                if (var7_9 == zzdm.zzbGO) {
                    return zzdm.zzbGO;
                }
                var1_3 = var1_3 || var6_8.zzlG || var7_9.zzlG;
                var0.add(var6_8);
                var4_5.add(var7_9);
            }
            var3_1.zzly = var0.toArray(new zzaj.zza[0]);
            var3_1.zzlz = var4_5.toArray(new zzaj.zza[0]);
        }
        var3_1.zzlG = var1_3;
        return var3_1;
    }

    private static boolean zzS(Object object) {
        if (!(object instanceof Double || object instanceof Float || object instanceof zzdl && ((zzdl)object).zzQb())) {
            return false;
        }
        return true;
    }

    private static boolean zzT(Object object) {
        if (!(object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof zzdl && ((zzdl)object).zzQc())) {
            return false;
        }
        return true;
    }

    private static long zzU(Object object) {
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        zzbo.e("getInt64 received non-Number");
        return 0L;
    }

    public static String zze(zzaj.zza zza2) {
        return zzdm.zzM(zzdm.zzj(zza2));
    }

    public static zzdl zzf(zzaj.zza zza2) {
        return zzdm.zzN(zzdm.zzj(zza2));
    }

    public static Long zzg(zzaj.zza zza2) {
        return zzdm.zzO(zzdm.zzj(zza2));
    }

    public static Double zzh(zzaj.zza zza2) {
        return zzdm.zzP(zzdm.zzj(zza2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static zzdl zzhA(String string) {
        try {
            return zzdl.zzhy(string);
        }
        catch (NumberFormatException numberFormatException) {}
        StringBuilder stringBuilder = new StringBuilder(33 + String.valueOf(string).length());
        stringBuilder.append("Failed to convert '");
        stringBuilder.append(string);
        stringBuilder.append("' to a number.");
        zzbo.e(stringBuilder.toString());
        return zzbGJ;
    }

    private static Long zzhB(String object) {
        if ((object = zzdm.zzhA((String)object)) == zzbGJ) {
            return zzbGH;
        }
        return object.longValue();
    }

    private static Double zzhC(String object) {
        if ((object = zzdm.zzhA((String)object)) == zzbGJ) {
            return zzbGI;
        }
        return object.doubleValue();
    }

    private static Boolean zzhD(String string) {
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        return zzbGL;
    }

    public static zzaj.zza zzhz(String string) {
        zzaj.zza zza2 = new zzaj.zza();
        zza2.type = 5;
        zza2.zzlB = string;
        return zza2;
    }

    public static Boolean zzi(zzaj.zza zza2) {
        return zzdm.zzQ(zzdm.zzj(zza2));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static Object zzj(zzaj.zza var0) {
        if (var0 == null) {
            return null;
        }
        var4_1 = var0.type;
        var3_2 = 0;
        var2_3 = 0;
        var1_4 = 0;
        switch (var4_1) {
            default: {
                var1_4 = var0.type;
                var0 = new StringBuilder(46);
                var0.append("Failed to convert a value of type: ");
                var0.append(var1_4);
                var0 = var0.toString();
                ** GOTO lbl36
            }
            case 8: {
                return var0.zzlD;
            }
            case 7: {
                var5_5 = new StringBuffer();
                var0 = var0.zzlE;
                var2_3 = ((Object)var0).length;
                while (var1_4 < var2_3) {
                    var6_8 = zzdm.zze((zzaj.zza)var0[var1_4]);
                    if (var6_8 == zzdm.zzbGK) {
                        return null;
                    }
                    var5_5.append(var6_8);
                    ++var1_4;
                }
                return var5_5.toString();
            }
            case 6: {
                return var0.zzlC;
            }
            case 5: {
                var0 = "Trying to convert a function id to object";
                ** GOTO lbl36
            }
            case 4: {
                var0 = "Trying to convert a macro reference to object";
lbl36: // 3 sources:
                zzbo.e((String)var0);
                return null;
            }
            case 3: {
                if (var0.zzly.length != var0.zzlz.length) {
                    var0 = (var0 = String.valueOf(var0.toString())).length() != 0 ? "Converting an invalid value to object: ".concat((String)var0) : new String("Converting an invalid value to object: ");
                    zzbo.e((String)var0);
                    return null;
                }
                var5_6 = new HashMap<Object, Object>(var0.zzlz.length);
                var1_4 = var3_2;
                while (var1_4 < var0.zzly.length) {
                    var6_9 = zzdm.zzj(var0.zzly[var1_4]);
                    var7_11 = zzdm.zzj(var0.zzlz[var1_4]);
                    if (var6_9 == null) return null;
                    if (var7_11 == null) {
                        return null;
                    }
                    var5_6.put(var6_9, var7_11);
                    ++var1_4;
                }
                return var5_6;
            }
            case 2: {
                var5_7 = new ArrayList<Object>(var0.zzlx.length);
                var0 = var0.zzlx;
                var3_2 = ((Object)var0).length;
                var1_4 = var2_3;
                while (var1_4 < var3_2) {
                    var6_10 = zzdm.zzj((zzaj.zza)var0[var1_4]);
                    if (var6_10 == null) {
                        return null;
                    }
                    var5_7.add(var6_10);
                    ++var1_4;
                }
                return var5_7;
            }
            case 1: 
        }
        return var0.string;
    }
}
