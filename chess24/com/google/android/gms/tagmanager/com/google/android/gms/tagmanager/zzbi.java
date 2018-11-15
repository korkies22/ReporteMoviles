/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.util.LruCache
 */
package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.util.LruCache;
import com.google.android.gms.tagmanager.zzl;
import com.google.android.gms.tagmanager.zzm;

@TargetApi(value=12)
class zzbi<K, V>
implements zzl<K, V> {
    private LruCache<K, V> zzbED;

    zzbi(int n, final zzm.zza<K, V> zza2) {
        this.zzbED = new LruCache<K, V>(this, n){

            protected int sizeOf(K k, V v) {
                return zza2.sizeOf(k, v);
            }
        };
    }

    @Override
    public V get(K k) {
        return (V)this.zzbED.get(k);
    }

    @Override
    public void zzi(K k, V v) {
        this.zzbED.put(k, v);
    }

}
