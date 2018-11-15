/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.LruObjectCache;
import java.util.LinkedHashMap;
import java.util.Map;

private static class LruObjectCache.LimitedLinkedHashMap<K, V>
extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -4566528080395573236L;
    private final int capacity;

    public LruObjectCache.LimitedLinkedHashMap(int n) {
        super(n, 0.75f, true);
        this.capacity = n;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        if (this.size() > this.capacity) {
            return true;
        }
        return false;
    }
}
