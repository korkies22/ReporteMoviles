/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import java.sql.SQLException;
import java.util.List;

public interface GenericRawResults<T>
extends CloseableWrappedIterable<T> {
    @Override
    public void close() throws SQLException;

    public String[] getColumnNames();

    public T getFirstResult() throws SQLException;

    public int getNumberColumns();

    public List<T> getResults() throws SQLException;
}
