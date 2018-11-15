/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.sql.SQLException;

public abstract class BaseArgumentHolder
implements ArgumentHolder {
    private String columnName = null;
    private FieldType fieldType = null;
    private SqlType sqlType = null;

    public BaseArgumentHolder() {
    }

    public BaseArgumentHolder(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public BaseArgumentHolder(String string) {
        this.columnName = string;
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public FieldType getFieldType() {
        return this.fieldType;
    }

    @Override
    public Object getSqlArgValue() throws SQLException {
        if (!this.isValueSet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Column value has not been set for ");
            stringBuilder.append(this.columnName);
            throw new SQLException(stringBuilder.toString());
        }
        Object object = this.getValue();
        if (object == null) {
            return null;
        }
        if (this.fieldType == null) {
            return object;
        }
        if (this.fieldType.isForeign() && this.fieldType.getType() == object.getClass()) {
            return this.fieldType.getForeignIdField().extractJavaFieldValue(object);
        }
        return this.fieldType.convertJavaFieldToSqlArgValue(object);
    }

    @Override
    public SqlType getSqlType() {
        return this.sqlType;
    }

    protected abstract Object getValue();

    protected abstract boolean isValueSet();

    @Override
    public void setMetaInfo(FieldType fieldType) {
        if (this.fieldType == null || this.fieldType == fieldType) {
            this.fieldType = fieldType;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldType name cannot be set twice from ");
        stringBuilder.append(this.fieldType);
        stringBuilder.append(" to ");
        stringBuilder.append(fieldType);
        stringBuilder.append(".  Using a SelectArg twice in query with different columns?");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void setMetaInfo(String string) {
        if (this.columnName == null || this.columnName.equals(string)) {
            this.columnName = string;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Column name cannot be set twice from ");
        stringBuilder.append(this.columnName);
        stringBuilder.append(" to ");
        stringBuilder.append(string);
        stringBuilder.append(".  Using a SelectArg twice in query with different columns?");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void setMetaInfo(String string, FieldType fieldType) {
        this.setMetaInfo(string);
        this.setMetaInfo(fieldType);
    }

    @Override
    public abstract void setValue(Object var1);

    public String toString() {
        Object object;
        block4 : {
            if (!this.isValueSet()) {
                return "[unset]";
            }
            try {
                object = this.getSqlArgValue();
                if (object != null) break block4;
                return "[null]";
            }
            catch (SQLException sQLException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[could not get value: ");
                stringBuilder.append(sQLException);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }
        object = object.toString();
        return object;
    }
}
