/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.db;

import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.db.BaseSqliteDatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateStringType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;

public class SqliteAndroidDatabaseType
extends BaseSqliteDatabaseType {
    @Override
    protected void appendBooleanType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        this.appendShortType(stringBuilder, fieldType, n);
    }

    @Override
    protected void appendDateType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        this.appendStringType(stringBuilder, fieldType, n);
    }

    @Override
    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return DatabaseTableConfigUtil.fromClass(connectionSource, class_);
    }

    @Override
    public String getDatabaseName() {
        return "Android SQLite";
    }

    @Override
    protected String getDriverClassName() {
        return null;
    }

    @Override
    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        if (.$SwitchMap$com$j256$ormlite$field$SqlType[dataPersister.getSqlType().ordinal()] != 1) {
            return super.getFieldConverter(dataPersister);
        }
        return DateStringType.getSingleton();
    }

    @Override
    public boolean isBatchUseTransaction() {
        return true;
    }

    @Override
    public boolean isDatabaseUrlThisType(String string, String string2) {
        return true;
    }

    @Override
    public boolean isNestedSavePointsSupported() {
        return false;
    }

    @Override
    public void loadDriver() {
    }

}
