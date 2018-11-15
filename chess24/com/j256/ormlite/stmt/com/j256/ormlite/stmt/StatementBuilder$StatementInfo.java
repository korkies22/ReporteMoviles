/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.StatementBuilder;
import java.util.List;

public static class StatementBuilder.StatementInfo {
    private final List<ArgumentHolder> argList;
    private final String statement;

    private StatementBuilder.StatementInfo(String string, List<ArgumentHolder> list) {
        this.argList = list;
        this.statement = string;
    }

    public List<ArgumentHolder> getArgList() {
        return this.argList;
    }

    public String getStatement() {
        return this.statement;
    }
}
