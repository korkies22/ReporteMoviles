/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class CharacterObjectType
extends BaseDataType {
    private static final CharacterObjectType singleTon = new CharacterObjectType();

    private CharacterObjectType() {
        super(SqlType.CHAR, new Class[]{Character.class});
    }

    protected CharacterObjectType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static CharacterObjectType getSingleton() {
        return singleTon;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        if (string.length() != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with field ");
            stringBuilder.append(fieldType);
            stringBuilder.append(", default string to long for Character: '");
            stringBuilder.append(string);
            stringBuilder.append("'");
            throw new SQLException(stringBuilder.toString());
        }
        return Character.valueOf(string.charAt(0));
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return Character.valueOf(databaseResults.getChar(n));
    }
}
