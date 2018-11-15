/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.HashMap;
import java.util.Map;

public final class ExpandedProductResultParser
extends ResultParser {
    private static String findAIvalue(int n, String string) {
        if (string.charAt(n) != '(') {
            return null;
        }
        string = string.substring(n + 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            if (c == ')') {
                return stringBuilder.toString();
            }
            if (c >= '0' && c <= '9') {
                stringBuilder.append(c);
                continue;
            }
            return null;
        }
        return stringBuilder.toString();
    }

    private static String findValue(int n, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        string = string.substring(n);
        for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            if (c == '(') {
                if (ExpandedProductResultParser.findAIvalue(n, string) != null) break;
                stringBuilder.append('(');
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public ExpandedProductParsedResult parse(Result var1_1) {
        if (var1_1.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        var18_2 = ExpandedProductResultParser.getMassagedText((Result)var1_1);
        var19_3 = new HashMap<String, String>();
        var17_4 = null;
        var6_15 = var13_14 = (var12_13 = (var14_12 = (var11_11 = (var1_1 = (var4_10 = (var5_9 = (var7_8 = (var8_7 = (var9_6 = (var10_5 = var17_4))))))))));
        var2_16 = 0;
        var16_17 = var11_11;
        var15_18 = var1_1;
        var11_11 = var17_4;
        while (var2_16 < var18_2.length()) {
            block100 : {
                block99 : {
                    block98 : {
                        block95 : {
                            block96 : {
                                block97 : {
                                    var17_4 = ExpandedProductResultParser.findAIvalue(var2_16, var18_2);
                                    if (var17_4 == null) {
                                        return null;
                                    }
                                    var1_1 = ExpandedProductResultParser.findValue(var2_16 += var17_4.length() + 2, var18_2);
                                    var3_19 = var2_16 + var1_1.length();
                                    var2_16 = var17_4.hashCode();
                                    if (var2_16 == 1570) break block95;
                                    if (var2_16 == 1572) break block96;
                                    if (var2_16 == 1574) break block97;
                                    switch (var2_16) {
                                        default: {
                                            switch (var2_16) {
                                                default: {
                                                    switch (var2_16) {
                                                        default: {
                                                            switch (var2_16) {
                                                                default: {
                                                                    switch (var2_16) {
                                                                        default: {
                                                                            switch (var2_16) {
                                                                                default: {
                                                                                    ** GOTO lbl-1000
                                                                                }
                                                                                case 1575750: {
                                                                                    if (var17_4.equals("3933")) {
                                                                                        var2_16 = 34;
                                                                                        ** break;
                                                                                    }
                                                                                    ** GOTO lbl-1000
                                                                                }
                                                                                case 1575749: {
                                                                                    if (var17_4.equals("3932")) {
                                                                                        var2_16 = 33;
                                                                                        ** break;
                                                                                    }
                                                                                    ** GOTO lbl-1000
                                                                                }
                                                                                case 1575748: {
                                                                                    if (var17_4.equals("3931")) {
                                                                                        var2_16 = 32;
                                                                                        ** break;
                                                                                    }
                                                                                    ** GOTO lbl-1000
                                                                                }
                                                                                case 1575747: 
                                                                            }
                                                                            if (var17_4.equals("3930")) {
                                                                                var2_16 = 31;
                                                                                ** break;
                                                                            }
                                                                            ** GOTO lbl-1000
                                                                        }
                                                                        case 1575719: {
                                                                            if (var17_4.equals("3923")) {
                                                                                var2_16 = 30;
                                                                                ** break;
                                                                            }
                                                                            ** GOTO lbl-1000
                                                                        }
                                                                        case 1575718: {
                                                                            if (var17_4.equals("3922")) {
                                                                                var2_16 = 29;
                                                                                ** break;
                                                                            }
                                                                            ** GOTO lbl-1000
                                                                        }
                                                                        case 1575717: {
                                                                            if (var17_4.equals("3921")) {
                                                                                var2_16 = 28;
                                                                                ** break;
                                                                            }
                                                                            ** GOTO lbl-1000
                                                                        }
                                                                        case 1575716: 
                                                                    }
                                                                    if (var17_4.equals("3920")) {
                                                                        var2_16 = 27;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568936: {
                                                                    if (var17_4.equals("3209")) {
                                                                        var2_16 = 26;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568935: {
                                                                    if (var17_4.equals("3208")) {
                                                                        var2_16 = 25;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568934: {
                                                                    if (var17_4.equals("3207")) {
                                                                        var2_16 = 24;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568933: {
                                                                    if (var17_4.equals("3206")) {
                                                                        var2_16 = 23;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568932: {
                                                                    if (var17_4.equals("3205")) {
                                                                        var2_16 = 22;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568931: {
                                                                    if (var17_4.equals("3204")) {
                                                                        var2_16 = 21;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568930: {
                                                                    if (var17_4.equals("3203")) {
                                                                        var2_16 = 20;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568929: {
                                                                    if (var17_4.equals("3202")) {
                                                                        var2_16 = 19;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568928: {
                                                                    if (var17_4.equals("3201")) {
                                                                        var2_16 = 18;
                                                                        ** break;
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                case 1568927: 
                                                            }
                                                            if (var17_4.equals("3200")) {
                                                                var2_16 = 17;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567975: {
                                                            if (var17_4.equals("3109")) {
                                                                var2_16 = 16;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567974: {
                                                            if (var17_4.equals("3108")) {
                                                                var2_16 = 15;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567973: {
                                                            if (var17_4.equals("3107")) {
                                                                var2_16 = 14;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567972: {
                                                            if (var17_4.equals("3106")) {
                                                                var2_16 = 13;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567971: {
                                                            if (var17_4.equals("3105")) {
                                                                var2_16 = 12;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567970: {
                                                            if (var17_4.equals("3104")) {
                                                                var2_16 = 11;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567969: {
                                                            if (var17_4.equals("3103")) {
                                                                var2_16 = 10;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567968: {
                                                            if (var17_4.equals("3102")) {
                                                                var2_16 = 9;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567967: {
                                                            if (var17_4.equals("3101")) {
                                                                var2_16 = 8;
                                                                ** break;
                                                            }
                                                            ** GOTO lbl-1000
                                                        }
                                                        case 1567966: 
                                                    }
                                                    if (var17_4.equals("3100")) {
                                                        var2_16 = 7;
                                                        ** break;
                                                    }
                                                    ** GOTO lbl-1000
                                                }
                                                case 1568: {
                                                    if (var17_4.equals("11")) {
                                                        var2_16 = 3;
                                                        ** break;
                                                    }
                                                    ** GOTO lbl-1000
                                                }
                                                case 1567: 
                                            }
                                            if (var17_4.equals("10")) {
                                                var2_16 = 2;
                                                ** break;
                                            }
                                            ** GOTO lbl-1000
                                        }
                                        case 1537: {
                                            if (var17_4.equals("01")) {
                                                var2_16 = 1;
                                                ** break;
                                            }
                                            ** GOTO lbl-1000
                                        }
                                        case 1536: {
                                            if (var17_4.equals("00")) {
                                                var2_16 = 0;
                                                ** break;
                                            }
                                            ** GOTO lbl-1000
lbl194: // 32 sources:
                                            break;
                                        }
                                    }
                                    break block98;
                                }
                                if (!var17_4.equals("17")) ** GOTO lbl-1000
                                var2_16 = 6;
                                break block98;
                            }
                            if (!var17_4.equals("15")) ** GOTO lbl-1000
                            var2_16 = 5;
                            break block98;
                        }
                        if (var17_4.equals("13")) {
                            var2_16 = 4;
                        } else lbl-1000: // 36 sources:
                        {
                            var2_16 = -1;
                        }
                    }
                    switch (var2_16) {
                        default: {
                            var19_3.put(var17_4, (String)var1_1);
                            break block99;
                        }
                        case 31: 
                        case 32: 
                        case 33: 
                        case 34: {
                            if (var1_1.length() < 4) {
                                return null;
                            }
                            var12_13 = var1_1.substring(3);
                            var6_15 = var1_1.substring(0, 3);
                            var13_14 = var17_4.substring(3);
                            break block99;
                        }
                        case 27: 
                        case 28: 
                        case 29: 
                        case 30: {
                            var13_14 = var17_4.substring(3);
                            var12_13 = var1_1;
                            break block99;
                        }
                        case 17: 
                        case 18: 
                        case 19: 
                        case 20: 
                        case 21: 
                        case 22: 
                        case 23: 
                        case 24: 
                        case 25: 
                        case 26: {
                            var15_18 = "LB";
                            var14_12 = var17_4.substring(3);
                            ** GOTO lbl232
                        }
                        case 7: 
                        case 8: 
                        case 9: 
                        case 10: 
                        case 11: 
                        case 12: 
                        case 13: 
                        case 14: 
                        case 15: 
                        case 16: {
                            var15_18 = "KG";
                            var14_12 = var17_4.substring(3);
lbl232: // 2 sources:
                            var16_17 = var15_18;
                            break block100;
                        }
                        case 6: {
                            var4_10 = var1_1;
                            break block99;
                        }
                        case 5: {
                            var5_9 = var1_1;
                            break block99;
                        }
                        case 4: {
                            var7_8 = var1_1;
                            break block99;
                        }
                        case 3: {
                            var8_7 = var1_1;
                            break block99;
                        }
                        case 2: {
                            var9_6 = var1_1;
                            break block99;
                        }
                        case 1: {
                            var11_11 = var1_1;
                            break block99;
                        }
                        case 0: 
                    }
                    var10_5 = var1_1;
                }
                var1_1 = var15_18;
            }
            var2_16 = var3_19;
            var15_18 = var1_1;
        }
        return new ExpandedProductParsedResult(var18_2, (String)var11_11, (String)var10_5, (String)var9_6, (String)var8_7, (String)var7_8, (String)var5_9, (String)var4_10, (String)var15_18, (String)var16_17, (String)var14_12, (String)var12_13, (String)var13_14, (String)var6_15, var19_3);
    }
}
