/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.Clause;
import java.sql.SQLException;
import java.util.List;

public class Exists
implements Clause {
    private final QueryBuilder.InternalQueryBuilderWrapper subQueryBuilder;

    public Exists(QueryBuilder.InternalQueryBuilderWrapper internalQueryBuilderWrapper) {
        this.subQueryBuilder = internalQueryBuilderWrapper;
    }

    @Override
    public void appendSql(DatabaseType databaseType, String string, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        stringBuilder.append("EXISTS (");
        this.subQueryBuilder.appendStatementString(stringBuilder, list);
        stringBuilder.append(") ");
    }
}
