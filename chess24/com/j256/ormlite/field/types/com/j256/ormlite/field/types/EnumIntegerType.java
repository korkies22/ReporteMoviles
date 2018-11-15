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

public class EnumIntegerType
extends BaseEnumType {
    private static final EnumIntegerType singleTon = new EnumIntegerType();

    private EnumIntegerType() {
        super(SqlType.INTEGER, new Class[0]);
    }

    protected EnumIntegerType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static EnumIntegerType getSingleton() {
        return singleTon;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return Integer.TYPE;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return ((Enum)object).ordinal();
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
            serializable.put(enum_.ordinal(), enum_);
        }
        return serializable;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) {
        return Integer.parseInt(string);
    }

    @Override
    public Object resultStringToJava(FieldType fieldType, String string, int n) throws SQLException {
        return this.sqlArgToJava(fieldType, Integer.parseInt(string), n);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getInt(n);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object object, int n) throws SQLException {
        if (fieldType == null) {
            return object;
        }
        object = (Integer)object;
        Map map = (Map)fieldType.getDataTypeConfigObj();
        if (map == null) {
            return EnumIntegerType.enumVal(fieldType, object, null, fieldType.getUnknownEnumVal());
        }
        return EnumIntegerType.enumVal(fieldType, object, (Enum)map.get(object), fieldType.getUnknownEnumVal());
    }
}
