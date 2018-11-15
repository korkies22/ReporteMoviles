/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateType;
import java.lang.reflect.Field;
import java.sql.Timestamp;

public class TimeStampType
extends DateType {
    private static final TimeStampType singleTon = new TimeStampType();

    private TimeStampType() {
        super(SqlType.DATE, new Class[]{Timestamp.class});
    }

    protected TimeStampType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static TimeStampType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isValidForField(Field field) {
        if (field.getType() == Timestamp.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return object;
    }

    @Override
    public Object moveToNextValue(Object object) {
        long l = System.currentTimeMillis();
        if (object == null) {
            return new Timestamp(l);
        }
        if (l == ((Timestamp)object).getTime()) {
            return new Timestamp(l + 1L);
        }
        return new Timestamp(l);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
        return object;
    }
}
