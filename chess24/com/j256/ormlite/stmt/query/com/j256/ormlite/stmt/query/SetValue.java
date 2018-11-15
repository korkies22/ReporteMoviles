/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.NullArgHolder;
import com.j256.ormlite.stmt.query.BaseComparison;
import java.sql.SQLException;
import java.util.List;

public class SetValue
extends BaseComparison {
    private static final ArgumentHolder nullValue = new NullArgHolder();

    public SetValue(String string, FieldType fieldType, Object object) throws SQLException {
        Object object2 = object;
        if (object == null) {
            object2 = nullValue;
        }
        super(string, fieldType, object2, false);
    }

    @Override
    public void appendOperation(StringBuilder stringBuilder) {
        stringBuilder.append("= ");
    }
}
