/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.FloatObjectType;

public class FloatType
extends FloatObjectType {
    private static final FloatType singleTon = new FloatType();

    private FloatType() {
        super(SqlType.FLOAT, new Class[]{Float.TYPE});
    }

    protected FloatType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static FloatType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
