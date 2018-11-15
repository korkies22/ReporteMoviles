/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.field.DataType;
import java.sql.SQLException;

public interface RawRowObjectMapper<T> {
    public T mapRow(String[] var1, DataType[] var2, Object[] var3) throws SQLException;
}
