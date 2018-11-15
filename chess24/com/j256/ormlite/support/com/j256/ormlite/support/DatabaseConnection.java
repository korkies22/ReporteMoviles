/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.SQLException;
import java.sql.Savepoint;

public interface DatabaseConnection {
    public static final int DEFAULT_RESULT_FLAGS = -1;
    public static final Object MORE_THAN_ONE = new Object();

    public void close() throws SQLException;

    public void closeQuietly();

    public void commit(Savepoint var1) throws SQLException;

    public CompiledStatement compileStatement(String var1, StatementBuilder.StatementType var2, FieldType[] var3, int var4) throws SQLException;

    public int delete(String var1, Object[] var2, FieldType[] var3) throws SQLException;

    public int executeStatement(String var1, int var2) throws SQLException;

    public int insert(String var1, Object[] var2, FieldType[] var3, GeneratedKeyHolder var4) throws SQLException;

    public boolean isAutoCommit() throws SQLException;

    public boolean isAutoCommitSupported() throws SQLException;

    public boolean isClosed() throws SQLException;

    public boolean isTableExists(String var1) throws SQLException;

    public long queryForLong(String var1) throws SQLException;

    public long queryForLong(String var1, Object[] var2, FieldType[] var3) throws SQLException;

    public <T> Object queryForOne(String var1, Object[] var2, FieldType[] var3, GenericRowMapper<T> var4, ObjectCache var5) throws SQLException;

    public void rollback(Savepoint var1) throws SQLException;

    public void setAutoCommit(boolean var1) throws SQLException;

    public Savepoint setSavePoint(String var1) throws SQLException;

    public int update(String var1, Object[] var2, FieldType[] var3) throws SQLException;
}
