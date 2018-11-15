/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

public final class zzac {
    public static int zza(int n, Object object) {
        if (n == 0) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return n;
    }

    public static long zza(long l, Object object) {
        if (l == 0L) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return l;
    }

    public static void zza(Handler handler) {
        if (Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException("Must be called on the handler thread");
        }
    }

    public static void zza(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static /* varargs */ void zza(boolean bl, String string, Object ... arrobject) {
        if (!bl) {
            throw new IllegalStateException(String.format(string, arrobject));
        }
    }

    public static void zzar(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    public static void zzas(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T zzb(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    public static void zzb(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static /* varargs */ void zzb(boolean bl, String string, Object ... arrobject) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, arrobject));
        }
    }

    public static int zzcR(int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        }
        return n;
    }

    public static void zzdn(String string) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(string);
        }
    }

    public static void zzdo(String string) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(string);
        }
    }

    public static String zzdv(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        return string;
    }

    public static String zzh(String string, Object object) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string;
    }

    public static <T> T zzw(T t) {
        if (t == null) {
            throw new NullPointerException("null reference");
        }
        return t;
    }

    public static void zzxx() {
        zzac.zzdo("Must not be called on the main application thread");
    }
}
