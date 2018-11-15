/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class BooleanObjectType
extends BaseDataType {
    private static final BooleanObjectType singleTon = new BooleanObjectType();

    private BooleanObjectType() {
        super(SqlType.BOOLEAN, new Class[]{Boolean.class});
    }

    protected BooleanObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static BooleanObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Boolean.parseBoolean(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getBoolean(n);
    }
}
