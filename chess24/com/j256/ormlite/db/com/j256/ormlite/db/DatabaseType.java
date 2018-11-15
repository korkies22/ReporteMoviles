/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.db;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseType {
    public void addPrimaryKeySql(FieldType[] var1, List<String> var2, List<String> var3, List<String> var4, List<String> var5) throws SQLException;

    public void addUniqueComboSql(FieldType[] var1, List<String> var2, List<String> var3, List<String> var4, List<String> var5) throws SQLException;

    public void appendColumnArg(String var1, StringBuilder var2, FieldType var3, List<String> var4, List<String> var5, List<String> var6, List<String> var7) throws SQLException;

    public void appendCreateTableSuffix(StringBuilder var1);

    public void appendEscapedEntityName(StringBuilder var1, String var2);

    public void appendEscapedWord(StringBuilder var1, String var2);

    public void appendInsertNoColumns(StringBuilder var1);

    public void appendLimitValue(StringBuilder var1, long var2, Long var4);

    public void appendOffsetValue(StringBuilder var1, long var2);

    public void appendSelectNextValFromSequence(StringBuilder var1, String var2);

    public void dropColumnArg(FieldType var1, List<String> var2, List<String> var3);

    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource var1, Class<T> var2) throws SQLException;

    public String generateIdSequenceName(String var1, FieldType var2);

    public String getCommentLinePrefix();

    public String getDatabaseName();

    public FieldConverter getFieldConverter(DataPersister var1);

    public String getPingStatement();

    public boolean isAllowGeneratedIdInsertSupported();

    public boolean isBatchUseTransaction();

    public boolean isCreateIfNotExistsSupported();

    public boolean isCreateIndexIfNotExistsSupported();

    public boolean isCreateTableReturnsNegative();

    public boolean isCreateTableReturnsZero();

    public boolean isDatabaseUrlThisType(String var1, String var2);

    public boolean isEntityNamesMustBeUpCase();

    public boolean isIdSequenceNeeded();

    public boolean isLimitAfterSelect();

    public boolean isLimitSqlSupported();

    public boolean isNestedSavePointsSupported();

    public boolean isOffsetLimitArgument();

    public boolean isOffsetSqlSupported();

    public boolean isSelectSequenceBeforeInsert();

    public boolean isTruncateSupported();

    public boolean isVarcharFieldWidthSupported();

    public void loadDriver() throws SQLException;

    public void setDriver(Driver var1);
}
