/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Looper;
import android.util.Log;

public final class zzc {
    public static void zza(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void zzar(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    public static void zzdn(String string) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            String string2 = String.valueOf(Thread.currentThread());
            String string3 = String.valueOf(Looper.getMainLooper().getThread());
            StringBuilder stringBuilder = new StringBuilder(57 + String.valueOf(string2).length() + String.valueOf(string3).length());
            stringBuilder.append("checkMainThread: current thread ");
            stringBuilder.append(string2);
            stringBuilder.append(" IS NOT the main thread ");
            stringBuilder.append(string3);
            stringBuilder.append("!");
            Log.e((String)"Asserts", (String)stringBuilder.toString());
            throw new IllegalStateException(string);
        }
    }

    public static void zzdo(String string) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            String string2 = String.valueOf(Thread.currentThread());
            String string3 = String.valueOf(Looper.getMainLooper().getThread());
            StringBuilder stringBuilder = new StringBuilder(56 + String.valueOf(string2).length() + String.valueOf(string3).length());
            stringBuilder.append("checkNotMainThread: current thread ");
            stringBuilder.append(string2);
            stringBuilder.append(" IS the main thread ");
            stringBuilder.append(string3);
            stringBuilder.append("!");
            Log.e((String)"Asserts", (String)stringBuilder.toString());
            throw new IllegalStateException(string);
        }
    }

    public static void zzt(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null reference");
        }
    }
}
