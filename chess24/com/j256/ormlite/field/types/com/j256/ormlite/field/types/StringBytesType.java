/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class StringBytesType
extends BaseDataType {
    private static final String DEFAULT_STRING_BYTES_CHARSET_NAME = "Unicode";
    private static final StringBytesType singleTon = new StringBytesType();

    private StringBytesType() {
        super(SqlType.BYTE_ARRAY, new Class[0]);
    }

    protected StringBytesType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    private String getCharsetName(FieldType fieldType) {
        if (fieldType != null && fieldType.getFormat() != null) {
            return fieldType.getFormat();
        }
        return DEFAULT_STRING_BYTES_CHARSET_NAME;
    }

    public static StringBytesType getSingleton() {
        return singleTon;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return String.class;
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
    public Object javaToSqlArg(FieldType object, Object arrby) throws SQLException {
        arrby = (String)arrby;
        object = this.getCharsetName((FieldType)object);
        try {
            arrby = arrby.getBytes((String)object);
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not convert string with charset name: ");
            stringBuilder.append((String)object);
            throw SqlExceptionUtil.create(stringBuilder.toString(), unsupportedEncodingException);
        }
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        throw new SQLException("String-bytes type cannot have default values");
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        throw new SQLException("String-bytes type cannot be converted from string to Java");
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getBytes(n);
    }

    @Override
    public Object sqlArgToJava(FieldType object, Object object2, int n) throws SQLException {
        object2 = object2;
        object = this.getCharsetName((FieldType)object);
        try {
            object2 = new String((byte[])object2, (String)object);
            return object2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not convert string with charset name: ");
            stringBuilder.append((String)object);
            throw SqlExceptionUtil.create(stringBuilder.toString(), unsupportedEncodingException);
        }
    }
}
