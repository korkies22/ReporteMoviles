/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.types.BaseDateType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class BaseDateType.DateStringFormatConfig
extends ThreadLocal<DateFormat> {
    BaseDateType.DateStringFormatConfig() {
    }

    @Override
    protected DateFormat initialValue() {
        return new SimpleDateFormat(DateStringFormatConfig.this.dateFormatStr);
    }
}
