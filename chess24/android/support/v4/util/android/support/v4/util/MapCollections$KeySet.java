/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class MapCollections.KeySet
implements Set<K> {
    MapCollections.KeySet() {
    }

    @Override
    public boolean add(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends K> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        MapCollections.this.colClear();
    }

    @Override
    public boolean contains(Object object) {
        if (MapCollections.this.colIndexOfKey(object) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection);
    }

    @Override
    public boolean equals(Object object) {
        return MapCollections.equalsSetHelper(this, object);
    }

    @Override
    public int hashCode() {
        int n = 0;
        for (int i = MapCollections.this.colGetSize() - 1; i >= 0; --i) {
            Object object = MapCollections.this.colGetEntry(i, 0);
            int n2 = object == null ? 0 : object.hashCode();
            n += n2;
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        if (MapCollections.this.colGetSize() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new MapCollections.ArrayIterator(MapCollections.this, 0);
    }

    @Override
    public boolean remove(Object object) {
        int n = MapCollections.this.colIndexOfKey(object);
        if (n >= 0) {
            MapCollections.this.colRemoveAt(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection);
    }

    @Override
    public int size() {
        return MapCollections.this.colGetSize();
    }

    @Override
    public Object[] toArray() {
        return MapCollections.this.toArrayHelper(0);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return MapCollections.this.toArrayHelper(arrT, 0);
    }
}
