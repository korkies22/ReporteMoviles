/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseEnumType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnumStringType
extends BaseEnumType {
    public static int DEFAULT_WIDTH = 100;
    private static final EnumStringType singleTon = new EnumStringType();

    private EnumStringType() {
        super(SqlType.STRING, new Class[]{Enum.class});
    }

    protected EnumStringType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static EnumStringType getSingleton() {
        return singleTon;
    }

    @Override
    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return ((Enum)object).name();
    }

    @Override
    public Object makeConfigObject(FieldType object2) throws SQLException {
        Serializable serializable = new HashMap();
        Enum[] arrenum = (Enum[])object2.getType().getEnumConstants();
        if (arrenum == null) {
            serializable = new StringBuilder();
            serializable.append("Field ");
            serializable.append(object2);
            serializable.append(" improperly configured as type ");
            serializable.append(this);
            throw new SQLException(serializable.toString());
        }
        for (Enum enum_ : arrenum) {
            serializable.put(enum_.name(), enum_);
        }
        return serializable;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return string;
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        return this.sqlArgToJava(fieldType, string, n);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getString(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) throws SQLException {
        if (fieldType == null) {
            return object;
        }
        object = (String)object;
        Map map = (Map)fieldType.getDataTypeConfigObj();
        if (map == null) {
            return EnumStringType.enumVal(fieldType, object, null, fieldType.getUnknownEnumVal());
        }
        return EnumStringType.enumVal(fieldType, object, (Enum)map.get(object), fieldType.getUnknownEnumVal());
    }
}
