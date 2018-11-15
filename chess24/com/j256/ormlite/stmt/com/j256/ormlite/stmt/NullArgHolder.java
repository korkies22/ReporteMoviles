/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;

public class NullArgHolder
implements ArgumentHolder {
    @Override
    public String getColumnName() {
        return "null-holder";
    }

    @Override
    public FieldType getFieldType() {
        return null;
    }

    @Override
    public Object getSqlArgValue() {
        return null;
    }

    @Override
    public SqlType getSqlType() {
        return SqlType.STRING;
    }

    @Override
    public void setMetaInfo(FieldType fieldType) {
    }

    @Override
    public void setMetaInfo(String string) {
    }

    @Override
    public void setMetaInfo(String string, FieldType fieldType) {
    }

    @Override
    public void setValue(Object object) {
        object = new StringBuilder();
        object.append("Cannot set null on ");
        object.append(this.getClass());
        throw new UnsupportedOperationException(object.toString());
    }
}
