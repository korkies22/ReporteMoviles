/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.LongObjectType;

public class LongType
extends LongObjectType {
    private static final LongType singleTon = new LongType();

    private LongType() {
        super(SqlType.LONG, new Class[]{Long.TYPE});
    }

    protected LongType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static LongType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
