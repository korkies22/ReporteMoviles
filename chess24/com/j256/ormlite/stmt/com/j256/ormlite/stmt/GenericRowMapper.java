/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public interface GenericRowMapper<T> {
    public T mapRow(DatabaseResults var1) throws SQLException;
}
