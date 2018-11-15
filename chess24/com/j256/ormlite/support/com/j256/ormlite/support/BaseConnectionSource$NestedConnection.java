/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.support;

import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

private static class BaseConnectionSource.NestedConnection {
    public final DatabaseConnection connection;
    private int nestedC;

    public BaseConnectionSource.NestedConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection;
        this.nestedC = 1;
    }

    public int decrementAndGet() {
        --this.nestedC;
        return this.nestedC;
    }

    public void increment() {
        ++this.nestedC;
    }
}
