/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.Clause;
import java.util.List;

public class Raw
implements Clause {
    private final ArgumentHolder[] args;
    private final String statement;

    public Raw(String string, ArgumentHolder[] arrargumentHolder) {
        this.statement = string;
        this.args = arrargumentHolder;
    }

    @Override
    public void appendSql(DatabaseType arrargumentHolder, String string, StringBuilder stringBuilder, List<ArgumentHolder> list) {
        stringBuilder.append(this.statement);
        stringBuilder.append(' ');
        arrargumentHolder = this.args;
        int n = arrargumentHolder.length;
        for (int i = 0; i < n; ++i) {
            list.add(arrargumentHolder[i]);
        }
    }
}
