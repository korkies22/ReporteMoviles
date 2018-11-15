/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.db;

import com.j256.ormlite.db.BaseDatabaseType;
import com.j256.ormlite.field.BaseFieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

protected static class BaseDatabaseType.BooleanNumberFieldConverter
extends BaseFieldConverter {
    protected BaseDatabaseType.BooleanNumberFieldConverter() {
    }

    @Override
    public SqlType getSqlType() {
        return SqlType.BOOLEAN;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        byte by;
        if (((Boolean)object).booleanValue()) {
            by = 1;
            do {
                return by;
                break;
            } while (true);
        }
        by = 0;
        return by;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        byte by;
        if (Boolean.parseBoolean(string)) {
            by = 1;
            do {
                return by;
                break;
            } while (true);
        }
        by = 0;
        return by;
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) {
        return this.sqlArgToJava(fieldType, Byte.parseByte(string), n);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getByte(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
        if ((Byte)object == 1) {
            return true;
        }
        return false;
    }
}
