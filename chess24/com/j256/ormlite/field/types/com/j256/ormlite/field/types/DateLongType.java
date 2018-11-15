/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.Date;

public class DateLongType
extends BaseDateType {
    private static final DateLongType singleTon = new DateLongType();

    private DateLongType() {
        super(SqlType.LONG, new Class[0]);
    }

    protected DateLongType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static DateLongType getSingleton() {
        return singleTon;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return Date.class;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return ((Date)object).getTime();
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        long l;
        try {
            l = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with field ");
            stringBuilder.append(fieldType);
            stringBuilder.append(" parsing default date-long value: ");
            stringBuilder.append(string);
            throw SqlExceptionUtil.create(stringBuilder.toString(), numberFormatException);
        }
        return l;
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) {
        return this.sqlArgToJava(fieldType, Long.parseLong(string), n);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getLong(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
        return new Date((Long)object);
    }
}
