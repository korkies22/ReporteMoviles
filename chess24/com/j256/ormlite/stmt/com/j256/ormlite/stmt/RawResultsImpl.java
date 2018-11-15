/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.SelectIterator;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RawResultsImpl<T>
implements GenericRawResults<T> {
    private final String[] columnNames;
    private SelectIterator<T, Void> iterator;

    public RawResultsImpl(ConnectionSource connectionSource, DatabaseConnection databaseConnection, String string, Class<?> class_, CompiledStatement compiledStatement, GenericRowMapper<T> genericRowMapper, ObjectCache objectCache) throws SQLException {
        this.iterator = new SelectIterator(class_, null, genericRowMapper, connectionSource, databaseConnection, compiledStatement, string, objectCache);
        this.columnNames = this.iterator.getRawResults().getColumnNames();
    }

    @Override
    public void close() throws SQLException {
        if (this.iterator != null) {
            this.iterator.close();
            this.iterator = null;
        }
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return this.iterator;
    }

    @Override
    public String[] getColumnNames() {
        return this.columnNames;
    }

    @Override
    public T getFirstResult() throws SQLException {
        try {
            if (this.iterator.hasNextThrow()) {
                T t = this.iterator.nextThrow();
                return t;
            }
            return null;
        }
        finally {
            this.close();
        }
    }

    @Override
    public int getNumberColumns() {
        return this.columnNames.length;
    }

    @Override
    public List<T> getResults() throws SQLException {
        ArrayList<T> arrayList = new ArrayList<T>();
        try {
            while (this.iterator.hasNext()) {
                arrayList.add(this.iterator.next());
            }
            return arrayList;
        }
        finally {
            this.iterator.close();
        }
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.iterator;
    }
}
