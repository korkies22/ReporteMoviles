/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseDateType
extends BaseDataType {
    protected static final DateStringFormatConfig defaultDateFormatConfig = new DateStringFormatConfig("yyyy-MM-dd HH:mm:ss.SSSSSS");

    protected BaseDateType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    protected static DateStringFormatConfig convertDateStringConfig(FieldType object, DateStringFormatConfig dateStringFormatConfig) {
        if (object == null) {
            return dateStringFormatConfig;
        }
        if ((object = (DateStringFormatConfig)object.getDataTypeConfigObj()) == null) {
            return dateStringFormatConfig;
        }
        return object;
    }

    protected static String normalizeDateString(DateStringFormatConfig object, String string) throws ParseException {
        object = object.getDateFormat();
        return object.format(object.parse(string));
    }

    protected static Date parseDateString(DateStringFormatConfig dateStringFormatConfig, String string) throws ParseException {
        return dateStringFormatConfig.getDateFormat().parse(string);
    }

    @Override
    public boolean isValidForField(Field field) {
        if (field.getType() == Date.class) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidForVersion() {
        return true;
    }

    @Override
    public Object moveToNextValue(Object object) {
        long l = System.currentTimeMillis();
        if (object == null) {
            return new Date(l);
        }
        if (l == ((Date)object).getTime()) {
            return new Date(l + 1L);
        }
        return new Date(l);
    }

    protected static class DateStringFormatConfig {
        final String dateFormatStr;
        private final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat(DateStringFormatConfig.this.dateFormatStr);
            }
        };

        public DateStringFormatConfig(String string) {
            this.dateFormatStr = string;
        }

        public DateFormat getDateFormat() {
            return this.threadLocal.get();
        }

        public String toString() {
            return this.dateFormatStr;
        }

    }

}
