/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.BaseComparison;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class In
extends BaseComparison {
    private final boolean in;
    private Iterable<?> objects;

    public In(String string, FieldType fieldType, Iterable<?> iterable, boolean bl) throws SQLException {
        super(string, fieldType, null, true);
        this.objects = iterable;
        this.in = bl;
    }

    public In(String string, FieldType fieldType, Object[] arrobject, boolean bl) throws SQLException {
        super(string, fieldType, null, true);
        this.objects = Arrays.asList(arrobject);
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
    public void appendValue(DatabaseType object, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        stringBuilder.append('(');
        Iterator<?> iterator = this.objects.iterator();
        boolean bl = true;
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj == null) {
                object = new StringBuilder();
                object.append("one of the IN values for '");
                object.append(this.columnName);
                object.append("' is null");
                throw new IllegalArgumentException(object.toString());
            }
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(',');
            }
            super.appendArgOrValue((DatabaseType)object, this.fieldType, stringBuilder, list, obj);
        }
        stringBuilder.append(") ");
    }
}
