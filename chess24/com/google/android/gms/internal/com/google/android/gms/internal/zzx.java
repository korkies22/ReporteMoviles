/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.apache.http.impl.cookie.DateParseException
 *  org.apache.http.impl.cookie.DateUtils
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzi;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class zzx {
    public static String zza(Map<String, String> map) {
        return zzx.zza(map, "ISO-8859-1");
    }

    public static String zza(Map<String, String> arrstring, String string) {
        if ((arrstring = arrstring.get("Content-Type")) != null) {
            arrstring = arrstring.split(";");
            for (int i = 1; i < arrstring.length; ++i) {
                String[] arrstring2 = arrstring[i].trim().split("=");
                if (arrstring2.length != 2 || !arrstring2[0].equals("charset")) continue;
                return arrstring2[1];
            }
        }
        return string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static zzb.zza zzb(zzi var0) {
        block13 : {
            var13_1 = System.currentTimeMillis();
            var15_2 = var0.zzy;
            var16_3 = var15_2.get("Date");
            var7_10 = var16_3 != null ? zzx.zzg(var16_3) : 0L;
            var16_4 = var15_2.get("Cache-Control");
            var2_11 = 0;
            if (var16_4 != null) break block13;
            var2_11 = var1_12 = 0;
            var5_13 = 0L;
            var3_14 = 0L;
            ** GOTO lbl45
        }
        var16_5 = var16_4.split(",");
        var1_12 = 0;
        var5_13 = 0L;
        var3_14 = 0L;
        do {
            block16 : {
                block14 : {
                    block15 : {
                        if (var2_11 >= var16_5.length) break block14;
                        var17_15 = var16_5[var2_11].trim();
                        if (var17_15.equals("no-cache") != false) return null;
                        if (var17_15.equals("no-store")) {
                            return null;
                        }
                        if (var17_15.startsWith("max-age=")) {
                            var9_17 = Long.parseLong(var17_15.substring(8));
                            var11_18 = var3_14;
                        }
                        if (var17_15.startsWith("stale-while-revalidate=")) {
                            var11_18 = Long.parseLong(var17_15.substring(23));
                            var9_17 = var5_13;
                        }
                        if (var17_15.equals("must-revalidate")) break block15;
                        var9_17 = var5_13;
                        var11_18 = var3_14;
                        if (!var17_15.equals("proxy-revalidate")) break block16;
                    }
                    var1_12 = 1;
                    var11_18 = var3_14;
                    var9_17 = var5_13;
                    break block16;
                }
                var2_11 = 1;
lbl45: // 2 sources:
                var16_7 = var15_2.get("Expires");
                var11_18 = var16_7 != null ? zzx.zzg(var16_7) : 0L;
                var16_8 = var15_2.get("Last-Modified");
                var9_17 = var16_8 != null ? zzx.zzg(var16_8) : 0L;
                var16_9 = var15_2.get("ETag");
                if (var2_11 != 0) {
                    var5_13 = var13_1 + var5_13 * 1000L;
                    var3_14 = var1_12 != 0 ? var5_13 : var5_13 + var3_14 * 1000L;
                } else if (var7_10 > 0L && var11_18 >= var7_10) {
                    var3_14 = var5_13 = var13_1 + (var11_18 - var7_10);
                } else {
                    var5_13 = var3_14 = 0L;
                }
                var17_15 = new zzb.zza();
                var17_15.data = var0.data;
                var17_15.zza = var16_9;
                var17_15.zze = var5_13;
                var17_15.zzd = var3_14;
                var17_15.zzb = var7_10;
                var17_15.zzc = var9_17;
                var17_15.zzf = var15_2;
                return var17_15;
                catch (Exception var17_16) {
                    var9_17 = var5_13;
                    var11_18 = var3_14;
                }
            }
            ++var2_11;
            var5_13 = var9_17;
            var3_14 = var11_18;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long zzg(String string) {
        try {
            return DateUtils.parseDate((String)string).getTime();
        }
        catch (DateParseException dateParseException) {
            return 0L;
        }
    }
}
