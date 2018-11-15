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

public class SetExpression
extends BaseComparison {
    public SetExpression(String string, FieldType fieldType, String string2) throws SQLException {
        super(string, fieldType, string2, true);
    }

    @Override
    protected void appendArgOrValue(DatabaseType databaseType, FieldType fieldType, StringBuilder stringBuilder, List<ArgumentHolder> list, Object object) {
        stringBuilder.append(object);
        stringBuilder.append(' ');
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        stringBuilder.append("= ");
    }
}
