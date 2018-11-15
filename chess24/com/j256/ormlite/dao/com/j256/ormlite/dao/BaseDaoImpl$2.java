/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import java.util.Iterator;

class BaseDaoImpl
implements CloseableIterable<T> {
    BaseDaoImpl() {
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        try {
            CloseableIterator closeableIterator = BaseDaoImpl.this.createIterator(-1);
            return closeableIterator;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build iterator for ");
            stringBuilder.append(BaseDaoImpl.this.dataClass);
            throw new IllegalStateException(stringBuilder.toString(), exception);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return this.closeableIterator();
    }
}
