/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.ContainerHelpers;
import android.support.v4.util.MapCollections;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class MapCollections.EntrySet
implements Set<Map.Entry<K, V>> {
    MapCollections.EntrySet() {
    }

    @Override
    public boolean add(Map.Entry<K, V> entry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Map.Entry<K, V>> object) {
        int n = MapCollections.this.colGetSize();
        object = object.iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            MapCollections.this.colPut(entry.getKey(), entry.getValue());
        }
        if (n != MapCollections.this.colGetSize()) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        MapCollections.this.colClear();
    }

    @Override
    public boolean contains(Object object) {
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        int n = MapCollections.this.colIndexOfKey((object = (Map.Entry)object).getKey());
        if (n < 0) {
            return false;
        }
        return ContainerHelpers.equal(MapCollections.this.colGetEntry(n, 1), object.getValue());
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
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
            Object object2 = MapCollections.this.colGetEntry(i, 1);
            int n2 = object == null ? 0 : object.hashCode();
            int n3 = object2 == null ? 0 : object2.hashCode();
            n += n2 ^ n3;
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
    public Iterator<Map.Entry<K, V>> iterator() {
        return new MapCollections.MapIterator(MapCollections.this);
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return MapCollections.this.colGetSize();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        throw new UnsupportedOperationException();
    }
}
