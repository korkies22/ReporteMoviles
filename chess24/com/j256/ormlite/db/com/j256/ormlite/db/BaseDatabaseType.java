/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.db;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.BaseFieldConverter;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDatabaseType
implements DatabaseType {
    protected static String DEFAULT_SEQUENCE_SUFFIX = "_id_seq";
    protected Driver driver;

    private void addSingleUnique(StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(" UNIQUE (");
        this.appendEscapedEntityName(stringBuilder, fieldType.getColumnName());
        stringBuilder.append(")");
        list.add(stringBuilder.toString());
    }

    private void appendCanBeNull(StringBuilder stringBuilder, FieldType fieldType) {
    }

    private void appendDefaultValue(StringBuilder stringBuilder, FieldType fieldType, Object object) {
        if (fieldType.isEscapedDefaultValue()) {
            this.appendEscapedWord(stringBuilder, object.toString());
            return;
        }
        stringBuilder.append(object);
    }

    private void appendDoubleType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("DOUBLE PRECISION");
    }

    private void appendFloatType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("FLOAT");
    }

    private void appendIntegerType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("INTEGER");
    }

    @Override
    public void addPrimaryKeySql(FieldType[] arrfieldType, List<String> list, List<String> object, List<String> object2, List<String> object32) {
        object = null;
        for (FieldType fieldType : arrfieldType) {
            if (fieldType.isGeneratedId() && !this.generatedIdSqlAtEnd() && !fieldType.isSelfGeneratedId()) {
                object2 = object;
            } else {
                object2 = object;
                if (fieldType.isId()) {
                    if (object == null) {
                        object = new StringBuilder(48);
                        object.append("PRIMARY KEY (");
                    } else {
                        object.append(',');
                    }
                    this.appendEscapedEntityName((StringBuilder)object, fieldType.getColumnName());
                    object2 = object;
                }
            }
            object = object2;
        }
        if (object != null) {
            object.append(") ");
            list.add(object.toString());
        }
    }

    @Override
    public void addUniqueComboSql(FieldType[] arrfieldType, List<String> list, List<String> object, List<String> object2, List<String> object32) {
        object = null;
        for (FieldType fieldType : arrfieldType) {
            object2 = object;
            if (fieldType.isUniqueCombo()) {
                if (object == null) {
                    object = new StringBuilder(48);
                    object.append("UNIQUE (");
                } else {
                    object.append(',');
                }
                this.appendEscapedEntityName((StringBuilder)object, fieldType.getColumnName());
                object2 = object;
            }
            object = object2;
        }
        if (object != null) {
            object.append(") ");
            list.add(object.toString());
        }
    }

    protected void appendBigDecimalNumericType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("NUMERIC");
    }

    protected void appendBooleanType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("BOOLEAN");
    }

    protected void appendByteArrayType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("BLOB");
    }

    protected void appendByteType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("TINYINT");
    }

    protected void appendCharType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("CHAR");
    }

    @Override
    public void appendColumnArg(String object, StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws SQLException {
        int n;
        this.appendEscapedEntityName(stringBuilder, fieldType.getColumnName());
        stringBuilder.append(' ');
        DataPersister dataPersister = fieldType.getDataPersister();
        int n2 = n = fieldType.getWidth();
        if (n == 0) {
            n2 = dataPersister.getDefaultWidth();
        }
        switch (.$SwitchMap$com$j256$ormlite$field$SqlType[dataPersister.getSqlType().ordinal()]) {
            default: {
                object = new StringBuilder();
                object.append("Unknown SQL-type ");
                object.append((Object)dataPersister.getSqlType());
                throw new IllegalArgumentException(object.toString());
            }
            case 14: {
                this.appendBigDecimalNumericType(stringBuilder, fieldType, n2);
                break;
            }
            case 13: {
                this.appendSerializableType(stringBuilder, fieldType, n2);
                break;
            }
            case 12: {
                this.appendDoubleType(stringBuilder, fieldType, n2);
                break;
            }
            case 11: {
                this.appendFloatType(stringBuilder, fieldType, n2);
                break;
            }
            case 10: {
                this.appendLongType(stringBuilder, fieldType, n2);
                break;
            }
            case 9: {
                this.appendIntegerType(stringBuilder, fieldType, n2);
                break;
            }
            case 8: {
                this.appendShortType(stringBuilder, fieldType, n2);
                break;
            }
            case 7: {
                this.appendByteArrayType(stringBuilder, fieldType, n2);
                break;
            }
            case 6: {
                this.appendByteType(stringBuilder, fieldType, n2);
                break;
            }
            case 5: {
                this.appendCharType(stringBuilder, fieldType, n2);
                break;
            }
            case 4: {
                this.appendDateType(stringBuilder, fieldType, n2);
                break;
            }
            case 3: {
                this.appendBooleanType(stringBuilder, fieldType, n2);
                break;
            }
            case 2: {
                this.appendLongStringType(stringBuilder, fieldType, n2);
                break;
            }
            case 1: {
                this.appendStringType(stringBuilder, fieldType, n2);
            }
        }
        stringBuilder.append(' ');
        if (fieldType.isGeneratedIdSequence() && !fieldType.isSelfGeneratedId()) {
            this.configureGeneratedIdSequence(stringBuilder, fieldType, list2, list, list4);
        } else if (fieldType.isGeneratedId() && !fieldType.isSelfGeneratedId()) {
            this.configureGeneratedId((String)object, stringBuilder, fieldType, list2, list3, list, list4);
        } else if (fieldType.isId()) {
            this.configureId(stringBuilder, fieldType, list2, list, list4);
        }
        if (!fieldType.isGeneratedId()) {
            object = fieldType.getDefaultValue();
            if (object != null) {
                stringBuilder.append("DEFAULT ");
                this.appendDefaultValue(stringBuilder, fieldType, object);
                stringBuilder.append(' ');
            }
            if (fieldType.isCanBeNull()) {
                this.appendCanBeNull(stringBuilder, fieldType);
            } else {
                stringBuilder.append("NOT NULL ");
            }
            if (fieldType.isUnique()) {
                this.addSingleUnique(stringBuilder, fieldType, list, list3);
            }
        }
    }

    @Override
    public void appendCreateTableSuffix(StringBuilder stringBuilder) {
    }

    protected void appendDateType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("TIMESTAMP");
    }

    @Override
    public void appendEscapedEntityName(StringBuilder stringBuilder, String string) {
        stringBuilder.append('`');
        stringBuilder.append(string);
        stringBuilder.append('`');
    }

    @Override
    public void appendEscapedWord(StringBuilder stringBuilder, String string) {
        stringBuilder.append('\'');
        stringBuilder.append(string);
        stringBuilder.append('\'');
    }

    @Override
    public void appendInsertNoColumns(StringBuilder stringBuilder) {
        stringBuilder.append("() VALUES ()");
    }

    @Override
    public void appendLimitValue(StringBuilder stringBuilder, long l, Long l2) {
        stringBuilder.append("LIMIT ");
        stringBuilder.append(l);
        stringBuilder.append(' ');
    }

    protected void appendLongStringType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("TEXT");
    }

    protected void appendLongType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("BIGINT");
    }

    @Override
    public void appendOffsetValue(StringBuilder stringBuilder, long l) {
        stringBuilder.append("OFFSET ");
        stringBuilder.append(l);
        stringBuilder.append(' ');
    }

    @Override
    public void appendSelectNextValFromSequence(StringBuilder stringBuilder, String string) {
    }

    protected void appendSerializableType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("BLOB");
    }

    protected void appendShortType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        stringBuilder.append("SMALLINT");
    }

    protected void appendStringType(StringBuilder stringBuilder, FieldType fieldType, int n) {
        if (this.isVarcharFieldWidthSupported()) {
            stringBuilder.append("VARCHAR(");
            stringBuilder.append(n);
            stringBuilder.append(")");
            return;
        }
        stringBuilder.append("VARCHAR");
    }

    protected void configureGeneratedId(String charSequence, StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        charSequence = new StringBuilder();
        charSequence.append("GeneratedId is not supported by database ");
        charSequence.append(this.getDatabaseName());
        charSequence.append(" for field ");
        charSequence.append(fieldType);
        throw new IllegalStateException(charSequence.toString());
    }

    protected void configureGeneratedIdSequence(StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2, List<String> list3) throws SQLException {
        stringBuilder = new StringBuilder();
        stringBuilder.append("GeneratedIdSequence is not supported by database ");
        stringBuilder.append(this.getDatabaseName());
        stringBuilder.append(" for field ");
        stringBuilder.append(fieldType);
        throw new SQLException(stringBuilder.toString());
    }

    protected void configureId(StringBuilder stringBuilder, FieldType fieldType, List<String> list, List<String> list2, List<String> list3) {
    }

    @Override
    public void dropColumnArg(FieldType fieldType, List<String> list, List<String> list2) {
    }

    @Override
    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return null;
    }

    @Override
    public String generateIdSequenceName(String string, FieldType object) {
        object = new StringBuilder();
        object.append(string);
        object.append(DEFAULT_SEQUENCE_SUFFIX);
        string = object.toString();
        if (this.isEntityNamesMustBeUpCase()) {
            return string.toUpperCase();
        }
        return string;
    }

    protected boolean generatedIdSqlAtEnd() {
        return true;
    }

    @Override
    public String getCommentLinePrefix() {
        return "-- ";
    }

    protected abstract String getDriverClassName();

    @Override
    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        return dataPersister;
    }

    @Override
    public String getPingStatement() {
        return "SELECT 1";
    }

    @Override
    public boolean isAllowGeneratedIdInsertSupported() {
        return true;
    }

    @Override
    public boolean isBatchUseTransaction() {
        return false;
    }

    @Override
    public boolean isCreateIfNotExistsSupported() {
        return false;
    }

    @Override
    public boolean isCreateIndexIfNotExistsSupported() {
        return this.isCreateIfNotExistsSupported();
    }

    @Override
    public boolean isCreateTableReturnsNegative() {
        return false;
    }

    @Override
    public boolean isCreateTableReturnsZero() {
        return true;
    }

    @Override
    public boolean isEntityNamesMustBeUpCase() {
        return false;
    }

    @Override
    public boolean isIdSequenceNeeded() {
        return false;
    }

    @Override
    public boolean isLimitAfterSelect() {
        return false;
    }

    @Override
    public boolean isLimitSqlSupported() {
        return true;
    }

    @Override
    public boolean isNestedSavePointsSupported() {
        return true;
    }

    @Override
    public boolean isOffsetLimitArgument() {
        return false;
    }

    @Override
    public boolean isOffsetSqlSupported() {
        return true;
    }

    @Override
    public boolean isSelectSequenceBeforeInsert() {
        return false;
    }

    @Override
    public boolean isTruncateSupported() {
        return false;
    }

    @Override
    public boolean isVarcharFieldWidthSupported() {
        return true;
    }

    @Override
    public void loadDriver() throws SQLException {
        String string = this.getDriverClassName();
        if (string != null) {
            try {
                Class.forName(string);
                return;
            }
            catch (ClassNotFoundException classNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Driver class was not found for ");
                stringBuilder.append(this.getDatabaseName());
                stringBuilder.append(" database.  Missing jar with class ");
                stringBuilder.append(string);
                stringBuilder.append(".");
                throw SqlExceptionUtil.create(stringBuilder.toString(), classNotFoundException);
            }
        }
    }

    @Override
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    protected static class BooleanNumberFieldConverter
    extends BaseFieldConverter {
        protected BooleanNumberFieldConverter() {
        }

        @Override
        public SqlType getSqlType() {
            return SqlType.BOOLEAN;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Object javaToSqlArg(FieldType fieldType, Object object) {
            byte by;
            if (((Boolean)object).booleanValue()) {
                by = 1;
                do {
                    return by;
                    break;
                } while (true);
            }
            by = 0;
            return by;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Object parseDefaultString(FieldType fieldType, String string) {
            byte by;
            if (Boolean.parseBoolean(string)) {
                by = 1;
                do {
                    return by;
                    break;
                } while (true);
            }
            by = 0;
            return by;
        }

        @Override
        public Object resultStringToJava(FieldType fieldType, String string, int n) {
            return this.sqlArgToJava(fieldType, Byte.parseByte(string), n);
        }

        @Override
        public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int n) throws SQLException {
            return databaseResults.getByte(n);
        }

        @Override
        public Object sqlArgToJava(FieldType fieldType, Object object, int n) {
            if ((Byte)object == 1) {
                return true;
            }
            return false;
        }
    }

}
