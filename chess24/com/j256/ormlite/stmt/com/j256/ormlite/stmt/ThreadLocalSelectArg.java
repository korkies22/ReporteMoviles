/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.BaseArgumentHolder;

public class ThreadLocalSelectArg
extends BaseArgumentHolder
implements ArgumentHolder {
    private ThreadLocal<ValueWrapper> threadValue = new ThreadLocal();

    public ThreadLocalSelectArg() {
    }

    public ThreadLocalSelectArg(SqlType sqlType, Object object) {
        super(sqlType);
        this.setValue(object);
    }

    public ThreadLocalSelectArg(Object object) {
        this.setValue(object);
    }

    public ThreadLocalSelectArg(String string, Object object) {
        super(string);
        this.setValue(object);
    }

    @Override
    protected Object getValue() {
        ValueWrapper valueWrapper = this.threadValue.get();
        if (valueWrapper == null) {
            return null;
        }
        return valueWrapper.value;
    }

    @Override
    protected boolean isValueSet() {
        if (this.threadValue.get() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void setValue(Object object) {
        this.threadValue.set(new ValueWrapper(object));
    }

    private static class ValueWrapper {
        Object value;

        public ValueWrapper(Object object) {
            this.value = object;
        }
    }

}
