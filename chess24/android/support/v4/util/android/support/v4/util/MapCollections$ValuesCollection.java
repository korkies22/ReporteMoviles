/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import java.util.Collection;
import java.util.Iterator;

final class MapCollections.ValuesCollection
implements Collection<V> {
    MapCollections.ValuesCollection() {
    }

    @Override
    public boolean add(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends V> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        MapCollections.this.colClear();
    }

    @Override
    public boolean contains(Object object) {
        if (MapCollections.this.colIndexOfValue(object) >= 0) {
            return true;
        }
        return false;
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
    public boolean isEmpty() {
        if (MapCollections.this.colGetSize() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<V> iterator() {
        return new MapCollections.ArrayIterator(MapCollections.this, 1);
    }

    @Override
    public boolean remove(Object object) {
        int n = MapCollections.this.colIndexOfValue(object);
        if (n >= 0) {
            MapCollections.this.colRemoveAt(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n = MapCollections.this.colGetSize();
        int n2 = 0;
        boolean bl = false;
        while (n2 < n) {
            int n3 = n;
            int n4 = n2;
            if (collection.contains(MapCollections.this.colGetEntry(n2, 1))) {
                MapCollections.this.colRemoveAt(n2);
                n4 = n2 - 1;
                n3 = n - 1;
                bl = true;
            }
            n2 = n4 + 1;
            n = n3;
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        int n = MapCollections.this.colGetSize();
        int n2 = 0;
        boolean bl = false;
        while (n2 < n) {
            int n3 = n;
            int n4 = n2;
            if (!collection.contains(MapCollections.this.colGetEntry(n2, 1))) {
                MapCollections.this.colRemoveAt(n2);
                n4 = n2 - 1;
                n3 = n - 1;
                bl = true;
            }
            n2 = n4 + 1;
            n = n3;
        }
        return bl;
    }

    @Override
    public int size() {
        return MapCollections.this.colGetSize();
    }

    @Override
    public Object[] toArray() {
        return MapCollections.this.toArrayHelper(1);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return MapCollections.this.toArrayHelper(arrT, 1);
    }
}
