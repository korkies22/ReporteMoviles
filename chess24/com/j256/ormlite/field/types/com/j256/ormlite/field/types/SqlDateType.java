/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType;
import com.j256.ormlite.field.types.DateType;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;

public class SqlDateType
extends DateType {
    private static final SqlDateType singleTon = new SqlDateType();
    private static final BaseDateType.DateStringFormatConfig sqlDateFormatConfig = new BaseDateType.DateStringFormatConfig("yyyy-MM-dd");

    private SqlDateType() {
        super(SqlType.DATE, new Class[]{Date.class});
    }

    protected SqlDateType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static SqlDateType getSingleton() {
        return singleTon;
    }

    @Override
    protected BaseDateType.DateStringFormatConfig getDefaultDateFormatConfig() {
        return sqlDateFormatConfig;
    }

    @Override
    public boolean isValidForField(Field field) {
        if (field.getType() == Date.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return new Timestamp(((Date)object).getTime());
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) {
        return this.sqlArgToJava(fieldType, Timestamp.valueOf(string), n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
        return new Date(((Timestamp)object).getTime());
    }
}
