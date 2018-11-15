/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

public class OrderBy {
    private final boolean ascending;
    private final String columnName;

    public OrderBy(String string, boolean bl) {
        this.columnName = string;
        this.ascending = bl;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public boolean isAscending() {
        return this.ascending;
    }
}
