/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import java.sql.SQLException;
import java.util.Collection;

public interface ForeignCollection<T>
extends Collection<T>,
CloseableIterable<T> {
    @Override
    public boolean add(T var1);

    public void closeLastIterator() throws SQLException;

    public CloseableIterator<T> closeableIterator(int var1);

    public CloseableWrappedIterable<T> getWrappedIterable();

    public CloseableWrappedIterable<T> getWrappedIterable(int var1);

    public boolean isEager();

    public CloseableIterator<T> iterator(int var1);

    public CloseableIterator<T> iteratorThrow() throws SQLException;

    public CloseableIterator<T> iteratorThrow(int var1) throws SQLException;

    public int refresh(T var1) throws SQLException;

    public int refreshAll() throws SQLException;

    public int refreshCollection() throws SQLException;

    public int update(T var1) throws SQLException;

    public int updateAll() throws SQLException;
}
