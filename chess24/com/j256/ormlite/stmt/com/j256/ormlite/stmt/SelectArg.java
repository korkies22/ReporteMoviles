/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.BaseArgumentHolder;

public class SelectArg
extends BaseArgumentHolder
implements ArgumentHolder {
    private boolean hasBeenSet = false;
    private Object value = null;

    public SelectArg() {
    }

    public SelectArg(SqlType sqlType) {
        super(sqlType);
    }

    public SelectArg(SqlType sqlType, Object object) {
        super(sqlType);
        this.setValue(object);
    }

    public SelectArg(Object object) {
        this.setValue(object);
    }

    public SelectArg(String string, Object object) {
        super(string);
        this.setValue(object);
    }

    @Override
    protected Object getValue() {
        return this.value;
    }

    @Override
    protected boolean isValueSet() {
        return this.hasBeenSet;
    }

    @Override
    public void setValue(Object object) {
        this.hasBeenSet = true;
        this.value = object;
    }
}
