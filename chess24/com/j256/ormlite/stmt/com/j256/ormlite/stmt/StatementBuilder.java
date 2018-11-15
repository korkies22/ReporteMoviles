/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.table.TableInfo;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class StatementBuilder<T, ID> {
    private static Logger logger = LoggerFactory.getLogger(StatementBuilder.class);
    protected boolean addTableName;
    protected final Dao<T, ID> dao;
    protected final DatabaseType databaseType;
    protected final TableInfo<T, ID> tableInfo;
    protected final String tableName;
    protected StatementType type;
    protected Where<T, ID> where = null;

    public StatementBuilder(DatabaseType object, TableInfo<T, ID> tableInfo, Dao<T, ID> dao, StatementType statementType) {
        this.databaseType = object;
        this.tableInfo = tableInfo;
        this.tableName = tableInfo.getTableName();
        this.dao = dao;
        this.type = statementType;
        if (!statementType.isOkForStatementBuilder()) {
            object = new StringBuilder();
            object.append("Building a statement from a ");
            object.append((Object)statementType);
            object.append(" statement is not allowed");
            throw new IllegalStateException(object.toString());
        }
    }

    protected abstract void appendStatementEnd(StringBuilder var1, List<ArgumentHolder> var2) throws SQLException;

    protected abstract void appendStatementStart(StringBuilder var1, List<ArgumentHolder> var2) throws SQLException;

    protected void appendStatementString(StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        this.appendStatementStart(stringBuilder, list);
        this.appendWhereStatement(stringBuilder, list, WhereOperation.FIRST);
        this.appendStatementEnd(stringBuilder, list);
    }

    protected boolean appendWhereStatement(StringBuilder stringBuilder, List<ArgumentHolder> list, WhereOperation whereOperation) throws SQLException {
        Object object = this.where;
        boolean bl = false;
        if (object == null) {
            if (whereOperation == WhereOperation.FIRST) {
                bl = true;
            }
            return bl;
        }
        whereOperation.appendBefore(stringBuilder);
        Where<T, ID> where = this.where;
        object = this.addTableName ? this.tableName : null;
        where.appendSql((String)object, stringBuilder, list);
        whereOperation.appendAfter(stringBuilder);
        return false;
    }

    protected String buildStatementString(List<ArgumentHolder> object) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder(128);
        this.appendStatementString(stringBuilder, (List<ArgumentHolder>)object);
        object = stringBuilder.toString();
        logger.debug("built statement {}", object);
        return object;
    }

    @Deprecated
    public void clear() {
        this.reset();
    }

    protected FieldType[] getResultFieldTypes() {
        return null;
    }

    StatementType getType() {
        return this.type;
    }

    protected MappedPreparedStmt<T, ID> prepareStatement(Long serializable) throws SQLException {
        FieldType[] arrfieldType = new FieldType[]();
        String string = this.buildStatementString((List<ArgumentHolder>)arrfieldType);
        ArgumentHolder[] arrargumentHolder = arrfieldType.toArray(new ArgumentHolder[arrfieldType.size()]);
        FieldType[] arrfieldType2 = this.getResultFieldTypes();
        arrfieldType = new FieldType[arrfieldType.size()];
        for (int i = 0; i < arrargumentHolder.length; ++i) {
            arrfieldType[i] = arrargumentHolder[i].getFieldType();
        }
        if (!this.type.isOkForStatementBuilder()) {
            serializable = new StringBuilder();
            serializable.append("Building a statement from a ");
            serializable.append((Object)((Object)this.type));
            serializable.append(" statement is not allowed");
            throw new IllegalStateException(serializable.toString());
        }
        TableInfo<T, ID> tableInfo = this.tableInfo;
        if (this.databaseType.isLimitSqlSupported()) {
            serializable = null;
        }
        return new MappedPreparedStmt<T, ID>(tableInfo, string, arrfieldType, arrfieldType2, arrargumentHolder, (Long)serializable, this.type);
    }

    public StatementInfo prepareStatementInfo() throws SQLException {
        ArrayList<ArgumentHolder> arrayList = new ArrayList<ArgumentHolder>();
        return new StatementInfo(this.buildStatementString(arrayList), arrayList);
    }

    public String prepareStatementString() throws SQLException {
        return this.buildStatementString(new ArrayList<ArgumentHolder>());
    }

    public void reset() {
        this.where = null;
    }

    public void setWhere(Where<T, ID> where) {
        this.where = where;
    }

    protected boolean shouldPrependTableNameToColumns() {
        return false;
    }

    protected FieldType verifyColumnName(String string) {
        return this.tableInfo.getFieldTypeByColumnName(string);
    }

    public Where<T, ID> where() {
        this.where = new Where<T, ID>(this.tableInfo, this, this.databaseType);
        return this.where;
    }

    public static class StatementInfo {
        private final List<ArgumentHolder> argList;
        private final String statement;

        private StatementInfo(String string, List<ArgumentHolder> list) {
            this.argList = list;
            this.statement = string;
        }

        public List<ArgumentHolder> getArgList() {
            return this.argList;
        }

        public String getStatement() {
            return this.statement;
        }
    }

    public static enum StatementType {
        SELECT(true, true, false, false),
        SELECT_LONG(true, true, false, false),
        SELECT_RAW(true, true, false, false),
        UPDATE(true, false, true, false),
        DELETE(true, false, true, false),
        EXECUTE(false, false, false, true);
        
        private final boolean okForExecute;
        private final boolean okForQuery;
        private final boolean okForStatementBuilder;
        private final boolean okForUpdate;

        private StatementType(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            this.okForStatementBuilder = bl;
            this.okForQuery = bl2;
            this.okForUpdate = bl3;
            this.okForExecute = bl4;
        }

        public boolean isOkForExecute() {
            return this.okForExecute;
        }

        public boolean isOkForQuery() {
            return this.okForQuery;
        }

        public boolean isOkForStatementBuilder() {
            return this.okForStatementBuilder;
        }

        public boolean isOkForUpdate() {
            return this.okForUpdate;
        }
    }

    protected static enum WhereOperation {
        FIRST("WHERE ", null),
        AND("AND (", ") "),
        OR("OR (", ") ");
        
        private final String after;
        private final String before;

        private WhereOperation(String string2, String string3) {
            this.before = string2;
            this.after = string3;
        }

        public void appendAfter(StringBuilder stringBuilder) {
            if (this.after != null) {
                stringBuilder.append(this.after);
            }
        }

        public void appendBefore(StringBuilder stringBuilder) {
            if (this.before != null) {
                stringBuilder.append(this.before);
            }
        }
    }

}
