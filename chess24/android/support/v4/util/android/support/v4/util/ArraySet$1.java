/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import java.util.Map;

class ArraySet
extends MapCollections<E, E> {
    ArraySet() {
    }

    @Override
    protected void colClear() {
        ArraySet.this.clear();
    }

    @Override
    protected Object colGetEntry(int n, int n2) {
        return ArraySet.this.mArray[n];
    }

    @Override
    protected Map<E, E> colGetMap() {
        throw new UnsupportedOperationException("not a map");
    }

    @Override
    protected int colGetSize() {
        return ArraySet.this.mSize;
    }

    @Override
    protected int colIndexOfKey(Object object) {
        return ArraySet.this.indexOf(object);
    }

    @Override
    protected int colIndexOfValue(Object object) {
        return ArraySet.this.indexOf(object);
    }

    @Override
    protected void colPut(E e, E e2) {
        ArraySet.this.add(e);
    }

    @Override
    protected void colRemoveAt(int n) {
        ArraySet.this.removeAt(n);
    }

    @Override
    protected E colSetValue(int n, E e) {
        throw new UnsupportedOperationException("not a map");
    }
}
