/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.util.zza;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzf {
    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        ArrayMap<K, V> arrayMap = new ArrayMap<K, V>(6);
        arrayMap.put(k, v);
        arrayMap.put(k2, v2);
        arrayMap.put(k3, v3);
        arrayMap.put(k4, v4);
        arrayMap.put(k5, v5);
        arrayMap.put(k6, v6);
        return Collections.unmodifiableMap(arrayMap);
    }

    public static <T> Set<T> zza(T t, T t2, T t3) {
        zza<T> zza2 = new zza<T>(3);
        zza2.add(t);
        zza2.add(t2);
        zza2.add(t3);
        return Collections.unmodifiableSet(zza2);
    }

    public static <T> Set<T> zza(T t, T t2, T t3, T t4) {
        zza<T> zza2 = new zza<T>(4);
        zza2.add(t);
        zza2.add(t2);
        zza2.add(t3);
        zza2.add(t4);
        return Collections.unmodifiableSet(zza2);
    }

    private static <K, V> void zza(K[] object, V[] arrV) {
        if (((K[])object).length != arrV.length) {
            int n = ((K[])object).length;
            int n2 = arrV.length;
            object = new StringBuilder(66);
            object.append("Key and values array lengths not equal: ");
            object.append(n);
            object.append(" != ");
            object.append(n2);
            throw new IllegalArgumentException(object.toString());
        }
    }

    public static <K, V> Map<K, V> zzb(K[] arrK, V[] arrV) {
        Map<K, V> map;
        zzf.zza(arrK, arrV);
        int n = arrK.length;
        switch (n) {
            default: {
                map = n <= 32 ? new ArrayMap(n) : new HashMap(n, 1.0f);
            }
            case 1: {
                return zzf.zze(arrK[0], arrV[0]);
            }
            case 0: {
                return zzf.zzyu();
            }
        }
        for (int i = 0; i < n; ++i) {
            map.put(arrK[i], arrV[i]);
        }
        return Collections.unmodifiableMap(map);
    }

    public static <T> List<T> zzc(T t, T t2) {
        ArrayList<T> arrayList = new ArrayList<T>(2);
        arrayList.add(t);
        arrayList.add(t2);
        return Collections.unmodifiableList(arrayList);
    }

    public static /* varargs */ <T> Set<T> zzc(T ... object) {
        switch (((T[])object).length) {
            default: {
                object = ((T[])object).length <= 32 ? new zza<T>(Arrays.asList(object)) : new HashSet<T>(Arrays.asList(object));
            }
            case 4: {
                return zzf.zza(object[0], object[1], object[2], object[3]);
            }
            case 3: {
                return zzf.zza(object[0], object[1], object[2]);
            }
            case 2: {
                return zzf.zzd(object[0], object[1]);
            }
            case 1: {
                return zzf.zzy(object[0]);
            }
            case 0: {
                return zzf.zzyt();
            }
        }
        return Collections.unmodifiableSet(object);
    }

    public static <T> Set<T> zzd(T t, T t2) {
        zza<T> zza2 = new zza<T>(2);
        zza2.add(t);
        zza2.add(t2);
        return Collections.unmodifiableSet(zza2);
    }

    public static <K, V> Map<K, V> zze(K k, V v) {
        return Collections.singletonMap(k, v);
    }

    public static <T> List<T> zzx(T t) {
        return Collections.singletonList(t);
    }

    public static <T> Set<T> zzy(T t) {
        return Collections.singleton(t);
    }

    public static <T> Set<T> zzyt() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> zzyu() {
        return Collections.emptyMap();
    }
}
