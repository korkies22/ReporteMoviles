/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.Iterator;

public interface CloseableIterator<T>
extends Iterator<T> {
    public void close() throws SQLException;

    public void closeQuietly();

    public T current() throws SQLException;

    public T first() throws SQLException;

    public DatabaseResults getRawResults();

    public T moveRelative(int var1) throws SQLException;

    public void moveToNext();

    public T nextThrow() throws SQLException;

    public T previous() throws SQLException;
}
