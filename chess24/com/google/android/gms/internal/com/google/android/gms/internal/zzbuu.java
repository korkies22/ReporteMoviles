/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbut;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzbuu {
    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static void zza(String var0, Object var1_1, StringBuffer var2_2, StringBuffer var3_3) throws IllegalAccessException, InvocationTargetException {
        block16 : {
            block18 : {
                block19 : {
                    block17 : {
                        if (var1_1 == null) {
                            return;
                        }
                        if (!(var1_1 instanceof zzbut)) break block17;
                        var7_4 = var2_2.length();
                        if (var0 != null) {
                            var3_3.append(var2_2);
                            var3_3.append(zzbuu.zzkd(var0));
                            var3_3.append(" <\n");
                            var2_2.append("  ");
                        }
                        var10_5 = var1_1.getClass();
                        var9_6 = var10_5.getFields();
                        var8_8 = ((Field[])var9_6).length;
                        for (var4_9 = 0; var4_9 < var8_8; ++var4_9) {
                            var13_14 = var9_6[var4_9];
                            var5_10 = var13_14.getModifiers();
                            var11_12 = var13_14.getName();
                            if ("cachedSize".equals(var11_12) || (var5_10 & 1) != 1 || (var5_10 & 8) == 8 || var11_12.startsWith("_") || var11_12.endsWith("_")) continue;
                            var12_13 = var13_14.getType();
                            var13_14 = var13_14.get(var1_1);
                            if (var12_13.isArray() && var12_13.getComponentType() != Byte.TYPE) {
                                var5_10 = var13_14 == null ? 0 : Array.getLength(var13_14);
                                for (var6_11 = 0; var6_11 < var5_10; ++var6_11) {
                                    zzbuu.zza((String)var11_12, Array.get(var13_14, var6_11), var2_2, var3_3);
                                }
                                continue;
                            }
                            zzbuu.zza((String)var11_12, var13_14, var2_2, var3_3);
                        }
                        var11_12 = var10_5.getMethods();
                        var5_10 = ((Method[])var11_12).length;
                        block5 : for (var4_9 = 0; var4_9 < var5_10; ++var4_9) {
                            var9_6 = var11_12[var4_9].getName();
                            if (var9_6.startsWith("set")) {
                                var12_13 = var9_6.substring(3);
                                var9_6 = String.valueOf(var12_13);
                                var9_6 = var9_6.length() != 0 ? "has".concat((String)var9_6) : new String("has");
                                var9_6 = var10_5.getMethod((String)var9_6, new Class[0]);
                                if (!((Boolean)var9_6.invoke(var1_1, new Object[0])).booleanValue()) break block16;
                                var9_6 = String.valueOf(var12_13);
                                var9_6 = var9_6.length() != 0 ? "get".concat((String)var9_6) : new String("get");
                                var9_6 = var10_5.getMethod((String)var9_6, new Class[0]);
                                zzbuu.zza((String)var12_13, var9_6.invoke(var1_1, new Object[0]), var2_2, var3_3);
                            }
lbl44: // 4 sources:
                        }
                        if (var0 == null) break block18;
                        var2_2.setLength(var7_4);
                        var3_3.append(var2_2);
                        var0 = ">\n";
                        break block19;
                    }
                    var0 = zzbuu.zzkd(var0);
                    var3_3.append(var2_2);
                    var3_3.append(var0);
                    var3_3.append(": ");
                    if (var1_1 instanceof String) {
                        var0 = zzbuu.zzcE((String)var1_1);
                        var3_3.append("\"");
                        var3_3.append(var0);
                        var3_3.append("\"");
                    } else if (var1_1 instanceof byte[]) {
                        zzbuu.zza((byte[])var1_1, var3_3);
                    } else {
                        var3_3.append(var1_1);
                    }
                    var0 = "\n";
                }
                var3_3.append(var0);
            }
            return;
            catch (NoSuchMethodException var9_7) {}
            {
            }
        }
        ** while (true)
    }

    private static void zza(byte[] arrby, StringBuffer stringBuffer) {
        if (arrby == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            if (n != 92 && n != 34) {
                if (n < 32 || n >= 127) {
                    stringBuffer.append(String.format("\\%03o", n));
                    continue;
                }
            } else {
                stringBuffer.append('\\');
            }
            stringBuffer.append((char)n);
        }
        stringBuffer.append('\"');
    }

    private static String zzcE(String string) {
        String string2 = string;
        if (!string.startsWith("http")) {
            string2 = string;
            if (string.length() > 200) {
                string2 = String.valueOf(string.substring(0, 200)).concat("[...]");
            }
        }
        return zzbuu.zzdC(string2);
    }

    private static String zzdC(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c >= ' ' && c <= '~' && c != '\"' && c != '\'') {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(String.format("\\u%04x", c));
        }
        return stringBuilder.toString();
    }

    public static <T extends zzbut> String zzg(T t) {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zzbuu.zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        }
        catch (InvocationTargetException invocationTargetException) {
            String string = String.valueOf(invocationTargetException.getMessage());
            if (string.length() != 0) {
                return "Error printing proto: ".concat(string);
            }
            return new String("Error printing proto: ");
        }
        catch (IllegalAccessException illegalAccessException) {
            String string = String.valueOf(illegalAccessException.getMessage());
            if (string.length() != 0) {
                return "Error printing proto: ".concat(string);
            }
            return new String("Error printing proto: ");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzkd(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        do {
            char c;
            block6 : {
                char c2;
                block5 : {
                    if (n >= string.length()) {
                        return stringBuffer.toString();
                    }
                    c2 = string.charAt(n);
                    if (n == 0) break block5;
                    c = c2;
                    if (!Character.isUpperCase(c2)) break block6;
                    stringBuffer.append('_');
                }
                c = Character.toLowerCase(c2);
            }
            stringBuffer.append(c);
            ++n;
        } while (true);
    }
}
