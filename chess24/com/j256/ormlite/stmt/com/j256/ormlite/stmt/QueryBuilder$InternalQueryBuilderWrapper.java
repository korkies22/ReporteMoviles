/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public static class QueryBuilder.InternalQueryBuilderWrapper {
    private final QueryBuilder<?, ?> queryBuilder;

    QueryBuilder.InternalQueryBuilderWrapper(QueryBuilder<?, ?> queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public void appendStatementString(StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        this.queryBuilder.appendStatementString(stringBuilder, list);
    }

    public FieldType[] getResultFieldTypes() {
        return this.queryBuilder.getResultFieldTypes();
    }
}
