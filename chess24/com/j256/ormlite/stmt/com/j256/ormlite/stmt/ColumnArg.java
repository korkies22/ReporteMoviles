/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

public class ColumnArg {
    private final String columnName;
    private final String tableName;

    public ColumnArg(String string) {
        this.tableName = null;
        this.columnName = string;
    }

    public ColumnArg(String string, String string2) {
        this.tableName = string;
        this.columnName = string2;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getTableName() {
        return this.tableName;
    }
}
