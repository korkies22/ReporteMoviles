/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.CharacterObjectType;

public class CharType
extends CharacterObjectType {
    private static final CharType singleTon = new CharType();

    private CharType() {
        super(SqlType.CHAR, new Class[]{Character.TYPE});
    }

    protected CharType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static CharType getSingleton() {
        return singleTon;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public Object javaToSqlArg(FieldType object, Object object2) {
        object = (Character)object2;
        if (object != null && object.charValue() != '\u0000') {
            return object;
        }
        return null;
    }
}
