/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.stmt.StatementBuilder;

public static enum StatementBuilder.StatementType {
    SELECT(true, true, false, false),
    SELECT_LONG(true, true, false, false),
    SELECT_RAW(true, true, false, false),
    UPDATE(true, false, true, false),
    DELETE(true, false, true, false),
    EXECUTE(false, false, false, true);
    
    private final boolean okForExecute;
    private final boolean okForQuery;
    private final boolean okForStatementBuilder;
    private final boolean okForUpdate;

    private StatementBuilder.StatementType(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.okForStatementBuilder = bl;
        this.okForQuery = bl2;
        this.okForUpdate = bl3;
        this.okForExecute = bl4;
    }

    public boolean isOkForExecute() {
        return this.okForExecute;
    }

    public boolean isOkForQuery() {
        return this.okForQuery;
    }

    public boolean isOkForStatementBuilder() {
        return this.okForStatementBuilder;
    }

    public boolean isOkForUpdate() {
        return this.okForUpdate;
    }
}
