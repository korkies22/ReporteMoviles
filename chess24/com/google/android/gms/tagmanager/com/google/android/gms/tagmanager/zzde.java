/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzl;
import com.google.android.gms.tagmanager.zzm;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class zzde<K, V>
implements zzl<K, V> {
    private final Map<K, V> zzbGp = new HashMap();
    private final int zzbGq;
    private final zzm.zza<K, V> zzbGr;
    private int zzbGs;

    zzde(int n, zzm.zza<K, V> zza2) {
        this.zzbGq = n;
        this.zzbGr = zza2;
    }

    @Override
    public V get(K object) {
        synchronized (this) {
            object = this.zzbGp.get(object);
            return (V)object;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void zzi(K k, V v) {
        synchronized (this) {
            if (k == null || v == null) throw new NullPointerException("key == null || value == null");
            this.zzbGs += this.zzbGr.sizeOf(k, v);
            if (this.zzbGs > this.zzbGq) {
                Iterator<Map.Entry<K, V>> iterator = this.zzbGp.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    this.zzbGs -= this.zzbGr.sizeOf(entry.getKey(), entry.getValue());
                    iterator.remove();
                    if (this.zzbGs > this.zzbGq) continue;
                }
            }
            this.zzbGp.put(k, v);
            return;
        }
    }
}
