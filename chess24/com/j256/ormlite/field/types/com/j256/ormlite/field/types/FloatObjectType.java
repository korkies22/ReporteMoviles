/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class FloatObjectType
extends BaseDataType {
    private static final FloatObjectType singleTon = new FloatObjectType();

    private FloatObjectType() {
        super(SqlType.FLOAT, new Class[]{Float.class});
    }

    protected FloatObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static FloatObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Float.valueOf(Float.parseFloat(string));
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return Float.valueOf(databaseResults.getFloat(n));
    }
}
