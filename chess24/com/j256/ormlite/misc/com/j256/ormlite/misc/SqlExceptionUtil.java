/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.misc;

import java.sql.SQLException;

public class SqlExceptionUtil {
    private SqlExceptionUtil() {
    }

    public static SQLException create(String object, Throwable throwable) {
        object = new SQLException((String)object);
        object.initCause(throwable);
        return object;
    }
}
