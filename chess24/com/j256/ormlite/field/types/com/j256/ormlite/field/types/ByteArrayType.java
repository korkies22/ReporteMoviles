/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.Arrays;

public class ByteArrayType
extends BaseDataType {
    private static final ByteArrayType singleTon = new ByteArrayType();

    private ByteArrayType() {
        super(SqlType.BYTE_ARRAY, new Class[0]);
    }

    protected ByteArrayType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static ByteArrayType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean dataIsEqual(Object object, Object object2) {
        boolean bl = false;
        if (object == null) {
            if (object2 == null) {
                bl = true;
            }
            return bl;
        }
        if (object2 == null) {
            return false;
        }
        return Arrays.equals((byte[])object, (byte[])object2);
    }

    @Override
    public Class<?> getPrimaryClass() {
        return byte[].class;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }

    @Override
    public boolean isArgumentHolderRequired() {
        return true;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        throw new SQLException("byte[] type cannot have default values");
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        throw new SQLException("byte[] type cannot be converted from string to Java");
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getBytes(n);
    }
}
