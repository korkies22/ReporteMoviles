/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import java.sql.SQLException;

public interface CloseableWrappedIterable<T>
extends CloseableIterable<T> {
    public void close() throws SQLException;
}
