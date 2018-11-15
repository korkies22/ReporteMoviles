/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.ShortObjectType;

public class ShortType
extends ShortObjectType {
    private static final ShortType singleTon = new ShortType();

    private ShortType() {
        super(SqlType.SHORT, new Class[]{Short.TYPE});
    }

    protected ShortType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static ShortType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
