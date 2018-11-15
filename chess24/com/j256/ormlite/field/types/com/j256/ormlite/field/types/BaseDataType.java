/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.BaseFieldConverter;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.lang.reflect.Field;
import java.sql.SQLException;

public abstract class BaseDataType
extends BaseFieldConverter
implements DataPersister {
    private final Class<?>[] classes;
    private final SqlType sqlType;

    public BaseDataType(SqlType sqlType, Class<?>[] arrclass) {
        this.sqlType = sqlType;
        this.classes = arrclass;
    }

    @Override
    public Object convertIdNumber(Number number) {
        return null;
    }

    @Override
    public boolean dataIsEqual(Object object, Object object2) {
        boolean bl = false;
        if (object == null) {
            if (object2 == null) {
                bl = true;
            }
            return bl;
        }
        if (object2 == null) {
            return false;
        }
        return object.equals(object2);
    }

    @Override
    public Object generateId() {
        throw new IllegalStateException("Should not have tried to generate this type");
    }

    @Override
    public String[] getAssociatedClassNames() {
        return null;
    }

    @Override
    public Class<?>[] getAssociatedClasses() {
        return this.classes;
    }

    @Override
    public int getDefaultWidth() {
        return 0;
    }

    @Override
    public Class<?> getPrimaryClass() {
        if (this.classes.length == 0) {
            return null;
        }
        return this.classes[0];
    }

    @Override
    public SqlType getSqlType() {
        return this.sqlType;
    }

    @Override
    public boolean isAppropriateId() {
        return true;
    }

    @Override
    public boolean isArgumentHolderRequired() {
        return false;
    }

    @Override
    public boolean isComparable() {
        return true;
    }

    @Override
    public boolean isEscapedDefaultValue() {
        return this.isEscapedValue();
    }

    @Override
    public boolean isEscapedValue() {
        return true;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isSelfGeneratedId() {
        return false;
    }

    @Override
    public boolean isValidForField(Field field) {
        if (this.classes.length == 0) {
            return true;
        }
        Class<?>[] arrclass = this.classes;
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            if (!arrclass[i].isAssignableFrom(field.getType())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidForVersion() {
        return false;
    }

    @Override
    public boolean isValidGeneratedType() {
        return false;
    }

    @Override
    public Object makeConfigObject(FieldType fieldType) throws SQLException {
        return null;
    }

    @Override
    public Object moveToNextValue(Object object) {
        return null;
    }

    @Override
    public abstract Object parseDefaultString(FieldType var1, String var2) throws SQLException;

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        return this.parseDefaultString(fieldType, string);
    }

    @Override
    public abstract Object resultToSqlArg(FieldType var1, DatabaseResults var2, int var3) throws SQLException;
}
