/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class DateTimeType
extends BaseDataType {
    private static final String[] associatedClassNames;
    private static Class<?> dateTimeClass;
    private static Method getMillisMethod;
    private static Constructor<?> millisConstructor;
    private static final DateTimeType singleTon;

    static {
        singleTon = new DateTimeType();
        associatedClassNames = new String[]{"org.joda.time.DateTime"};
    }

    private DateTimeType() {
        super(SqlType.LONG, new Class[0]);
    }

    protected DateTimeType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    private Constructor<?> getConstructor() throws Exception {
        if (millisConstructor == null) {
            millisConstructor = this.getDateTimeClass().getConstructor(Long.TYPE);
        }
        return millisConstructor;
    }

    private Class<?> getDateTimeClass() throws ClassNotFoundException {
        if (dateTimeClass == null) {
            dateTimeClass = Class.forName("org.joda.time.DateTime");
        }
        return dateTimeClass;
    }

    private Method getMillisMethod() throws Exception {
        if (getMillisMethod == null) {
            getMillisMethod = this.getDateTimeClass().getMethod("getMillis", new Class[0]);
        }
        return getMillisMethod;
    }

    public static DateTimeType getSingleton() {
        return singleTon;
    }

    @Override
    public String[] getAssociatedClassNames() {
        return associatedClassNames;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Class<?> getPrimaryClass() {
        try {
            return this.getDateTimeClass();
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
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
    public Object javaToSqlArg(FieldType object, Object object2) throws SQLException {
        block3 : {
            try {
                object = this.getMillisMethod();
                if (object2 != null) break block3;
                return null;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not use reflection to get millis from Joda DateTime: ");
                stringBuilder.append(object2);
                throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
            }
        }
        object = object.invoke(object2, new Object[0]);
        return object;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Long.parseLong(string);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getLong(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) throws SQLException {
        try {
            fieldType = this.getConstructor().newInstance((Long)object);
            return fieldType;
        }
        catch (Exception exception) {
            throw SqlExceptionUtil.create("Could not use reflection to construct a Joda DateTime", exception);
        }
    }
}
