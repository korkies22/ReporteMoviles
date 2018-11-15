/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import java.util.Iterator;

class LazyForeignCollection
implements CloseableIterable<T> {
    final /* synthetic */ int val$flags;

    LazyForeignCollection(int n) {
        this.val$flags = n;
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        try {
            CloseableIterator closeableIterator = LazyForeignCollection.this.seperateIteratorThrow(this.val$flags);
            return closeableIterator;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build lazy iterator for ");
            stringBuilder.append(LazyForeignCollection.this.dao.getDataClass());
            throw new IllegalStateException(stringBuilder.toString(), exception);
        }
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.closeableIterator();
    }
}
