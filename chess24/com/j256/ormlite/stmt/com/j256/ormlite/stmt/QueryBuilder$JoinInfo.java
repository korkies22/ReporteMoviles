/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;

private class QueryBuilder.JoinInfo {
    FieldType localField;
    StatementBuilder.WhereOperation operation;
    final QueryBuilder<?, ?> queryBuilder;
    FieldType remoteField;
    final String type;

    public QueryBuilder.JoinInfo(String string, QueryBuilder<?, ?> queryBuilder2, StatementBuilder.WhereOperation whereOperation) {
        this.type = string;
        this.queryBuilder = queryBuilder2;
        this.operation = whereOperation;
    }
}
