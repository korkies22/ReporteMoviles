/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.tagmanager.zzbi;
import com.google.android.gms.tagmanager.zzde;
import com.google.android.gms.tagmanager.zzl;

class zzm<K, V> {
    final zza<K, V> zzbCR = new zza<K, V>(this){

        @Override
        public int sizeOf(K k, V v) {
            return 1;
        }
    };

    public zzl<K, V> zza(int n, zza<K, V> zza2) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (this.zzyS() < 12) {
            return new zzde<K, V>(n, zza2);
        }
        return new zzbi<K, V>(n, zza2);
    }

    int zzyS() {
        return Build.VERSION.SDK_INT;
    }

    public static interface zza<K, V> {
        public int sizeOf(K var1, V var2);
    }

}
