/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class IntegerObjectType
extends BaseDataType {
    private static final IntegerObjectType singleTon = new IntegerObjectType();

    private IntegerObjectType() {
        super(SqlType.INTEGER, new Class[]{Integer.class});
    }

    protected IntegerObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static IntegerObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public Object convertIdNumber(Number number) {
        return number.intValue();
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
            return 1;
        }
        return (Integer)object + 1;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Integer.parseInt(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getInt(n);
    }
}
