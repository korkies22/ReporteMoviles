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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class DateType
extends BaseDateType {
    private static final DateType singleTon = new DateType();

    private DateType() {
        super(SqlType.DATE, new Class[]{Date.class});
    }

    protected DateType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static DateType getSingleton() {
        return singleTon;
    }

    protected BaseDateType.DateStringFormatConfig getDefaultDateFormatConfig() {
        return defaultDateFormatConfig;
    }

    @Override
    public boolean isArgumentHolderRequired() {
        return true;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return new Timestamp(((Date)object).getTime());
    }

    @Override
    public Object parseDefaultString(FieldType object, String string) throws SQLException {
        object = DateType.convertDateStringConfig((FieldType)object, this.getDefaultDateFormatConfig());
        try {
            Timestamp timestamp = new Timestamp(DateType.parseDateString((BaseDateType.DateStringFormatConfig)object, string).getTime());
            return timestamp;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems parsing default date string '");
            stringBuilder.append(string);
            stringBuilder.append("' using '");
            stringBuilder.append(object);
            stringBuilder.append('\'');
            throw SqlExceptionUtil.create(stringBuilder.toString(), parseException);
        }
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getTimestamp(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
        return new Date(((Timestamp)object).getTime());
    }
}
