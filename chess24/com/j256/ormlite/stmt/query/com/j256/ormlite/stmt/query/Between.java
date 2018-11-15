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

public class Between
extends BaseComparison {
    private Object high;
    private Object low;

    public Between(String string, FieldType fieldType, Object object, Object object2) throws SQLException {
        super(string, fieldType, null, true);
        this.low = object;
        this.high = object2;
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        stringBuilder.append("BETWEEN ");
    }

    @Override
    public void appendValue(DatabaseType object, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        if (this.low == null) {
            object = new StringBuilder();
            object.append("BETWEEN low value for '");
            object.append(this.columnName);
            object.append("' is null");
            throw new IllegalArgumentException(object.toString());
        }
        if (this.high == null) {
            object = new StringBuilder();
            object.append("BETWEEN high value for '");
            object.append(this.columnName);
            object.append("' is null");
            throw new IllegalArgumentException(object.toString());
        }
        this.appendArgOrValue((DatabaseType)object, this.fieldType, stringBuilder, list, this.low);
        stringBuilder.append("AND ");
        this.appendArgOrValue((DatabaseType)object, this.fieldType, stringBuilder, list, this.high);
    }
}
