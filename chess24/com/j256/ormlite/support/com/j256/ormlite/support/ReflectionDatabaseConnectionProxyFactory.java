/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseConnectionProxyFactory;
import java.lang.reflect.Constructor;
import java.sql.SQLException;

public class ReflectionDatabaseConnectionProxyFactory
implements DatabaseConnectionProxyFactory {
    private final Constructor<? extends DatabaseConnection> constructor;
    private final Class<? extends DatabaseConnection> proxyClass;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ReflectionDatabaseConnectionProxyFactory(Class<? extends DatabaseConnection> class_) throws IllegalArgumentException {
        this.proxyClass = class_;
        try {
            this.constructor = class_.getConstructor(DatabaseConnection.class);
            return;
        }
        catch (Exception exception) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find constructor with DatabaseConnection argument in ");
        stringBuilder.append(class_);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public DatabaseConnection createProxy(DatabaseConnection databaseConnection) throws SQLException {
        try {
            databaseConnection = this.constructor.newInstance(databaseConnection);
            return databaseConnection;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create a new instance of ");
            stringBuilder.append(this.proxyClass);
            throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
        }
    }
}
