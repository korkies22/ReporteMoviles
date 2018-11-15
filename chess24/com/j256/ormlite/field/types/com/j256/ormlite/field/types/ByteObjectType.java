/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class ByteObjectType
extends BaseDataType {
    private static final ByteObjectType singleTon = new ByteObjectType();

    private ByteObjectType() {
        super(SqlType.BYTE, new Class[]{Byte.class});
    }

    protected ByteObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static ByteObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Byte.parseByte(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getByte(n);
    }
}
