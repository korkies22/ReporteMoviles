/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.LruCache
 */
package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.zzm;

class zzbi
extends LruCache<K, V> {
    final /* synthetic */ zzm.zza zzbEE;

    zzbi(com.google.android.gms.tagmanager.zzbi zzbi2, int n, zzm.zza zza2) {
        this.zzbEE = zza2;
        super(n);
    }

    protected int sizeOf(K k, V v) {
        return this.zzbEE.sizeOf(k, v);
    }
}
