/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.SQLException;
import java.sql.Savepoint;

public class DatabaseConnectionProxy
implements DatabaseConnection {
    private final DatabaseConnection proxy;

    public DatabaseConnectionProxy(DatabaseConnection databaseConnection) {
        this.proxy = databaseConnection;
    }

    @Override
    public void close() throws SQLException {
        if (this.proxy != null) {
            this.proxy.close();
        }
    }

    @Override
    public void closeQuietly() {
        if (this.proxy != null) {
            this.proxy.closeQuietly();
        }
    }

    @Override
    public void commit(Savepoint savepoint) throws SQLException {
        if (this.proxy != null) {
            this.proxy.commit(savepoint);
        }
    }

    @Override
    public CompiledStatement compileStatement(String string, StatementBuilder.StatementType statementType, FieldType[] arrfieldType, int n) throws SQLException {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.compileStatement(string, statementType, arrfieldType, n);
    }

    @Override
    public int delete(String string, Object[] arrobject, FieldType[] arrfieldType) throws SQLException {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.delete(string, arrobject, arrfieldType);
    }

    @Override
    public int executeStatement(String string, int n) throws SQLException {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.executeStatement(string, n);
    }

    @Override
    public int insert(String string, Object[] arrobject, FieldType[] arrfieldType, GeneratedKeyHolder generatedKeyHolder) throws SQLException {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.insert(string, arrobject, arrfieldType, generatedKeyHolder);
    }

    @Override
    public boolean isAutoCommit() throws SQLException {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isAutoCommit();
    }

    @Override
    public boolean isAutoCommitSupported() throws SQLException {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isAutoCommitSupported();
    }

    @Override
    public boolean isClosed() throws SQLException {
        if (this.proxy == null) {
            return true;
        }
        return this.proxy.isClosed();
    }

    @Override
    public boolean isTableExists(String string) throws SQLException {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isTableExists(string);
    }

    @Override
    public long queryForLong(String string) throws SQLException {
        if (this.proxy == null) {
            return 0L;
        }
        return this.proxy.queryForLong(string);
    }

    @Override
    public long queryForLong(String string, Object[] arrobject, FieldType[] arrfieldType) throws SQLException {
        if (this.proxy == null) {
            return 0L;
        }
        return this.proxy.queryForLong(string, arrobject, arrfieldType);
    }

    @Override
    public <T> Object queryForOne(String string, Object[] arrobject, FieldType[] arrfieldType, GenericRowMapper<T> genericRowMapper, ObjectCache objectCache) throws SQLException {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.queryForOne(string, arrobject, arrfieldType, genericRowMapper, objectCache);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        if (this.proxy != null) {
            this.proxy.rollback(savepoint);
        }
    }

    @Override
    public void setAutoCommit(boolean bl) throws SQLException {
        if (this.proxy != null) {
            this.proxy.setAutoCommit(bl);
        }
    }

    @Override
    public Savepoint setSavePoint(String string) throws SQLException {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.setSavePoint(string);
    }

    @Override
    public int update(String string, Object[] arrobject, FieldType[] arrfieldType) throws SQLException {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.update(string, arrobject, arrfieldType);
    }
}
