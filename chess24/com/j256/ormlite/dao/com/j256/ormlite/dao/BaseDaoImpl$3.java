/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.stmt.PreparedQuery;
import java.util.Iterator;

class BaseDaoImpl
implements CloseableIterable<T> {
    final /* synthetic */ PreparedQuery val$preparedQuery;

    BaseDaoImpl(PreparedQuery preparedQuery) {
        this.val$preparedQuery = preparedQuery;
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        try {
            CloseableIterator closeableIterator = BaseDaoImpl.this.createIterator(this.val$preparedQuery, -1);
            return closeableIterator;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build prepared-query iterator for ");
            stringBuilder.append(BaseDaoImpl.this.dataClass);
            throw new IllegalStateException(stringBuilder.toString(), exception);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return this.closeableIterator();
    }
}
