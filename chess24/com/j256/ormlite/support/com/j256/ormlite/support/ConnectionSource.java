/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;

public interface ConnectionSource {
    public void clearSpecialConnection(DatabaseConnection var1);

    public void close() throws SQLException;

    public void closeQuietly();

    public DatabaseType getDatabaseType();

    public DatabaseConnection getReadOnlyConnection() throws SQLException;

    public DatabaseConnection getReadWriteConnection() throws SQLException;

    public DatabaseConnection getSpecialConnection();

    public boolean isOpen();

    public void releaseConnection(DatabaseConnection var1) throws SQLException;

    public boolean saveSpecialConnection(DatabaseConnection var1) throws SQLException;
}
