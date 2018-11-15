/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public abstract class BaseFieldConverter
implements FieldConverter {
    @Override
    public boolean isStreamType() {
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        return object;
    }

    @Override
    public Object resultToJava(FieldType fieldType, DatabaseResults object, int n) throws SQLException {
        if ((object = this.resultToSqlArg(fieldType, (DatabaseResults)object, n)) == null) {
            return null;
        }
        return this.sqlArgToJava(fieldType, object, n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) throws SQLException {
        return object;
    }
}
