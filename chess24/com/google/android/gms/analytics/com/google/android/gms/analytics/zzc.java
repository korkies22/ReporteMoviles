/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.internal.zzsw;

public final class zzc {
    public static String zzal(int n) {
        return zzc.zzc("&cd", n);
    }

    public static String zzam(int n) {
        return zzc.zzc("cd", n);
    }

    public static String zzan(int n) {
        return zzc.zzc("&cm", n);
    }

    public static String zzao(int n) {
        return zzc.zzc("cm", n);
    }

    public static String zzap(int n) {
        return zzc.zzc("&pr", n);
    }

    public static String zzaq(int n) {
        return zzc.zzc("pr", n);
    }

    public static String zzar(int n) {
        return zzc.zzc("&promo", n);
    }

    public static String zzas(int n) {
        return zzc.zzc("promo", n);
    }

    public static String zzat(int n) {
        return zzc.zzc("pi", n);
    }

    public static String zzau(int n) {
        return zzc.zzc("&il", n);
    }

    public static String zzav(int n) {
        return zzc.zzc("il", n);
    }

    public static String zzaw(int n) {
        return zzc.zzc("cd", n);
    }

    public static String zzax(int n) {
        return zzc.zzc("cm", n);
    }

    private static String zzc(String string, int n) {
        if (n < 1) {
            zzsw.zzf("index out of range for prefix", string);
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(11 + String.valueOf(string).length());
        stringBuilder.append(string);
        stringBuilder.append(n);
        return stringBuilder.toString();
    }
}
