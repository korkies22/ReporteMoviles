/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class LongObjectType
extends BaseDataType {
    private static final LongObjectType singleTon = new LongObjectType();

    private LongObjectType() {
        super(SqlType.LONG, new Class[]{Long.class});
    }

    protected LongObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static LongObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public Object convertIdNumber(Number number) {
        return number.longValue();
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public boolean isValidForVersion() {
        return true;
    }

    @Override
    public boolean isValidGeneratedType() {
        return true;
    }

    @Override
    public Object moveToNextValue(Object object) {
        if (object == null) {
            return 1L;
        }
        return (Long)object + 1L;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Long.parseLong(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getLong(n);
    }
}
