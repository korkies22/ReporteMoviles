/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class RawRowMapperImpl<T, ID>
implements RawRowMapper<T> {
    private final TableInfo<T, ID> tableInfo;

    public RawRowMapperImpl(TableInfo<T, ID> tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public T mapRow(String[] arrstring, String[] arrstring2) throws SQLException {
        T t = this.tableInfo.createObject();
        for (int i = 0; i < arrstring.length; ++i) {
            if (i >= arrstring2.length) continue;
            FieldType fieldType = this.tableInfo.getFieldTypeByColumnName(arrstring[i]);
            fieldType.assignField(t, fieldType.convertStringToJavaField(arrstring2[i], i), false, null);
        }
        return t;
    }
}
