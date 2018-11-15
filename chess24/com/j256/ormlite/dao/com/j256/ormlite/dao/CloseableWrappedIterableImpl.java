/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import java.sql.SQLException;
import java.util.Iterator;

public class CloseableWrappedIterableImpl<T>
implements CloseableWrappedIterable<T> {
    private final CloseableIterable<T> iterable;
    private CloseableIterator<T> iterator;

    public CloseableWrappedIterableImpl(CloseableIterable<T> closeableIterable) {
        this.iterable = closeableIterable;
    }

    @Override
    public void close() throws SQLException {
        if (this.iterator != null) {
            this.iterator.close();
            this.iterator = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CloseableIterator<T> closeableIterator() {
        try {
            this.close();
        }
        catch (SQLException sQLException) {}
        this.iterator = this.iterable.closeableIterator();
        return this.iterator;
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.closeableIterator();
    }
}
