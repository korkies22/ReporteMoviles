/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import java.sql.SQLException;

public interface RawRowMapper<T> {
    public T mapRow(String[] var1, String[] var2) throws SQLException;
}
