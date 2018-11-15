/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.sql.SQLException;

public interface ArgumentHolder {
    public String getColumnName();

    public FieldType getFieldType();

    public Object getSqlArgValue() throws SQLException;

    public SqlType getSqlType();

    public void setMetaInfo(FieldType var1);

    public void setMetaInfo(String var1);

    public void setMetaInfo(String var1, FieldType var2);

    public void setValue(Object var1);
}
