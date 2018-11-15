/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.SetExpression;
import com.j256.ormlite.stmt.query.SetValue;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder<T, ID>
extends StatementBuilder<T, ID> {
    private List<Clause> updateClauseList = null;

    public UpdateBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, Dao<T, ID> dao) {
        super(databaseType, tableInfo, dao, StatementBuilder.StatementType.UPDATE);
    }

    private void addUpdateColumnToList(String string, Clause clause) {
        if (this.updateClauseList == null) {
            this.updateClauseList = new ArrayList<Clause>();
        }
        this.updateClauseList.add(clause);
    }

    @Override
    protected void appendStatementEnd(StringBuilder stringBuilder, List<ArgumentHolder> list) {
    }

    @Override
    protected void appendStatementStart(StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        if (this.updateClauseList != null && !this.updateClauseList.isEmpty()) {
            stringBuilder.append("UPDATE ");
            this.databaseType.appendEscapedEntityName(stringBuilder, this.tableInfo.getTableName());
            stringBuilder.append(" SET ");
            boolean bl = true;
            for (Clause clause : this.updateClauseList) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(',');
                }
                clause.appendSql(this.databaseType, null, stringBuilder, list);
            }
            return;
        }
        throw new IllegalArgumentException("UPDATE statements must have at least one SET column");
    }

    @Deprecated
    @Override
    public void clear() {
        this.reset();
    }

    public String escapeColumnName(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + 4);
        this.databaseType.appendEscapedEntityName(stringBuilder, string);
        return stringBuilder.toString();
    }

    public void escapeColumnName(StringBuilder stringBuilder, String string) {
        this.databaseType.appendEscapedEntityName(stringBuilder, string);
    }

    public String escapeValue(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + 4);
        this.databaseType.appendEscapedWord(stringBuilder, string);
        return stringBuilder.toString();
    }

    public void escapeValue(StringBuilder stringBuilder, String string) {
        this.databaseType.appendEscapedWord(stringBuilder, string);
    }

    public PreparedUpdate<T> prepare() throws SQLException {
        return super.prepareStatement(null);
    }

    @Override
    public void reset() {
        super.reset();
        this.updateClauseList = null;
    }

    public int update() throws SQLException {
        return this.dao.update(this.prepare());
    }

    public StatementBuilder<T, ID> updateColumnExpression(String string, String charSequence) throws SQLException {
        FieldType fieldType = this.verifyColumnName(string);
        if (fieldType.isForeignCollection()) {
            charSequence = new StringBuilder();
            charSequence.append("Can't update foreign colletion field: ");
            charSequence.append(string);
            throw new SQLException(charSequence.toString());
        }
        this.addUpdateColumnToList(string, new SetExpression(string, fieldType, (String)charSequence));
        return this;
    }

    public StatementBuilder<T, ID> updateColumnValue(String string, Object object) throws SQLException {
        FieldType fieldType = this.verifyColumnName(string);
        if (fieldType.isForeignCollection()) {
            object = new StringBuilder();
            object.append("Can't update foreign colletion field: ");
            object.append(string);
            throw new SQLException(object.toString());
        }
        this.addUpdateColumnToList(string, new SetValue(string, fieldType, object));
        return this;
    }
}
