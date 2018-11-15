/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BigDecimalStringType
extends BaseDataType {
    public static int DEFAULT_WIDTH = 255;
    private static final BigDecimalStringType singleTon = new BigDecimalStringType();

    private BigDecimalStringType() {
        super(SqlType.STRING, new Class[]{BigDecimal.class});
    }

    protected BigDecimalStringType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static BigDecimalStringType getSingleton() {
        return singleTon;
    }

    @Override
    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) {
        return ((BigDecimal)object).toString();
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String string) throws SQLException {
        try {
            BigDecimal bigDecimal = new BigDecimal(string);
            return bigDecimal;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with field ");
            stringBuilder.append(fieldType);
            stringBuilder.append(" parsing default BigDecimal string '");
            stringBuilder.append(string);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), illegalArgumentException);
        }
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
        return databaseResults.getString(n);
    }

    @Override
    public Object sqlArgToJava(FieldType object, Object object2, int n) throws SQLException {
        try {
            object = new BigDecimal((String)object2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Problems with column ");
            stringBuilder.append(n);
            stringBuilder.append(" parsing BigDecimal string '");
            stringBuilder.append(object2);
            stringBuilder.append("'");
            throw SqlExceptionUtil.create(stringBuilder.toString(), illegalArgumentException);
        }
    }
}
