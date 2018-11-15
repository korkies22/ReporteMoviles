/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.NeedsFutureClause;
import java.sql.SQLException;
import java.util.List;

public class ManyClause
implements Clause,
NeedsFutureClause {
    public static final String AND_OPERATION = "AND";
    public static final String OR_OPERATION = "OR";
    private final Clause first;
    private final String operation;
    private final Clause[] others;
    private Clause second;
    private final int startOthersAt;

    public ManyClause(Clause clause, Clause clause2, Clause[] arrclause, String string) {
        this.first = clause;
        this.second = clause2;
        this.others = arrclause;
        this.startOthersAt = 0;
        this.operation = string;
    }

    public ManyClause(Clause clause, String string) {
        this.first = clause;
        this.second = null;
        this.others = null;
        this.startOthersAt = 0;
        this.operation = string;
    }

    public ManyClause(Clause[] arrclause, String string) {
        this.first = arrclause[0];
        if (arrclause.length < 2) {
            this.second = null;
            this.startOthersAt = arrclause.length;
        } else {
            this.second = arrclause[1];
            this.startOthersAt = 2;
        }
        this.others = arrclause;
        this.operation = string;
    }

    @Override
    public void appendSql(DatabaseType databaseType, String string, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        stringBuilder.append("(");
        this.first.appendSql(databaseType, string, stringBuilder, list);
        if (this.second != null) {
            stringBuilder.append(this.operation);
            stringBuilder.append(' ');
            this.second.appendSql(databaseType, string, stringBuilder, list);
        }
        if (this.others != null) {
            for (int i = this.startOthersAt; i < this.others.length; ++i) {
                stringBuilder.append(this.operation);
                stringBuilder.append(' ');
                this.others[i].appendSql(databaseType, string, stringBuilder, list);
            }
        }
        stringBuilder.append(") ");
    }

    @Override
    public void setMissingClause(Clause clause) {
        this.second = clause;
    }
}
