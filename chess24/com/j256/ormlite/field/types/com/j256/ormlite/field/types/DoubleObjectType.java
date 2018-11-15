/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class DoubleObjectType
extends BaseDataType {
    private static final DoubleObjectType singleTon = new DoubleObjectType();

    private DoubleObjectType() {
        super(SqlType.DOUBLE, new Class[]{Double.class});
    }

    protected DoubleObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static DoubleObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Double.parseDouble(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getDouble(n);
    }
}
