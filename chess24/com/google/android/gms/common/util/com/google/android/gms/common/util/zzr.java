/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

public class zzr {
    public static int zza(byte[] arrby, int n, int n2, int n3) {
        int n4;
        int n5 = (n2 & -4) + n;
        while (n < n5) {
            n4 = (arrby[n] & 255 | (arrby[n + 1] & 255) << 8 | (arrby[n + 2] & 255) << 16 | arrby[n + 3] << 24) * -862048943;
            n3 ^= (n4 << 15 | n4 >>> 17) * 461845907;
            n3 = (n3 >>> 19 | n3 << 13) * 5 - 430675100;
            n += 4;
        }
        n4 = 0;
        n = 0;
        switch (n2 & 3) {
            default: {
                break;
            }
            case 3: {
                n = (arrby[n5 + 2] & 255) << 16;
            }
            case 2: {
                n4 = n | (arrby[n5 + 1] & 255) << 8;
            }
            case 1: {
                n = (arrby[n5] & 255 | n4) * -862048943;
                n3 ^= (n >>> 17 | n << 15) * 461845907;
            }
        }
        n = n3 ^ n2;
        n = (n ^ n >>> 16) * -2048144789;
        n = (n ^ n >>> 13) * -1028477387;
        return n ^ n >>> 16;
    }
}
