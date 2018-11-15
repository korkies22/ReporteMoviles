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

public class BigDecimalNumericType
extends BaseDataType {
    private static final BigDecimalNumericType singleTon = new BigDecimalNumericType();

    private BigDecimalNumericType() {
        super(SqlType.BIG_DECIMAL, new Class[0]);
    }

    protected BigDecimalNumericType(SqlType sqlType, Class<?>[] arrclass) {
        super(sqlType, arrclass);
    }

    public static BigDecimalNumericType getSingleton() {
        return singleTon;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return BigDecimal.class;
    }

    @Override
    public boolean isAppropriateId() {
        return false;
    }

    @Override
    public boolean isEscapedValue() {
        return false;
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
        return databaseResults.getBigDecimal(n);
    }
}
