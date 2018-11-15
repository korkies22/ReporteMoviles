/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.BaseComparison;
import java.sql.SQLException;
import java.util.List;

public class SimpleComparison
extends BaseComparison {
    public static final String EQUAL_TO_OPERATION = "=";
    public static final String GREATER_THAN_EQUAL_TO_OPERATION = ">=";
    public static final String GREATER_THAN_OPERATION = ">";
    public static final String LESS_THAN_EQUAL_TO_OPERATION = "<=";
    public static final String LESS_THAN_OPERATION = "<";
    public static final String LIKE_OPERATION = "LIKE";
    public static final String NOT_EQUAL_TO_OPERATION = "<>";
    private final String operation;

    public SimpleComparison(String string, FieldType fieldType, Object object, String string2) throws SQLException {
        super(string, fieldType, object, true);
        this.operation = string2;
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        stringBuilder.append(this.operation);
        stringBuilder.append(' ');
    }
}
