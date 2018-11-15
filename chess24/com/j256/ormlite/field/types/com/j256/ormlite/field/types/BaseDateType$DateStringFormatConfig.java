/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.types.BaseDateType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

protected static class BaseDateType.DateStringFormatConfig {
    final String dateFormatStr;
    private final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateStringFormatConfig.this.dateFormatStr);
        }
    };

    public BaseDateType.DateStringFormatConfig(String string) {
        this.dateFormatStr = string;
    }

    public DateFormat getDateFormat() {
        return this.threadLocal.get();
    }

    public String toString() {
        return this.dateFormatStr;
    }

}
