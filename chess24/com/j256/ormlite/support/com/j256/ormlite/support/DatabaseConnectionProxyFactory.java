/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;

public interface DatabaseConnectionProxyFactory {
    public DatabaseConnection createProxy(DatabaseConnection var1) throws SQLException;
}
