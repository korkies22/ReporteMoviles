/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class LongStringType
extends StringType {
    private static final LongStringType singleTon = new LongStringType();

    private LongStringType() {
        super(SqlType.LONG_STRING, new Class[0]);
    }

    protected LongStringType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static LongStringType getSingleton() {
        return singleTon;
    }

    @Override
    public int getDefaultWidth() {
        return 0;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return String.class;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }
}
