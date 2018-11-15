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

public class IsNull
extends BaseComparison {
    public IsNull(String string, FieldType fieldType) throws SQLException {
        super(string, fieldType, null, true);
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        stringBuilder.append("IS NULL ");
    }

    @Override
    public void appendValue(DatabaseType databaseType, StringBuilder stringBuilder, List<ArgumentHolder> list) {
    }
}
