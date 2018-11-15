/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntegerObjectType;

public class IntType
extends IntegerObjectType {
    private static final IntType singleTon = new IntType();

    private IntType() {
        super(SqlType.INTEGER, new Class[]{Integer.TYPE});
    }

    protected IntType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static IntType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
