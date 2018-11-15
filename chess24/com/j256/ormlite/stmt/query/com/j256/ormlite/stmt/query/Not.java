/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.Comparison;
import com.j256.ormlite.stmt.query.Exists;
import com.j256.ormlite.stmt.query.NeedsFutureClause;
import java.sql.SQLException;
import java.util.List;

public class Not
implements Clause,
NeedsFutureClause {
    private Comparison comparison = null;
    private Exists exists = null;

    public Not() {
    }

    public Not(Clause clause) {
        this.setMissingClause(clause);
    }

    @Override
    public void appendSql(DatabaseType databaseType, String string, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        if (this.comparison == null && this.exists == null) {
            throw new IllegalStateException("Clause has not been set in NOT operation");
        }
        if (this.comparison == null) {
            stringBuilder.append("(NOT ");
            this.exists.appendSql(databaseType, string, stringBuilder, list);
        } else {
            stringBuilder.append("(NOT ");
            if (string != null) {
                databaseType.appendEscapedEntityName(stringBuilder, string);
                stringBuilder.append('.');
            }
            databaseType.appendEscapedEntityName(stringBuilder, this.comparison.getColumnName());
            stringBuilder.append(' ');
            this.comparison.appendOperation(stringBuilder);
            this.comparison.appendValue(databaseType, stringBuilder, list);
        }
        stringBuilder.append(") ");
    }

    @Override
    public void setMissingClause(Clause clause) {
        if (this.comparison != null) {
            throw new IllegalArgumentException("NOT operation already has a comparison set");
        }
        if (clause instanceof Comparison) {
            this.comparison = (Comparison)clause;
            return;
        }
        if (clause instanceof Exists) {
            this.exists = (Exists)clause;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NOT operation can only work with comparison SQL clauses, not ");
        stringBuilder.append(clause);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String toString() {
        if (this.comparison == null) {
            return "NOT without comparison";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NOT comparison ");
        stringBuilder.append(this.comparison);
        return stringBuilder.toString();
    }
}
