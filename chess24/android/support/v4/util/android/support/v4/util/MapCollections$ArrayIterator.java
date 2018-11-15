/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class MapCollections.ArrayIterator<T>
implements Iterator<T> {
    boolean mCanRemove = false;
    int mIndex;
    final int mOffset;
    int mSize;

    MapCollections.ArrayIterator(int n) {
        this.mOffset = n;
        this.mSize = MapCollections.this.colGetSize();
    }

    @Override
    public boolean hasNext() {
        if (this.mIndex < this.mSize) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        Object object = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
        ++this.mIndex;
        this.mCanRemove = true;
        return (T)object;
    }

    @Override
    public void remove() {
        if (!this.mCanRemove) {
            throw new IllegalStateException();
        }
        --this.mIndex;
        --this.mSize;
        this.mCanRemove = false;
        MapCollections.this.colRemoveAt(this.mIndex);
    }
}
