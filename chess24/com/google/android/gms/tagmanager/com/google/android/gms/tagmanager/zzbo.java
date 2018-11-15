/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzbp;
import com.google.android.gms.tagmanager.zzz;

public final class zzbo {
    static zzbp zzbEL = new zzz();
    static int zzbEM;

    public static void e(String string) {
        zzbEL.e(string);
    }

    public static int getLogLevel() {
        return zzbEM;
    }

    public static void setLogLevel(int n) {
        zzbEM = n;
        zzbEL.setLogLevel(n);
    }

    public static void v(String string) {
        zzbEL.v(string);
    }

    public static void zzb(String string, Throwable throwable) {
        zzbEL.zzb(string, throwable);
    }

    public static void zzbc(String string) {
        zzbEL.zzbc(string);
    }

    public static void zzbd(String string) {
        zzbEL.zzbd(string);
    }

    public static void zzbe(String string) {
        zzbEL.zzbe(string);
    }

    public static void zzc(String string, Throwable throwable) {
        zzbEL.zzc(string, throwable);
    }
}
