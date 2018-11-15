/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteOpenHelper
 */
package com.j256.ormlite.android;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseConnectionProxyFactory;

public class AndroidConnectionSource
extends BaseConnectionSource
implements ConnectionSource {
    private static DatabaseConnectionProxyFactory connectionProxyFactory;
    private static final Logger logger;
    private boolean cancelQueriesEnabled = false;
    private DatabaseConnection connection = null;
    private final DatabaseType databaseType = new SqliteAndroidDatabaseType();
    private final SQLiteOpenHelper helper;
    private volatile boolean isOpen = true;
    private final SQLiteDatabase sqliteDatabase;

    static {
        logger = LoggerFactory.getLogger(AndroidConnectionSource.class);
    }

    public AndroidConnectionSource(SQLiteDatabase sQLiteDatabase) {
        this.helper = null;
        this.sqliteDatabase = sQLiteDatabase;
    }

    public AndroidConnectionSource(SQLiteOpenHelper sQLiteOpenHelper) {
        this.helper = sQLiteOpenHelper;
        this.sqliteDatabase = null;
    }

    public static void setDatabaseConnectionProxyFactory(DatabaseConnectionProxyFactory databaseConnectionProxyFactory) {
        connectionProxyFactory = databaseConnectionProxyFactory;
    }

    @Override
    public void clearSpecialConnection(DatabaseConnection databaseConnection) {
        this.clearSpecial(databaseConnection, logger);
    }

    @Override
    public void close() {
        this.isOpen = false;
    }

    @Override
    public void closeQuietly() {
        this.close();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    @Override
    public DatabaseConnection getReadOnlyConnection() throws java.sql.SQLException {
        return this.getReadWriteConnection();
    }

    @Override
    public DatabaseConnection getReadWriteConnection() throws java.sql.SQLException {
        DatabaseConnection databaseConnection = this.getSavedConnection();
        if (databaseConnection != null) {
            return databaseConnection;
        }
        if (this.connection == null) {
            if (this.sqliteDatabase == null) {
                try {
                    databaseConnection = this.helper.getWritableDatabase();
                }
                catch (SQLException sQLException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Getting a writable database from helper ");
                    stringBuilder.append((Object)this.helper);
                    stringBuilder.append(" failed");
                    throw SqlExceptionUtil.create(stringBuilder.toString(), (Throwable)sQLException);
                }
            } else {
                databaseConnection = this.sqliteDatabase;
            }
            this.connection = new AndroidDatabaseConnection((SQLiteDatabase)databaseConnection, true, this.cancelQueriesEnabled);
            if (connectionProxyFactory != null) {
                this.connection = connectionProxyFactory.createProxy(this.connection);
            }
            logger.trace("created connection {} for db {}, helper {}", this.connection, (Object)databaseConnection, (Object)this.helper);
        } else {
            logger.trace("{}: returning read-write connection {}, helper {}", this, (Object)this.connection, (Object)this.helper);
        }
        return this.connection;
    }

    public boolean isCancelQueriesEnabled() {
        return this.cancelQueriesEnabled;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void releaseConnection(DatabaseConnection databaseConnection) {
    }

    @Override
    public boolean saveSpecialConnection(DatabaseConnection databaseConnection) throws java.sql.SQLException {
        return this.saveSpecial(databaseConnection);
    }

    public void setCancelQueriesEnabled(boolean bl) {
        this.cancelQueriesEnabled = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(Object.super.hashCode()));
        return stringBuilder.toString();
    }
}
