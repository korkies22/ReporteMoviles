/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class zza<E>
extends AbstractSet<E> {
    private final ArrayMap<E, E> zzaGJ;

    public zza() {
        this.zzaGJ = new ArrayMap();
    }

    public zza(int n) {
        this.zzaGJ = new ArrayMap(n);
    }

    public zza(Collection<E> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    @Override
    public boolean add(E e) {
        if (this.zzaGJ.containsKey(e)) {
            return false;
        }
        this.zzaGJ.put(e, e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection instanceof zza) {
            return this.zza((zza)collection);
        }
        return super.addAll(collection);
    }

    @Override
    public void clear() {
        this.zzaGJ.clear();
    }

    @Override
    public boolean contains(Object object) {
        return this.zzaGJ.containsKey(object);
    }

    @Override
    public Iterator<E> iterator() {
        return this.zzaGJ.keySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        if (!this.zzaGJ.containsKey(object)) {
            return false;
        }
        this.zzaGJ.remove(object);
        return true;
    }

    @Override
    public int size() {
        return this.zzaGJ.size();
    }

    public boolean zza(zza<? extends E> zza2) {
        int n = this.size();
        ((SimpleArrayMap)this.zzaGJ).putAll(zza2.zzaGJ);
        if (this.size() > n) {
            return true;
        }
        return false;
    }
}
