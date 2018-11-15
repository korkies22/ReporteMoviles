/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.stmt.query.Between;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.Exists;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.stmt.query.InSubQuery;
import com.j256.ormlite.stmt.query.IsNotNull;
import com.j256.ormlite.stmt.query.IsNull;
import com.j256.ormlite.stmt.query.ManyClause;
import com.j256.ormlite.stmt.query.NeedsFutureClause;
import com.j256.ormlite.stmt.query.Not;
import com.j256.ormlite.stmt.query.Raw;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Where<T, ID> {
    private static final int CLAUSE_STACK_START_SIZE = 4;
    private Clause[] clauseStack = new Clause[4];
    private int clauseStackLevel;
    private final DatabaseType databaseType;
    private final String idColumnName;
    private final FieldType idFieldType;
    private NeedsFutureClause needsFuture = null;
    private final StatementBuilder<T, ID> statementBuilder;
    private final TableInfo<T, ID> tableInfo;

    Where(TableInfo<T, ID> tableInfo, StatementBuilder<T, ID> statementBuilder, DatabaseType databaseType) {
        this.tableInfo = tableInfo;
        this.statementBuilder = statementBuilder;
        this.idFieldType = tableInfo.getIdField();
        this.idColumnName = this.idFieldType == null ? null : this.idFieldType.getColumnName();
        this.databaseType = databaseType;
    }

    private void addClause(Clause clause) {
        if (this.needsFuture == null) {
            this.push(clause);
            return;
        }
        this.needsFuture.setMissingClause(clause);
        this.needsFuture = null;
    }

    private void addNeedsFuture(NeedsFutureClause needsFutureClause) {
        if (this.needsFuture != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.needsFuture);
            stringBuilder.append(" is already waiting for a future clause, can't add: ");
            stringBuilder.append(needsFutureClause);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.needsFuture = needsFutureClause;
    }

    private Clause[] buildClauseArray(Where<T, ID>[] arrwhere, String string) {
        if (arrwhere.length == 0) {
            return null;
        }
        Clause[] arrclause = new Clause[arrwhere.length];
        for (int i = arrwhere.length - 1; i >= 0; --i) {
            arrclause[i] = this.pop(string);
        }
        return arrclause;
    }

    private QueryBuilder<T, ID> checkQueryBuilderMethod(String string) throws SQLException {
        if (this.statementBuilder instanceof QueryBuilder) {
            return (QueryBuilder)this.statementBuilder;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot call ");
        stringBuilder.append(string);
        stringBuilder.append(" on a statement of type ");
        stringBuilder.append((Object)this.statementBuilder.getType());
        throw new SQLException(stringBuilder.toString());
    }

    private FieldType findColumnFieldType(String string) {
        return this.tableInfo.getFieldTypeByColumnName(string);
    }

    private Where<T, ID> in(boolean bl, String charSequence, QueryBuilder<?, ?> queryBuilder) throws SQLException {
        if (queryBuilder.getSelectColumnCount() != 1) {
            if (queryBuilder.getSelectColumnCount() == 0) {
                throw new SQLException("Inner query must have only 1 select column specified instead of *");
            }
            charSequence = new StringBuilder();
            charSequence.append("Inner query must have only 1 select column specified instead of ");
            charSequence.append(queryBuilder.getSelectColumnCount());
            charSequence.append(": ");
            charSequence.append(Arrays.toString(queryBuilder.getSelectColumns().toArray(new String[0])));
            throw new SQLException(charSequence.toString());
        }
        queryBuilder.enableInnerQuery();
        this.addClause(new InSubQuery((String)charSequence, this.findColumnFieldType((String)charSequence), new QueryBuilder.InternalQueryBuilderWrapper(queryBuilder), bl));
        return this;
    }

    private /* varargs */ Where<T, ID> in(boolean bl, String string, Object ... object) throws SQLException {
        if (((Object[])object).length == 1) {
            if (object[0].getClass().isArray()) {
                object = new StringBuilder();
                object.append("Object argument to ");
                string = bl ? "IN" : "notId";
                object.append(string);
                object.append(" seems to be an array within an array");
                throw new IllegalArgumentException(object.toString());
            }
            if (object[0] instanceof Where) {
                object = new StringBuilder();
                object.append("Object argument to ");
                string = bl ? "IN" : "notId";
                object.append(string);
                object.append(" seems to be a Where object, did you mean the QueryBuilder?");
                throw new IllegalArgumentException(object.toString());
            }
            if (object[0] instanceof PreparedStmt) {
                object = new StringBuilder();
                object.append("Object argument to ");
                string = bl ? "IN" : "notId";
                object.append(string);
                object.append(" seems to be a prepared statement, did you mean the QueryBuilder?");
                throw new IllegalArgumentException(object.toString());
            }
        }
        this.addClause(new In(string, this.findColumnFieldType(string), (Object[])object, bl));
        return this;
    }

    private Clause peek() {
        return this.clauseStack[this.clauseStackLevel - 1];
    }

    private Clause pop(String object) {
        int n;
        if (this.clauseStackLevel == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expecting there to be a clause already defined for '");
            stringBuilder.append((String)object);
            stringBuilder.append("' operation");
            throw new IllegalStateException(stringBuilder.toString());
        }
        object = this.clauseStack;
        this.clauseStackLevel = n = this.clauseStackLevel - 1;
        object = object[n];
        this.clauseStack[this.clauseStackLevel] = null;
        return object;
    }

    private void push(Clause clause) {
        Clause[] arrclause;
        int n;
        if (this.clauseStackLevel == this.clauseStack.length) {
            arrclause = new Clause[this.clauseStackLevel * 2];
            for (n = 0; n < this.clauseStackLevel; ++n) {
                arrclause[n] = this.clauseStack[n];
                this.clauseStack[n] = null;
            }
            this.clauseStack = arrclause;
        }
        arrclause = this.clauseStack;
        n = this.clauseStackLevel;
        this.clauseStackLevel = n + 1;
        arrclause[n] = clause;
    }

    public Where<T, ID> and() {
        ManyClause manyClause = new ManyClause(this.pop("AND"), "AND");
        this.push(manyClause);
        this.addNeedsFuture(manyClause);
        return this;
    }

    public Where<T, ID> and(int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Must have at least one clause in and(numClauses)");
        }
        Clause[] arrclause = new Clause[n];
        --n;
        while (n >= 0) {
            arrclause[n] = this.pop("AND");
            --n;
        }
        this.addClause(new ManyClause(arrclause, "AND"));
        return this;
    }

    public /* varargs */ Where<T, ID> and(Where<T, ID> arrclause, Where<T, ID> object, Where<T, ID> ... arrwhere) {
        arrclause = this.buildClauseArray(arrwhere, "AND");
        object = this.pop("AND");
        this.addClause(new ManyClause(this.pop("AND"), (Clause)object, arrclause, "AND"));
        return this;
    }

    void appendSql(String string, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        if (this.clauseStackLevel == 0) {
            throw new IllegalStateException("No where clauses defined.  Did you miss a where operation?");
        }
        if (this.clauseStackLevel != 1) {
            throw new IllegalStateException("Both the \"left-hand\" and \"right-hand\" clauses have been defined.  Did you miss an AND or OR?");
        }
        if (this.needsFuture != null) {
            throw new IllegalStateException("The SQL statement has not been finished since there are previous operations still waiting for clauses.");
        }
        this.peek().appendSql(this.databaseType, string, stringBuilder, list);
    }

    public Where<T, ID> between(String string, Object object, Object object2) throws SQLException {
        this.addClause(new Between(string, this.findColumnFieldType(string), object, object2));
        return this;
    }

    @Deprecated
    public Where<T, ID> clear() {
        return this.reset();
    }

    public long countOf() throws SQLException {
        return this.checkQueryBuilderMethod("countOf()").countOf();
    }

    public Where<T, ID> eq(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, "="));
        return this;
    }

    public Where<T, ID> exists(QueryBuilder<?, ?> queryBuilder) {
        queryBuilder.enableInnerQuery();
        this.addClause(new Exists(new QueryBuilder.InternalQueryBuilderWrapper(queryBuilder)));
        return this;
    }

    public Where<T, ID> ge(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, ">="));
        return this;
    }

    public String getStatement() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        this.appendSql(null, stringBuilder, new ArrayList<ArgumentHolder>());
        return stringBuilder.toString();
    }

    public Where<T, ID> gt(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, ">"));
        return this;
    }

    public <OD> Where<T, ID> idEq(Dao<OD, ?> dao, OD OD) throws SQLException {
        if (this.idColumnName == null) {
            throw new SQLException("Object has no id column specified");
        }
        this.addClause(new SimpleComparison(this.idColumnName, this.idFieldType, dao.extractId(OD), "="));
        return this;
    }

    public Where<T, ID> idEq(ID ID) throws SQLException {
        if (this.idColumnName == null) {
            throw new SQLException("Object has no id column specified");
        }
        this.addClause(new SimpleComparison(this.idColumnName, this.idFieldType, ID, "="));
        return this;
    }

    public Where<T, ID> in(String string, QueryBuilder<?, ?> queryBuilder) throws SQLException {
        return this.in(true, string, queryBuilder);
    }

    public Where<T, ID> in(String string, Iterable<?> iterable) throws SQLException {
        this.addClause(new In(string, this.findColumnFieldType(string), iterable, true));
        return this;
    }

    public /* varargs */ Where<T, ID> in(String string, Object ... arrobject) throws SQLException {
        return this.in(true, string, arrobject);
    }

    public Where<T, ID> isNotNull(String string) throws SQLException {
        this.addClause(new IsNotNull(string, this.findColumnFieldType(string)));
        return this;
    }

    public Where<T, ID> isNull(String string) throws SQLException {
        this.addClause(new IsNull(string, this.findColumnFieldType(string)));
        return this;
    }

    public CloseableIterator<T> iterator() throws SQLException {
        return this.checkQueryBuilderMethod("iterator()").iterator();
    }

    public Where<T, ID> le(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, "<="));
        return this;
    }

    public Where<T, ID> like(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, "LIKE"));
        return this;
    }

    public Where<T, ID> lt(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, "<"));
        return this;
    }

    public Where<T, ID> ne(String string, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, "<>"));
        return this;
    }

    public Where<T, ID> not() {
        Not not = new Not();
        this.addClause(not);
        this.addNeedsFuture(not);
        return this;
    }

    public Where<T, ID> not(Where<T, ID> where) {
        this.addClause(new Not(this.pop("NOT")));
        return this;
    }

    public Where<T, ID> notIn(String string, QueryBuilder<?, ?> queryBuilder) throws SQLException {
        return this.in(false, string, queryBuilder);
    }

    public Where<T, ID> notIn(String string, Iterable<?> iterable) throws SQLException {
        this.addClause(new In(string, this.findColumnFieldType(string), iterable, false));
        return this;
    }

    public /* varargs */ Where<T, ID> notIn(String string, Object ... arrobject) throws SQLException {
        return this.in(false, string, arrobject);
    }

    public Where<T, ID> or() {
        ManyClause manyClause = new ManyClause(this.pop("OR"), "OR");
        this.push(manyClause);
        this.addNeedsFuture(manyClause);
        return this;
    }

    public Where<T, ID> or(int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Must have at least one clause in or(numClauses)");
        }
        Clause[] arrclause = new Clause[n];
        --n;
        while (n >= 0) {
            arrclause[n] = this.pop("OR");
            --n;
        }
        this.addClause(new ManyClause(arrclause, "OR"));
        return this;
    }

    public /* varargs */ Where<T, ID> or(Where<T, ID> arrclause, Where<T, ID> object, Where<T, ID> ... arrwhere) {
        arrclause = this.buildClauseArray(arrwhere, "OR");
        object = this.pop("OR");
        this.addClause(new ManyClause(this.pop("OR"), (Clause)object, arrclause, "OR"));
        return this;
    }

    public PreparedQuery<T> prepare() throws SQLException {
        return this.statementBuilder.prepareStatement(null);
    }

    public List<T> query() throws SQLException {
        return this.checkQueryBuilderMethod("query()").query();
    }

    public T queryForFirst() throws SQLException {
        return this.checkQueryBuilderMethod("queryForFirst()").queryForFirst();
    }

    public GenericRawResults<String[]> queryRaw() throws SQLException {
        return this.checkQueryBuilderMethod("queryRaw()").queryRaw();
    }

    public String[] queryRawFirst() throws SQLException {
        return this.checkQueryBuilderMethod("queryRawFirst()").queryRawFirst();
    }

    public /* varargs */ Where<T, ID> raw(String string, ArgumentHolder ... arrargumentHolder) {
        int n = arrargumentHolder.length;
        for (int i = 0; i < n; ++i) {
            ArgumentHolder argumentHolder = arrargumentHolder[i];
            String string2 = argumentHolder.getColumnName();
            if (string2 == null) {
                if (argumentHolder.getSqlType() != null) continue;
                throw new IllegalArgumentException("Either the column name or SqlType must be set on each argument");
            }
            argumentHolder.setMetaInfo(this.findColumnFieldType(string2));
        }
        this.addClause(new Raw(string, arrargumentHolder));
        return this;
    }

    public Where<T, ID> rawComparison(String string, String string2, Object object) throws SQLException {
        this.addClause(new SimpleComparison(string, this.findColumnFieldType(string), object, string2));
        return this;
    }

    public Where<T, ID> reset() {
        for (int i = 0; i < this.clauseStackLevel; ++i) {
            this.clauseStack[i] = null;
        }
        this.clauseStackLevel = 0;
        return this;
    }

    public String toString() {
        if (this.clauseStackLevel == 0) {
            return "empty where clause";
        }
        Clause clause = this.peek();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("where clause: ");
        stringBuilder.append(clause);
        return stringBuilder.toString();
    }
}
