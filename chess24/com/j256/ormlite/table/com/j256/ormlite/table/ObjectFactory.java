/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

public interface ObjectFactory<T> {
    public T createObject(Constructor<T> var1, Class<T> var2) throws SQLException;
}
