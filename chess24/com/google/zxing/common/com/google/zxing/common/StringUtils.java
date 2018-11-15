/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING;
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
        boolean bl = SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING);
        ASSUME_SHIFT_JIS = bl;
    }

    private StringUtils() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String guessEncoding(byte[] var0, Map<DecodeHintType, ?> var1_1) {
        var29_2 = var0;
        if (var1_1 != null && var1_1.containsKey((Object)DecodeHintType.CHARACTER_SET)) {
            return var1_1.get((Object)DecodeHintType.CHARACTER_SET).toString();
        }
        var18_3 = 0;
        var27_4 = var29_2.length;
        var12_5 = var29_2.length > 3 && var29_2[0] == -17 && var29_2[1] == -69 && var29_2[2] == -65;
        var14_8 = var5_7 = (var24_6 = 1);
        var15_19 = var11_18 = (var10_17 = (var4_16 = (var9_15 = (var7_14 = (var6_13 = (var3_12 = (var8_11 = (var2_10 = (var16_9 = 0)))))))));
        var17_20 = var11_18;
        var22_21 = var9_15;
        var23_22 = var7_14;
        var25_23 = var6_13;
        var13_24 = var3_12;
        var7_14 = var14_8;
        var6_13 = var18_3;
        var3_12 = var16_9;
        for (var14_8 = var2_10; var14_8 < var27_4 && (var24_6 != 0 || var5_7 != 0 || var7_14 != 0); ++var14_8) {
            block33 : {
                block37 : {
                    block36 : {
                        block35 : {
                            block34 : {
                                block30 : {
                                    block32 : {
                                        block31 : {
                                            block28 : {
                                                block29 : {
                                                    var28_29 = var0[var14_8] & 255;
                                                    var16_9 = var7_14;
                                                    var2_10 = var8_11;
                                                    var18_3 = var25_23;
                                                    var19_25 = var23_22;
                                                    var20_26 = var22_21;
                                                    if (var7_14 == 0) break block28;
                                                    if (var8_11 <= 0) break block29;
                                                    var2_10 = var8_11;
                                                    if ((var28_29 & 128) == 0) ** GOTO lbl-1000
                                                    var2_10 = var8_11 - 1;
                                                    var16_9 = var7_14;
                                                    var18_3 = var25_23;
                                                    var19_25 = var23_22;
                                                    var20_26 = var22_21;
                                                    break block28;
                                                }
                                                var16_9 = var7_14;
                                                var2_10 = var8_11;
                                                var18_3 = var25_23;
                                                var19_25 = var23_22;
                                                var20_26 = var22_21;
                                                if ((var28_29 & 128) == 0) break block28;
                                                var2_10 = var8_11;
                                                if ((var28_29 & 64) == 0) ** GOTO lbl-1000
                                                var2_10 = var8_11 + 1;
                                                if ((var28_29 & 32) == 0) {
                                                    var18_3 = var25_23 + 1;
                                                    var16_9 = var7_14;
                                                    var19_25 = var23_22;
                                                    var20_26 = var22_21;
                                                } else {
                                                    ++var2_10;
                                                    if ((var28_29 & 16) == 0) {
                                                        var19_25 = var23_22 + 1;
                                                        var16_9 = var7_14;
                                                        var18_3 = var25_23;
                                                        var20_26 = var22_21;
                                                    } else {
                                                        var2_10 = var8_11 = var2_10 + 1;
                                                        if ((var28_29 & 8) == 0) {
                                                            var20_26 = var22_21 + 1;
                                                            var16_9 = var7_14;
                                                            var2_10 = var8_11;
                                                            var18_3 = var25_23;
                                                            var19_25 = var23_22;
                                                        } else lbl-1000: // 3 sources:
                                                        {
                                                            var16_9 = 0;
                                                            var20_26 = var22_21;
                                                            var19_25 = var23_22;
                                                            var18_3 = var25_23;
                                                        }
                                                    }
                                                }
                                            }
                                            var8_11 = var24_6;
                                            var21_27 = var10_17;
                                            if (var24_6 == 0) break block30;
                                            if (var28_29 <= 127 || var28_29 >= 160) break block31;
                                            var8_11 = 0;
                                            var21_27 = var10_17;
                                            break block30;
                                        }
                                        var8_11 = var24_6;
                                        var21_27 = var10_17;
                                        if (var28_29 <= 159) break block30;
                                        if (var28_29 < 192 || var28_29 == 215) break block32;
                                        var8_11 = var24_6;
                                        var21_27 = var10_17;
                                        if (var28_29 != 247) break block30;
                                    }
                                    var21_27 = var10_17 + 1;
                                    var8_11 = var24_6;
                                }
                                var7_14 = var3_12;
                                var22_21 = var6_13;
                                var23_22 = var5_7;
                                var10_17 = var13_24;
                                var26_28 = var4_16;
                                var9_15 = var17_20;
                                var11_18 = var15_19;
                                if (var5_7 == 0) break block33;
                                if (var13_24 <= 0) break block34;
                                if (var28_29 < 64 || var28_29 == 127 || var28_29 > 252) break block35;
                                var10_17 = var13_24 - 1;
                                var7_14 = var3_12;
                                var22_21 = var6_13;
                                var23_22 = var5_7;
                                var26_28 = var4_16;
                                var9_15 = var17_20;
                                var11_18 = var15_19;
                                break block33;
                            }
                            if (var28_29 != 128 && var28_29 != 160 && var28_29 <= 239) break block36;
                        }
                        var23_22 = 0;
                        var7_14 = var3_12;
                        var22_21 = var6_13;
                        var10_17 = var13_24;
                        var26_28 = var4_16;
                        var9_15 = var17_20;
                        var11_18 = var15_19;
                        break block33;
                    }
                    if (var28_29 <= 160 || var28_29 >= 224) break block37;
                    var7_14 = var3_12 + 1;
                    var3_12 = var17_20 + 1;
                    if (var3_12 > var4_16) {
                        var3_12 = var4_16 = var3_12;
                    }
                    ** GOTO lbl140
                }
                if (var28_29 > 127) {
                    var10_17 = var13_24 + 1;
                    var11_18 = var15_19 + 1;
                    if (var11_18 > var6_13) {
                        var6_13 = var11_18;
                    }
                    var9_15 = 0;
                    var7_14 = var3_12;
                    var22_21 = var6_13;
                    var23_22 = var5_7;
                    var26_28 = var4_16;
                } else {
                    var9_15 = 0;
                    var7_14 = var3_12;
                    var3_12 = var9_15;
lbl140: // 2 sources:
                    var11_18 = 0;
                    var22_21 = var6_13;
                    var23_22 = var5_7;
                    var10_17 = var13_24;
                    var26_28 = var4_16;
                    var9_15 = var3_12;
                }
            }
            var3_12 = var7_14;
            var6_13 = var22_21;
            var24_6 = var8_11;
            var5_7 = var23_22;
            var7_14 = var16_9;
            var8_11 = var2_10;
            var13_24 = var10_17;
            var25_23 = var18_3;
            var23_22 = var19_25;
            var22_21 = var20_26;
            var4_16 = var26_28;
            var10_17 = var21_27;
            var17_20 = var9_15;
            var15_19 = var11_18;
        }
        var2_10 = var7_14;
        if (var7_14 != 0) {
            var2_10 = var7_14;
            if (var8_11 > 0) {
                var2_10 = 0;
            }
        }
        if (var5_7 != 0 && var13_24 > 0) {
            var5_7 = 0;
        }
        if (var2_10 != 0) {
            if (var12_5 != false) return "UTF8";
            if (var25_23 + var23_22 + var22_21 > 0) {
                return "UTF8";
            }
        }
        if (var5_7 != 0) {
            if (StringUtils.ASSUME_SHIFT_JIS != false) return "SJIS";
            if (var4_16 >= 3) return "SJIS";
            if (var6_13 >= 3) {
                return "SJIS";
            }
        }
        if (var24_6 != 0 && var5_7 != 0) {
            if (var4_16 == 2) {
                if (var3_12 == 2) return "SJIS";
            }
            if (var10_17 * 10 < var27_4) return "ISO8859_1";
            return "SJIS";
        }
        if (var24_6 != 0) {
            return "ISO8859_1";
        }
        if (var5_7 != 0) {
            return "SJIS";
        }
        if (var2_10 == 0) return StringUtils.PLATFORM_DEFAULT_ENCODING;
        return "UTF8";
    }
}
