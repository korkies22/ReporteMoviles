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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateStringType
extends BaseDateType {
    public static int DEFAULT_WIDTH = 50;
    private static final DateStringType singleTon = new DateStringType();

    private DateStringType() {
        super(SqlType.STRING, new Class[0]);
    }

    protected DateStringType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static DateStringType getSingleton() {
        return singleTon;
    }

    @Override
    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return byte[].class;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return DateStringType.convertDateStringConfig(fieldType, defaultDateFormatConfig).getDateFormat().format((Date)object);
    }

    @Override
    public Object makeConfigObject(FieldType object) {
        if ((object = object.getFormat()) == null) {
            return defaultDateFormatConfig;
        }
        return new BaseDateType.DateStringFormatConfig((String)object);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        BaseDateType.DateStringFormatConfig dateStringFormatConfig = DateStringType.convertDateStringConfig(fieldType, defaultDateFormatConfig);
        try {
            String string2 = DateStringType.normalizeDateString(dateStringFormatConfig, string);
            return string2;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with field ");
            stringBuilder.append(fieldType);
            stringBuilder.append(" parsing default date-string '");
            stringBuilder.append(string);
            stringBuilder.append("' using '");
            stringBuilder.append(dateStringFormatConfig);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), parseException);
        }
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        return this.sqlArgToJava(fieldType, string, n);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getString(n);
    }

    @Override
    public Object sqlArgToJava(FieldType object, Object object2, int n) throws SQLException {
        object2 = (String)object2;
        object = DateStringType.convertDateStringConfig((FieldType)object, defaultDateFormatConfig);
        try {
            Date date = DateStringType.parseDateString((BaseDateType.DateStringFormatConfig)object, (String)object2);
            return date;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with column ");
            stringBuilder.append(n);
            stringBuilder.append(" parsing date-string '");
            stringBuilder.append((String)object2);
            stringBuilder.append("' using '");
            stringBuilder.append(object);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), parseException);
        }
    }
}
