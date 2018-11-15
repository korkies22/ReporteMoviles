/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.db;

import com.j256.ormlite.db.BaseDatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BigDecimalStringType;
import java.util.List;

public abstract class BaseSqliteDatabaseType
extends BaseDatabaseType {
    private static final FieldConverter booleanConverter = new BaseDatabaseType.BooleanNumberFieldConverter();

    @Override
    public void appendInsertNoColumns(StringBuilder stringBuilder) {
        stringBuilder.append("DEFAULT VALUES");
    }

    @Override
    protected void appendLongType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        if (fieldType.getSqlType() == SqlType.LONG && fieldType.isGeneratedId()) {
            stringBuilder.append("INTEGER");
            return;
        }
        stringBuilder.append("BIGINT");
    }

    @Override
    protected void configureGeneratedId(String string, StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        if (fieldType.getSqlType() != SqlType.INTEGER && fieldType.getSqlType() != SqlType.LONG) {
            throw new IllegalArgumentException("Sqlite requires that auto-increment generated-id be integer or long type");
        }
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT ");
    }

    @Override
    protected boolean generatedIdSqlAtEnd() {
        return false;
    }

    @Override
    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        switch (.$SwitchMap$com$j256$ormlite$field$SqlType[dataPersister.getSqlType().ordinal()]) {
            default: {
                return super.getFieldConverter(dataPersister);
            }
            case 2: {
                return BigDecimalStringType.getSingleton();
            }
            case 1: 
        }
        return booleanConverter;
    }

    @Override
    public boolean isCreateIfNotExistsSupported() {
        return true;
    }

    @Override
    public boolean isCreateTableReturnsZero() {
        return false;
    }

    @Override
    public boolean isVarcharFieldWidthSupported() {
        return false;
    }

}
