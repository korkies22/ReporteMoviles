/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.zzd;
import java.lang.reflect.Field;

public final class zze<T>
extends zzd.zza {
    private final T mWrappedObject;

    private zze(T t) {
        this.mWrappedObject = t;
    }

    public static <T> zzd zzA(T t) {
        return new zze<T>(t);
    }

    public static <T> T zzE(zzd object) {
        int n;
        if (object instanceof zze) {
            return ((zze)object).mWrappedObject;
        }
        IBinder iBinder = object.asBinder();
        Field[] arrfield = iBinder.getClass().getDeclaredFields();
        int n2 = arrfield.length;
        object = null;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            Field field = arrfield[n];
            int n4 = n3;
            if (!field.isSynthetic()) {
                n4 = n3 + 1;
                object = field;
            }
            n3 = n4;
        }
        if (n3 == 1) {
            if (!object.isAccessible()) {
                object.setAccessible(true);
                try {
                    object = object.get((Object)iBinder);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", illegalAccessException);
                }
                catch (NullPointerException nullPointerException) {
                    throw new IllegalArgumentException("Binder object is null.", nullPointerException);
                }
                return (T)object;
            }
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
        n = arrfield.length;
        object = new StringBuilder(64);
        object.append("Unexpected number of IObjectWrapper declared fields: ");
        object.append(n);
        throw new IllegalArgumentException(object.toString());
    }
}
