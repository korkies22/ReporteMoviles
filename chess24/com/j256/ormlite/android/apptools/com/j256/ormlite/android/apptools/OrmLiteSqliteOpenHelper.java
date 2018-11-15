/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 */
package com.j256.ormlite.android.apptools;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfigLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

public abstract class OrmLiteSqliteOpenHelper
extends SQLiteOpenHelper {
    protected static Logger logger = LoggerFactory.getLogger(OrmLiteSqliteOpenHelper.class);
    protected boolean cancelQueriesEnabled;
    protected AndroidConnectionSource connectionSource = new AndroidConnectionSource(this);
    private volatile boolean isOpen = true;

    public OrmLiteSqliteOpenHelper(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n) {
        super(context, string, cursorFactory, n);
        logger.trace("{}: constructed connectionSource {}", (Object)this, (Object)this.connectionSource);
    }

    public OrmLiteSqliteOpenHelper(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n, int n2) {
        this(context, string, cursorFactory, n, OrmLiteSqliteOpenHelper.openFileId(context, n2));
    }

    public OrmLiteSqliteOpenHelper(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n, File file) {
        this(context, string, cursorFactory, n, OrmLiteSqliteOpenHelper.openFile(file));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public OrmLiteSqliteOpenHelper(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n, InputStream inputStream) {
        Throwable throwable2222;
        super(context, string, cursorFactory, n);
        if (inputStream == null) {
            return;
        }
        DaoManager.addCachedDatabaseConfigs(DatabaseTableConfigLoader.loadDatabaseConfigFromReader(new BufferedReader(new InputStreamReader(inputStream), 4096)));
        try {
            inputStream.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLException sQLException) {}
            {
                throw new IllegalStateException("Could not load object config file", sQLException);
            }
        }
        try {
            inputStream.close();
        }
        catch (IOException iOException) {
            throw throwable2222;
        }
        throw throwable2222;
    }

    private static InputStream openFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        }
        catch (FileNotFoundException fileNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not open config file ");
            stringBuilder.append(file);
            throw new IllegalArgumentException(stringBuilder.toString(), fileNotFoundException);
        }
    }

    private static InputStream openFileId(Context object, int n) {
        if ((object = object.getResources().openRawResource(n)) == null) {
            object = new StringBuilder();
            object.append("Could not find object config file with id ");
            object.append(n);
            throw new IllegalStateException(object.toString());
        }
        return object;
    }

    public void close() {
        super.close();
        this.connectionSource.close();
        this.isOpen = false;
    }

    public ConnectionSource getConnectionSource() {
        if (!this.isOpen) {
            logger.warn(new IllegalStateException(), "Getting connectionSource was called after closed");
        }
        return this.connectionSource;
    }

    public <D extends Dao<T, ?>, T> D getDao(Class<T> class_) throws SQLException {
        return DaoManager.createDao(this.getConnectionSource(), class_);
    }

    public <D extends RuntimeExceptionDao<T, ?>, T> D getRuntimeExceptionDao(Class<T> class_) {
        RuntimeExceptionDao runtimeExceptionDao;
        try {
            runtimeExceptionDao = new RuntimeExceptionDao(this.getDao(class_));
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create RuntimeExcepitionDao for class ");
            stringBuilder.append(class_);
            throw new RuntimeException(stringBuilder.toString(), sQLException);
        }
        return (D)runtimeExceptionDao;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        ConnectionSource connectionSource = this.getConnectionSource();
        DatabaseConnection databaseConnection = connectionSource.getSpecialConnection();
        boolean bl = true;
        if (databaseConnection == null) {
            databaseConnection = new AndroidDatabaseConnection(sQLiteDatabase, true, this.cancelQueriesEnabled);
            try {
                connectionSource.saveSpecialConnection(databaseConnection);
            }
            catch (SQLException sQLException) {
                throw new IllegalStateException("Could not save special connection", sQLException);
            }
        } else {
            bl = false;
        }
        try {
            this.onCreate(sQLiteDatabase, connectionSource);
            return;
        }
        finally {
            if (bl) {
                connectionSource.clearSpecialConnection(databaseConnection);
            }
        }
    }

    public abstract void onCreate(SQLiteDatabase var1, ConnectionSource var2);

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        ConnectionSource connectionSource = this.getConnectionSource();
        DatabaseConnection databaseConnection = connectionSource.getSpecialConnection();
        boolean bl = true;
        if (databaseConnection == null) {
            databaseConnection = new AndroidDatabaseConnection(sQLiteDatabase, true, this.cancelQueriesEnabled);
            try {
                connectionSource.saveSpecialConnection(databaseConnection);
            }
            catch (SQLException sQLException) {
                throw new IllegalStateException("Could not save special connection", sQLException);
            }
        } else {
            bl = false;
        }
        try {
            this.onUpgrade(sQLiteDatabase, connectionSource, n, n2);
            return;
        }
        finally {
            if (bl) {
                connectionSource.clearSpecialConnection(databaseConnection);
            }
        }
    }

    public abstract void onUpgrade(SQLiteDatabase var1, ConnectionSource var2, int var3, int var4);

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(Object.super.hashCode()));
        return stringBuilder.toString();
    }
}
