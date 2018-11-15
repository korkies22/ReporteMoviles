/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.lang.reflect.Field;

public class VoidType
extends BaseDataType {
    VoidType() {
        super(null, new Class[0]);
    }

    @Override
    public boolean isValidForField(Field field) {
        return false;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return null;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) {
        return null;
    }
}
