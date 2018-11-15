/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public interface FieldConverter {
    public SqlType getSqlType();

    public boolean isStreamType();

    public Object javaToSqlArg(FieldType var1, Object var2) throws SQLException;

    public Object parseDefaultString(FieldType var1, String var2) throws SQLException;

    public Object resultStringToJava(FieldType var1, String var2, int var3) throws SQLException;

    public Object resultToJava(FieldType var1, DatabaseResults var2, int var3) throws SQLException;

    public Object resultToSqlArg(FieldType var1, DatabaseResults var2, int var3) throws SQLException;

    public Object sqlArgToJava(FieldType var1, Object var2, int var3) throws SQLException;
}
