/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import java.lang.reflect.Field;
import java.sql.SQLException;

public interface DataPersister
extends FieldConverter {
    public Object convertIdNumber(Number var1);

    public boolean dataIsEqual(Object var1, Object var2);

    public Object generateId();

    public String[] getAssociatedClassNames();

    public Class<?>[] getAssociatedClasses();

    public int getDefaultWidth();

    public Class<?> getPrimaryClass();

    public boolean isAppropriateId();

    public boolean isArgumentHolderRequired();

    public boolean isComparable();

    public boolean isEscapedDefaultValue();

    public boolean isEscapedValue();

    public boolean isPrimitive();

    public boolean isSelfGeneratedId();

    public boolean isValidForField(Field var1);

    public boolean isValidForVersion();

    public boolean isValidGeneratedType();

    public Object makeConfigObject(FieldType var1) throws SQLException;

    public Object moveToNextValue(Object var1);
}
