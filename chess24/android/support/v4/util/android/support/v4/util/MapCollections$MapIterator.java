/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.ContainerHelpers;
import android.support.v4.util.MapCollections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

final class MapCollections.MapIterator
implements Iterator<Map.Entry<K, V>>,
Map.Entry<K, V> {
    int mEnd;
    boolean mEntryValid = false;
    int mIndex;

    MapCollections.MapIterator() {
        this.mEnd = MapCollections.this.colGetSize() - 1;
        this.mIndex = -1;
    }

    @Override
    public boolean equals(Object object) {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        boolean bl = object instanceof Map.Entry;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (Map.Entry)object;
        bl = bl2;
        if (ContainerHelpers.equal(object.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))) {
            bl = bl2;
            if (ContainerHelpers.equal(object.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1))) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public K getKey() {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return (K)MapCollections.this.colGetEntry(this.mIndex, 0);
    }

    @Override
    public V getValue() {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return (V)MapCollections.this.colGetEntry(this.mIndex, 1);
    }

    @Override
    public boolean hasNext() {
        if (this.mIndex < this.mEnd) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        Object object = MapCollections.this;
        int n = this.mIndex;
        int n2 = 0;
        object = object.colGetEntry(n, 0);
        Object object2 = MapCollections.this.colGetEntry(this.mIndex, 1);
        n = object == null ? 0 : object.hashCode();
        if (object2 != null) {
            n2 = object2.hashCode();
        }
        return n ^ n2;
    }

    @Override
    public Map.Entry<K, V> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        ++this.mIndex;
        this.mEntryValid = true;
        return this;
    }

    @Override
    public void remove() {
        if (!this.mEntryValid) {
            throw new IllegalStateException();
        }
        MapCollections.this.colRemoveAt(this.mIndex);
        --this.mIndex;
        --this.mEnd;
        this.mEntryValid = false;
    }

    @Override
    public V setValue(V v) {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return MapCollections.this.colSetValue(this.mIndex, v);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getKey());
        stringBuilder.append("=");
        stringBuilder.append(this.getValue());
        return stringBuilder.toString();
    }
}
