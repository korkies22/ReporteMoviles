/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.stmt.query.OrderBy;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class QueryBuilder<T, ID>
extends StatementBuilder<T, ID> {
    private boolean distinct;
    private List<String> groupByList;
    private String groupByRaw;
    private String having;
    private final FieldType idField;
    private boolean isCountOfQuery;
    private boolean isInnerQuery;
    private List<QueryBuilder<T, ID>> joinList;
    private Long limit;
    private Long offset;
    private ArgumentHolder[] orderByArgs;
    private List<OrderBy> orderByList;
    private String orderByRaw;
    private FieldType[] resultFieldTypes;
    private List<String> selectColumnList;
    private boolean selectIdColumn = true;
    private List<String> selectRawList;

    public QueryBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, Dao<T, ID> dao) {
        super(databaseType, tableInfo, dao, StatementBuilder.StatementType.SELECT);
        this.idField = tableInfo.getIdField();
    }

    private void addJoinInfo(String object, QueryBuilder<?, ?> queryBuilder, StatementBuilder.WhereOperation whereOperation) throws SQLException {
        object = new JoinInfo((String)object, queryBuilder, whereOperation);
        this.matchJoinedFields((JoinInfo)object, queryBuilder);
        if (this.joinList == null) {
            this.joinList = new ArrayList<QueryBuilder<T, ID>>();
        }
        this.joinList.add((QueryBuilder<T, ID>)object);
    }

    private void addSelectColumnToList(String string) {
        this.verifyColumnName(string);
        this.selectColumnList.add(string);
    }

    private void appendColumnName(StringBuilder stringBuilder, String string) {
        if (this.addTableName) {
            this.databaseType.appendEscapedEntityName(stringBuilder, this.tableName);
            stringBuilder.append('.');
        }
        this.databaseType.appendEscapedEntityName(stringBuilder, string);
    }

    private void appendColumns(StringBuilder stringBuilder) {
        if (this.selectColumnList == null) {
            if (this.addTableName) {
                this.databaseType.appendEscapedEntityName(stringBuilder, this.tableName);
                stringBuilder.append('.');
            }
            stringBuilder.append("* ");
            this.resultFieldTypes = this.tableInfo.getFieldTypes();
            return;
        }
        boolean bl = this.isInnerQuery;
        ArrayList<FieldType> arrayList = new ArrayList<FieldType>(this.selectColumnList.size() + 1);
        Iterator<String> iterator = this.selectColumnList.iterator();
        boolean bl2 = true;
        while (iterator.hasNext()) {
            boolean bl3;
            Object object = iterator.next();
            if ((object = this.tableInfo.getFieldTypeByColumnName((String)object)).isForeignCollection()) {
                arrayList.add((FieldType)object);
                continue;
            }
            if (bl2) {
                bl3 = false;
            } else {
                stringBuilder.append(',');
                bl3 = bl2;
            }
            this.appendFieldColumnName(stringBuilder, (FieldType)object, arrayList);
            bl2 = bl3;
            if (object != this.idField) continue;
            bl = true;
            bl2 = bl3;
        }
        if (!bl && this.selectIdColumn) {
            if (!bl2) {
                stringBuilder.append(',');
            }
            this.appendFieldColumnName(stringBuilder, this.idField, arrayList);
        }
        stringBuilder.append(' ');
        this.resultFieldTypes = arrayList.toArray(new FieldType[arrayList.size()]);
    }

    private void appendFieldColumnName(StringBuilder stringBuilder, FieldType fieldType, List<FieldType> list) {
        this.appendColumnName(stringBuilder, fieldType.getColumnName());
        if (list != null) {
            list.add(fieldType);
        }
    }

    private void appendGroupBys(StringBuilder stringBuilder) {
        boolean bl = this.hasGroupStuff();
        boolean bl2 = true;
        if (bl) {
            this.appendGroupBys(stringBuilder, true);
            bl2 = false;
        }
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                if (joinInfo.queryBuilder == null || !QueryBuilder.super.hasGroupStuff()) continue;
                QueryBuilder.super.appendGroupBys(stringBuilder, bl2);
            }
        }
    }

    private void appendGroupBys(StringBuilder stringBuilder, boolean bl) {
        if (bl) {
            stringBuilder.append("GROUP BY ");
        }
        if (this.groupByRaw != null) {
            if (!bl) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.groupByRaw);
        } else {
            for (String string : this.groupByList) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(',');
                }
                this.appendColumnName(stringBuilder, string);
            }
        }
        stringBuilder.append(' ');
    }

    private void appendHaving(StringBuilder stringBuilder) {
        if (this.having != null) {
            stringBuilder.append("HAVING ");
            stringBuilder.append(this.having);
            stringBuilder.append(' ');
        }
    }

    private void appendJoinSql(StringBuilder stringBuilder) {
        for (JoinInfo joinInfo : this.joinList) {
            stringBuilder.append(joinInfo.type);
            stringBuilder.append(" JOIN ");
            this.databaseType.appendEscapedEntityName(stringBuilder, joinInfo.queryBuilder.tableName);
            stringBuilder.append(" ON ");
            this.databaseType.appendEscapedEntityName(stringBuilder, this.tableName);
            stringBuilder.append('.');
            this.databaseType.appendEscapedEntityName(stringBuilder, joinInfo.localField.getColumnName());
            stringBuilder.append(" = ");
            this.databaseType.appendEscapedEntityName(stringBuilder, joinInfo.queryBuilder.tableName);
            stringBuilder.append('.');
            this.databaseType.appendEscapedEntityName(stringBuilder, joinInfo.remoteField.getColumnName());
            stringBuilder.append(' ');
            if (joinInfo.queryBuilder.joinList == null) continue;
            QueryBuilder.super.appendJoinSql(stringBuilder);
        }
    }

    private void appendLimit(StringBuilder stringBuilder) {
        if (this.limit != null && this.databaseType.isLimitSqlSupported()) {
            this.databaseType.appendLimitValue(stringBuilder, this.limit, this.offset);
        }
    }

    private void appendOffset(StringBuilder stringBuilder) throws SQLException {
        if (this.offset == null) {
            return;
        }
        if (this.databaseType.isOffsetLimitArgument()) {
            if (this.limit == null) {
                throw new SQLException("If the offset is specified, limit must also be specified with this database");
            }
        } else {
            this.databaseType.appendOffsetValue(stringBuilder, this.offset);
        }
    }

    private void appendOrderBys(StringBuilder stringBuilder, List<ArgumentHolder> list) {
        boolean bl = this.hasOrderStuff();
        boolean bl2 = true;
        if (bl) {
            this.appendOrderBys(stringBuilder, true, list);
            bl2 = false;
        }
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                if (joinInfo.queryBuilder == null || !QueryBuilder.super.hasOrderStuff()) continue;
                QueryBuilder.super.appendOrderBys(stringBuilder, bl2, list);
            }
        }
    }

    private void appendOrderBys(StringBuilder stringBuilder, boolean bl, List<ArgumentHolder> object) {
        if (bl) {
            stringBuilder.append("ORDER BY ");
        }
        boolean bl2 = bl;
        if (this.orderByRaw != null) {
            if (!bl) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.orderByRaw);
            if (this.orderByArgs != null) {
                ArgumentHolder[] object2 = this.orderByArgs;
                int n = object2.length;
                for (int i = 0; i < n; ++i) {
                    object.add((OrderBy)((Object)object2[i]));
                }
            }
            bl2 = false;
        }
        if (this.orderByList != null) {
            for (OrderBy orderBy : this.orderByList) {
                if (bl2) {
                    bl2 = false;
                } else {
                    stringBuilder.append(',');
                }
                this.appendColumnName(stringBuilder, orderBy.getColumnName());
                if (orderBy.isAscending()) continue;
                stringBuilder.append(" DESC");
            }
        }
        stringBuilder.append(' ');
    }

    private void appendSelectRaw(StringBuilder stringBuilder) {
        Iterator<String> iterator = this.selectRawList.iterator();
        boolean bl = true;
        while (iterator.hasNext()) {
            String string = iterator.next();
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
        }
        stringBuilder.append(' ');
    }

    private boolean hasGroupStuff() {
        if (this.groupByList != null && !this.groupByList.isEmpty() || this.groupByRaw != null) {
            return true;
        }
        return false;
    }

    private boolean hasOrderStuff() {
        if (this.orderByList != null && !this.orderByList.isEmpty() || this.orderByRaw != null) {
            return true;
        }
        return false;
    }

    private void matchJoinedFields(QueryBuilder<T, ID> object, QueryBuilder<?, ?> queryBuilder) throws SQLException {
        FieldType[] arrfieldType = this.tableInfo.getFieldTypes();
        int n = 0;
        for (FieldType fieldType : arrfieldType) {
            FieldType fieldType2 = fieldType.getForeignIdField();
            if (!fieldType.isForeign() || !fieldType2.equals(queryBuilder.tableInfo.getIdField())) continue;
            object.localField = fieldType;
            object.remoteField = fieldType2;
            return;
        }
        arrfieldType = queryBuilder.tableInfo.getFieldTypes();
        int n2 = arrfieldType.length;
        for (int i = n; i < n2; ++i) {
            FieldType fieldType;
            fieldType = arrfieldType[i];
            if (!fieldType.isForeign() || !fieldType.getForeignIdField().equals(this.idField)) continue;
            object.localField = this.idField;
            object.remoteField = fieldType;
            return;
        }
        object = new StringBuilder();
        object.append("Could not find a foreign ");
        object.append(this.tableInfo.getDataClass());
        object.append(" field in ");
        object.append(queryBuilder.tableInfo.getDataClass());
        object.append(" or vice versa");
        throw new SQLException(object.toString());
    }

    private void setAddTableName(boolean bl) {
        this.addTableName = bl;
        if (this.joinList != null) {
            Iterator<QueryBuilder<T, ID>> iterator = this.joinList.iterator();
            while (iterator.hasNext()) {
                QueryBuilder.super.setAddTableName(bl);
            }
        }
    }

    @Override
    protected void appendStatementEnd(StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        this.appendGroupBys(stringBuilder);
        this.appendHaving(stringBuilder);
        this.appendOrderBys(stringBuilder, list);
        if (!this.databaseType.isLimitAfterSelect()) {
            this.appendLimit(stringBuilder);
        }
        this.appendOffset(stringBuilder);
        this.setAddTableName(false);
    }

    @Override
    protected void appendStatementStart(StringBuilder stringBuilder, List<ArgumentHolder> list) {
        if (this.joinList == null) {
            this.setAddTableName(false);
        } else {
            this.setAddTableName(true);
        }
        stringBuilder.append("SELECT ");
        if (this.databaseType.isLimitAfterSelect()) {
            this.appendLimit(stringBuilder);
        }
        if (this.distinct) {
            stringBuilder.append("DISTINCT ");
        }
        if (this.isCountOfQuery) {
            this.type = StatementBuilder.StatementType.SELECT_LONG;
            stringBuilder.append("COUNT(*) ");
        } else if (this.selectRawList != null && !this.selectRawList.isEmpty()) {
            this.type = StatementBuilder.StatementType.SELECT_RAW;
            this.appendSelectRaw(stringBuilder);
        } else {
            this.type = StatementBuilder.StatementType.SELECT;
            this.appendColumns(stringBuilder);
        }
        stringBuilder.append("FROM ");
        this.databaseType.appendEscapedEntityName(stringBuilder, this.tableName);
        stringBuilder.append(' ');
        if (this.joinList != null) {
            this.appendJoinSql(stringBuilder);
        }
    }

    @Override
    protected boolean appendWhereStatement(StringBuilder stringBuilder, List<ArgumentHolder> list, StatementBuilder.WhereOperation whereOperation) throws SQLException {
        boolean bl = whereOperation == StatementBuilder.WhereOperation.FIRST;
        if (this.where != null) {
            bl = super.appendWhereStatement(stringBuilder, list, whereOperation);
        }
        boolean bl2 = bl;
        if (this.joinList != null) {
            Iterator<QueryBuilder<T, ID>> iterator = this.joinList.iterator();
            do {
                bl2 = bl;
                if (!iterator.hasNext()) break;
                JoinInfo joinInfo = (JoinInfo)((Object)iterator.next());
                whereOperation = bl ? StatementBuilder.WhereOperation.FIRST : joinInfo.operation;
                bl = joinInfo.queryBuilder.appendWhereStatement(stringBuilder, list, whereOperation);
            } while (true);
        }
        return bl2;
    }

    @Deprecated
    @Override
    public void clear() {
        this.reset();
    }

    public long countOf() throws SQLException {
        this.setCountOf(true);
        return this.dao.countOf(this.prepare());
    }

    public QueryBuilder<T, ID> distinct() {
        this.distinct = true;
        this.selectIdColumn = false;
        return this;
    }

    void enableInnerQuery() {
        this.isInnerQuery = true;
    }

    @Override
    protected FieldType[] getResultFieldTypes() {
        return this.resultFieldTypes;
    }

    int getSelectColumnCount() {
        if (this.isCountOfQuery) {
            return 1;
        }
        if (this.selectRawList != null && !this.selectRawList.isEmpty()) {
            return this.selectRawList.size();
        }
        if (this.selectColumnList == null) {
            return 0;
        }
        return this.selectColumnList.size();
    }

    List<String> getSelectColumns() {
        if (this.isCountOfQuery) {
            return Collections.singletonList("COUNT(*)");
        }
        if (this.selectRawList != null && !this.selectRawList.isEmpty()) {
            return this.selectRawList;
        }
        if (this.selectColumnList == null) {
            return Collections.emptyList();
        }
        return this.selectColumnList;
    }

    public QueryBuilder<T, ID> groupBy(String string) {
        if (this.verifyColumnName(string).isForeignCollection()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't groupBy foreign colletion field: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.groupByList == null) {
            this.groupByList = new ArrayList<String>();
        }
        this.groupByList.add(string);
        this.selectIdColumn = false;
        return this;
    }

    public QueryBuilder<T, ID> groupByRaw(String string) {
        this.groupByRaw = string;
        return this;
    }

    public QueryBuilder<T, ID> having(String string) {
        this.having = string;
        return this;
    }

    public CloseableIterator<T> iterator() throws SQLException {
        return this.dao.iterator(this.prepare());
    }

    public QueryBuilder<T, ID> join(QueryBuilder<?, ?> queryBuilder) throws SQLException {
        this.addJoinInfo("INNER", queryBuilder, StatementBuilder.WhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> joinOr(QueryBuilder<?, ?> queryBuilder) throws SQLException {
        this.addJoinInfo("INNER", queryBuilder, StatementBuilder.WhereOperation.OR);
        return this;
    }

    public QueryBuilder<T, ID> leftJoin(QueryBuilder<?, ?> queryBuilder) throws SQLException {
        this.addJoinInfo("LEFT", queryBuilder, StatementBuilder.WhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> leftJoinOr(QueryBuilder<?, ?> queryBuilder) throws SQLException {
        this.addJoinInfo("LEFT", queryBuilder, StatementBuilder.WhereOperation.OR);
        return this;
    }

    @Deprecated
    public QueryBuilder<T, ID> limit(int n) {
        return this.limit(Long.valueOf(n));
    }

    public QueryBuilder<T, ID> limit(Long l) {
        this.limit = l;
        return this;
    }

    @Deprecated
    public QueryBuilder<T, ID> offset(int n) throws SQLException {
        return this.offset(Long.valueOf(n));
    }

    public QueryBuilder<T, ID> offset(Long l) throws SQLException {
        if (this.databaseType.isOffsetSqlSupported()) {
            this.offset = l;
            return this;
        }
        throw new SQLException("Offset is not supported by this database");
    }

    public QueryBuilder<T, ID> orderBy(String string, boolean bl) {
        if (this.verifyColumnName(string).isForeignCollection()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't orderBy foreign colletion field: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.orderByList == null) {
            this.orderByList = new ArrayList<OrderBy>();
        }
        this.orderByList.add(new OrderBy(string, bl));
        return this;
    }

    public QueryBuilder<T, ID> orderByRaw(String string) {
        return this.orderByRaw(string, null);
    }

    public /* varargs */ QueryBuilder<T, ID> orderByRaw(String string, ArgumentHolder ... arrargumentHolder) {
        this.orderByRaw = string;
        this.orderByArgs = arrargumentHolder;
        return this;
    }

    public PreparedQuery<T> prepare() throws SQLException {
        return super.prepareStatement(this.limit);
    }

    public List<T> query() throws SQLException {
        return this.dao.query(this.prepare());
    }

    public T queryForFirst() throws SQLException {
        return this.dao.queryForFirst(this.prepare());
    }

    public GenericRawResults<String[]> queryRaw() throws SQLException {
        return this.dao.queryRaw(this.prepareStatementString(), new String[0]);
    }

    public String[] queryRawFirst() throws SQLException {
        return this.dao.queryRaw(this.prepareStatementString(), new String[0]).getFirstResult();
    }

    @Override
    public void reset() {
        super.reset();
        this.distinct = false;
        this.selectIdColumn = true;
        this.selectColumnList = null;
        this.selectRawList = null;
        this.orderByList = null;
        this.orderByRaw = null;
        this.groupByList = null;
        this.groupByRaw = null;
        this.isInnerQuery = false;
        this.isCountOfQuery = false;
        this.having = null;
        this.limit = null;
        this.offset = null;
        if (this.joinList != null) {
            this.joinList.clear();
            this.joinList = null;
        }
        this.addTableName = false;
    }

    public QueryBuilder<T, ID> selectColumns(Iterable<String> object) {
        if (this.selectColumnList == null) {
            this.selectColumnList = new ArrayList<String>();
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.addSelectColumnToList((String)object.next());
        }
        return this;
    }

    public /* varargs */ QueryBuilder<T, ID> selectColumns(String ... arrstring) {
        if (this.selectColumnList == null) {
            this.selectColumnList = new ArrayList<String>();
        }
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            this.addSelectColumnToList(arrstring[i]);
        }
        return this;
    }

    public /* varargs */ QueryBuilder<T, ID> selectRaw(String ... arrstring) {
        if (this.selectRawList == null) {
            this.selectRawList = new ArrayList<String>();
        }
        for (String string : arrstring) {
            this.selectRawList.add(string);
        }
        return this;
    }

    public QueryBuilder<T, ID> setCountOf(boolean bl) {
        this.isCountOfQuery = bl;
        return this;
    }

    @Override
    protected boolean shouldPrependTableNameToColumns() {
        if (this.joinList != null) {
            return true;
        }
        return false;
    }

    public static class InternalQueryBuilderWrapper {
        private final QueryBuilder<?, ?> queryBuilder;

        InternalQueryBuilderWrapper(QueryBuilder<?, ?> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public void appendStatementString(StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
            this.queryBuilder.appendStatementString(stringBuilder, list);
        }

        public FieldType[] getResultFieldTypes() {
            return this.queryBuilder.getResultFieldTypes();
        }
    }

    private class JoinInfo {
        FieldType localField;
        StatementBuilder.WhereOperation operation;
        final QueryBuilder<?, ?> queryBuilder;
        FieldType remoteField;
        final String type;

        public JoinInfo(String string, QueryBuilder<?, ?> queryBuilder2, StatementBuilder.WhereOperation whereOperation) {
            this.type = string;
            this.queryBuilder = queryBuilder2;
            this.operation = whereOperation;
        }
    }

}
