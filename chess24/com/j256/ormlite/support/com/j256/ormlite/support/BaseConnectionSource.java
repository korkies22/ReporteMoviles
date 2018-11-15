/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;

public abstract class BaseConnectionSource
implements ConnectionSource {
    private ThreadLocal<NestedConnection> specialConnection = new ThreadLocal();

    protected boolean clearSpecial(DatabaseConnection databaseConnection, Logger logger) {
        NestedConnection nestedConnection = this.specialConnection.get();
        if (databaseConnection != null) {
            if (nestedConnection == null) {
                logger.error("no connection has been saved when clear() called");
            } else {
                if (nestedConnection.connection == databaseConnection) {
                    if (nestedConnection.decrementAndGet() == 0) {
                        this.specialConnection.set(null);
                    }
                    return true;
                }
                logger.error("connection saved {} is not the one being cleared {}", nestedConnection.connection, (Object)databaseConnection);
            }
        }
        return false;
    }

    protected DatabaseConnection getSavedConnection() {
        NestedConnection nestedConnection = this.specialConnection.get();
        if (nestedConnection == null) {
            return null;
        }
        return nestedConnection.connection;
    }

    @Override
    public DatabaseConnection getSpecialConnection() {
        NestedConnection nestedConnection = this.specialConnection.get();
        if (nestedConnection == null) {
            return null;
        }
        return nestedConnection.connection;
    }

    protected boolean isSavedConnection(DatabaseConnection databaseConnection) {
        NestedConnection nestedConnection = this.specialConnection.get();
        if (nestedConnection == null) {
            return false;
        }
        if (nestedConnection.connection == databaseConnection) {
            return true;
        }
        return false;
    }

    protected boolean saveSpecial(DatabaseConnection databaseConnection) throws SQLException {
        NestedConnection nestedConnection = this.specialConnection.get();
        if (nestedConnection == null) {
            this.specialConnection.set(new NestedConnection(databaseConnection));
            return true;
        }
        if (nestedConnection.connection != databaseConnection) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("trying to save connection ");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(" but already have saved connection ");
            stringBuilder.append(nestedConnection.connection);
            throw new SQLException(stringBuilder.toString());
        }
        nestedConnection.increment();
        return false;
    }

    private static class NestedConnection {
        public final DatabaseConnection connection;
        private int nestedC;

        public NestedConnection(DatabaseConnection databaseConnection) {
            this.connection = databaseConnection;
            this.nestedC = 1;
        }

        public int decrementAndGet() {
            --this.nestedC;
            return this.nestedC;
        }

        public void increment() {
            ++this.nestedC;
        }
    }

}
