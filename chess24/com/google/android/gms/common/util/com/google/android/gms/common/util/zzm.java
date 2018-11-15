/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

public final class zzm {
    /*
     * Enabled aggressive block sorting
     */
    public static String zza(byte[] arrby, int n, int n2, boolean bl) {
        int n3;
        if (arrby == null) return null;
        if (arrby.length == 0) return null;
        if (n < 0) return null;
        if (n2 <= 0) return null;
        if (n + n2 > arrby.length) {
            return null;
        }
        int n4 = 57;
        if (bl) {
            n4 = 75;
        }
        StringBuilder stringBuilder = new StringBuilder(n4 * ((n2 + 16 - 1) / 16));
        n4 = n2;
        int n5 = n3 = 0;
        while (n4 > 0) {
            int n6;
            int n7;
            block18 : {
                block17 : {
                    if (n3 == 0) {
                        String string;
                        Object[] arrobject;
                        if (n2 < 65536) {
                            string = "%04X:";
                            arrobject = new Object[]{n};
                        } else {
                            string = "%08X:";
                            arrobject = new Object[]{n};
                        }
                        stringBuilder.append(String.format(string, arrobject));
                        n7 = n;
                    } else {
                        n7 = n5;
                        if (n3 == 8) {
                            stringBuilder.append(" -");
                            n7 = n5;
                        }
                    }
                    stringBuilder.append(String.format(" %02X", arrby[n] & 255));
                    n6 = n4 - 1;
                    if (bl && (++n3 == 16 || n6 == 0)) {
                        n5 = 16 - n3;
                        if (n5 > 0) {
                            for (n4 = 0; n4 < n5; ++n4) {
                                stringBuilder.append("   ");
                            }
                        }
                        if (n5 >= 8) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append("  ");
                        for (n4 = 0; n4 < n3; ++n4) {
                            char c = (char)arrby[n7 + n4];
                            if (c < ' ' || c > '~') {
                                c = '.';
                            }
                            stringBuilder.append(c);
                        }
                    }
                    if (n3 == 16) break block17;
                    n4 = n3;
                    if (n6 != 0) break block18;
                }
                stringBuilder.append('\n');
                n4 = 0;
            }
            ++n;
            n3 = n4;
            n5 = n7;
            n4 = n6;
        }
        return stringBuilder.toString();
    }
}
