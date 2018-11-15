/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import java.lang.reflect.Field;
import java.sql.SQLException;

public abstract class BaseEnumType
extends BaseDataType {
    protected BaseEnumType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    protected static Enum<?> enumVal(FieldType fieldType, Object object, Enum<?> object2, Enum<?> enum_) throws SQLException {
        if (object2 != null) {
            return object2;
        }
        if (enum_ == null) {
            object2 = new StringBuilder();
            object2.append("Cannot get enum value of '");
            object2.append(object);
            object2.append("' for field ");
            object2.append(fieldType);
            throw new SQLException(object2.toString());
        }
        return enum_;
    }

    @Override
    public boolean isValidForField(Field field) {
        return field.getType().isEnum();
    }
}
