/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.UUID;

public class UuidType
extends BaseDataType {
    public static int DEFAULT_WIDTH = 48;
    private static final UuidType singleTon = new UuidType();

    private UuidType() {
        super(SqlType.STRING, new Class[]{UUID.class});
    }

    protected UuidType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static UuidType getSingleton() {
        return singleTon;
    }

    @Override
    public Object generateId() {
        return UUID.randomUUID();
    }

    @Override
    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    public boolean isSelfGeneratedId() {
        return true;
    }

    @Override
    public boolean isValidGeneratedType() {
        return true;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return ((UUID)object).toString();
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        try {
            UUID uUID = UUID.fromString(string);
            return uUID;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with field ");
            stringBuilder.append(fieldType);
            stringBuilder.append(" parsing default UUID-string '");
            stringBuilder.append(string);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), illegalArgumentException);
        }
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getString(n);
    }

    @Override
    public Object sqlArgToJava(FieldType object, Object object2, int n) throws SQLException {
        object = (String)object2;
        try {
            object2 = UUID.fromString((String)object);
            return object2;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with column ");
            stringBuilder.append(n);
            stringBuilder.append(" parsing UUID-string '");
            stringBuilder.append((String)object);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), illegalArgumentException);
        }
    }
}
