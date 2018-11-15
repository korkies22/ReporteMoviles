/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.stmt.StatementBuilder;

protected static enum StatementBuilder.WhereOperation {
    FIRST("WHERE ", null),
    AND("AND (", ") "),
    OR("OR (", ") ");
    
    private final String after;
    private final String before;

    private StatementBuilder.WhereOperation(String string2, String string3) {
        this.before = string2;
        this.after = string3;
    }

    public void appendAfter(StringBuilder stringBuilder) {
        if (this.after != null) {
            stringBuilder.append(this.after);
        }
    }

    public void appendBefore(StringBuilder stringBuilder) {
        if (this.before != null) {
            stringBuilder.append(this.before);
        }
    }
}
