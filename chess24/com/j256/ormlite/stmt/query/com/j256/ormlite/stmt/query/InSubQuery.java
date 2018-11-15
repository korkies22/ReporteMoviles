/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.BaseComparison;
import java.sql.SQLException;
import java.util.List;

public class InSubQuery
extends BaseComparison {
    private final boolean in;
    private final QueryBuilder.InternalQueryBuilderWrapper subQueryBuilder;

    public InSubQuery(String string, FieldType fieldType, QueryBuilder.InternalQueryBuilderWrapper internalQueryBuilderWrapper, boolean bl) throws SQLException {
        super(string, fieldType, null, true);
        this.subQueryBuilder = internalQueryBuilderWrapper;
        this.in = bl;
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        if (this.in) {
            stringBuilder.append("IN ");
            return;
        }
        stringBuilder.append("NOT IN ");
    }

    @Override
    public void appendValue(DatabaseType arrfieldType, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        stringBuilder.append('(');
        this.subQueryBuilder.appendStatementString(stringBuilder, list);
        arrfieldType = this.subQueryBuilder.getResultFieldTypes();
        if (arrfieldType != null) {
            if (arrfieldType.length != 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("There must be only 1 result column in sub-query but we found ");
                stringBuilder.append(arrfieldType.length);
                throw new SQLException(stringBuilder.toString());
            }
            if (this.fieldType.getSqlType() != arrfieldType[0].getSqlType()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Outer column ");
                stringBuilder.append(this.fieldType);
                stringBuilder.append(" is not the same type as inner column ");
                stringBuilder.append(arrfieldType[0]);
                throw new SQLException(stringBuilder.toString());
            }
        }
        stringBuilder.append(") ");
    }
}
