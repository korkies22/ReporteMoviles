/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import java.util.Map;

class ArrayMap
extends MapCollections<K, V> {
    ArrayMap() {
    }

    @Override
    protected void colClear() {
        ArrayMap.this.clear();
    }

    @Override
    protected Object colGetEntry(int n, int n2) {
        return ArrayMap.this.mArray[(n << 1) + n2];
    }

    @Override
    protected Map<K, V> colGetMap() {
        return ArrayMap.this;
    }

    @Override
    protected int colGetSize() {
        return ArrayMap.this.mSize;
    }

    @Override
    protected int colIndexOfKey(Object object) {
        return ArrayMap.this.indexOfKey(object);
    }

    @Override
    protected int colIndexOfValue(Object object) {
        return ArrayMap.this.indexOfValue(object);
    }

    @Override
    protected void colPut(K k, V v) {
        ArrayMap.this.put(k, v);
    }

    @Override
    protected void colRemoveAt(int n) {
        ArrayMap.this.removeAt(n);
    }

    @Override
    protected V colSetValue(int n, V v) {
        return ArrayMap.this.setValueAt(n, v);
    }
}
