/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class ShortObjectType
extends BaseDataType {
    private static final ShortObjectType singleTon = new ShortObjectType();

    private ShortObjectType() {
        super(SqlType.SHORT, new Class[]{Short.class});
    }

    protected ShortObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static ShortObjectType getSingleton() {
        return singleTon;
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
    public Object moveToNextValue(Object object) {
        if (object == null) {
            return (short)1;
        }
        return (short)((Short)object + 1);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Short.parseShort(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getShort(n);
    }
}
