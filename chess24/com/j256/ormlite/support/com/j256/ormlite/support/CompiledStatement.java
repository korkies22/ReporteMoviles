/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public interface CompiledStatement {
    public void cancel() throws SQLException;

    public void close() throws SQLException;

    public void closeQuietly();

    public int getColumnCount() throws SQLException;

    public String getColumnName(int var1) throws SQLException;

    public int runExecute() throws SQLException;

    public DatabaseResults runQuery(ObjectCache var1) throws SQLException;

    public int runUpdate() throws SQLException;

    public void setMaxRows(int var1) throws SQLException;

    public void setObject(int var1, Object var2, SqlType var3) throws SQLException;

    public void setQueryTimeout(long var1) throws SQLException;
}
