/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.RawResultsImpl;
import com.j256.ormlite.stmt.RawRowMapperImpl;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.SelectIterator;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.mapped.MappedCreate;
import com.j256.ormlite.stmt.mapped.MappedDelete;
import com.j256.ormlite.stmt.mapped.MappedDeleteCollection;
import com.j256.ormlite.stmt.mapped.MappedQueryForId;
import com.j256.ormlite.stmt.mapped.MappedRefresh;
import com.j256.ormlite.stmt.mapped.MappedUpdate;
import com.j256.ormlite.stmt.mapped.MappedUpdateId;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class StatementExecutor<T, ID>
implements GenericRowMapper<String[]> {
    private static Logger logger = LoggerFactory.getLogger(StatementExecutor.class);
    private static final FieldType[] noFieldTypes = new FieldType[0];
    private String countStarQuery;
    private final Dao<T, ID> dao;
    private final DatabaseType databaseType;
    private FieldType[] ifExistsFieldTypes;
    private String ifExistsQuery;
    private MappedDelete<T, ID> mappedDelete;
    private MappedCreate<T, ID> mappedInsert;
    private MappedQueryForId<T, ID> mappedQueryForId;
    private MappedRefresh<T, ID> mappedRefresh;
    private MappedUpdate<T, ID> mappedUpdate;
    private MappedUpdateId<T, ID> mappedUpdateId;
    private PreparedQuery<T> preparedQueryForAll;
    private RawRowMapper<T> rawRowMapper;
    private final TableInfo<T, ID> tableInfo;

    public StatementExecutor(DatabaseType databaseType, TableInfo<T, ID> tableInfo, Dao<T, ID> dao) {
        this.databaseType = databaseType;
        this.tableInfo = tableInfo;
        this.dao = dao;
    }

    private void assignStatementArguments(CompiledStatement compiledStatement, String[] arrstring) throws SQLException {
        for (int i = 0; i < arrstring.length; ++i) {
            compiledStatement.setObject(i, arrstring[i], SqlType.STRING);
        }
    }

    private void prepareQueryForAll() throws SQLException {
        if (this.preparedQueryForAll == null) {
            this.preparedQueryForAll = new QueryBuilder<T, ID>(this.databaseType, this.tableInfo, this.dao).prepare();
        }
    }

    public SelectIterator<T, ID> buildIterator(BaseDaoImpl<T, ID> baseDaoImpl, ConnectionSource connectionSource, int n, ObjectCache objectCache) throws SQLException {
        this.prepareQueryForAll();
        return this.buildIterator(baseDaoImpl, connectionSource, this.preparedQueryForAll, objectCache, n);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SelectIterator<T, ID> buildIterator(BaseDaoImpl<T, ID> object, ConnectionSource connectionSource, PreparedStmt<T> object2, ObjectCache objectCache, int n) throws SQLException {
        void var1_4;
        DatabaseConnection databaseConnection;
        block5 : {
            databaseConnection = connectionSource.getReadOnlyConnection();
            CompiledStatement compiledStatement = object2.compile(databaseConnection, StatementBuilder.StatementType.SELECT, n);
            try {
                return new SelectIterator(this.tableInfo.getDataClass(), object, object2, connectionSource, databaseConnection, compiledStatement, object2.getStatement(), objectCache);
            }
            catch (Throwable throwable) {
                object2 = compiledStatement;
                break block5;
            }
            catch (Throwable throwable) {
                object2 = null;
            }
        }
        if (object2 != null) {
            object2.close();
        }
        if (databaseConnection == null) throw var1_4;
        connectionSource.releaseConnection(databaseConnection);
        throw var1_4;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public <CT> CT callBatchTasks(DatabaseConnection databaseConnection, boolean bl, Callable<CT> callable) throws SQLException {
        void var3_8;
        block10 : {
            boolean bl2;
            block9 : {
                if (this.databaseType.isBatchUseTransaction()) {
                    return TransactionManager.callInTransaction(databaseConnection, bl, this.databaseType, callable);
                }
                boolean bl3 = false;
                bl2 = false;
                bl = bl3;
                if (!databaseConnection.isAutoCommitSupported()) break block9;
                bl = bl3;
                bl2 = databaseConnection.isAutoCommit();
                if (!bl2) break block9;
                try {
                    databaseConnection.setAutoCommit(false);
                    logger.debug("disabled auto-commit on table {} before batch tasks", (Object)this.tableInfo.getTableName());
                }
                catch (Throwable throwable) {
                    bl = bl2;
                    break block10;
                }
            }
            bl = bl2;
            try {
                callable = callable.call();
                if (!bl2) return (CT)callable;
            }
            catch (Exception exception) {
                bl = bl2;
                try {
                    throw SqlExceptionUtil.create("Batch tasks callable threw non-SQL exception", exception);
                    catch (SQLException sQLException) {
                        bl = bl2;
                        throw sQLException;
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            databaseConnection.setAutoCommit(true);
            logger.debug("re-enabled auto-commit on table {} after batch tasks", (Object)this.tableInfo.getTableName());
            return (CT)callable;
        }
        if (!bl) throw var3_8;
        databaseConnection.setAutoCommit(true);
        logger.debug("re-enabled auto-commit on table {} after batch tasks", (Object)this.tableInfo.getTableName());
        throw var3_8;
    }

    public int create(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) throws SQLException {
        if (this.mappedInsert == null) {
            this.mappedInsert = MappedCreate.build(this.databaseType, this.tableInfo);
        }
        return this.mappedInsert.insert(this.databaseType, databaseConnection, t, objectCache);
    }

    public int delete(DatabaseConnection object, PreparedDelete<T> preparedDelete) throws SQLException {
        object = preparedDelete.compile((DatabaseConnection)object, StatementBuilder.StatementType.DELETE);
        try {
            int n = object.runUpdate();
            return n;
        }
        finally {
            object.close();
        }
    }

    public int delete(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) throws SQLException {
        if (this.mappedDelete == null) {
            this.mappedDelete = MappedDelete.build(this.databaseType, this.tableInfo);
        }
        return this.mappedDelete.delete(databaseConnection, t, objectCache);
    }

    public int deleteById(DatabaseConnection databaseConnection, ID ID, ObjectCache objectCache) throws SQLException {
        if (this.mappedDelete == null) {
            this.mappedDelete = MappedDelete.build(this.databaseType, this.tableInfo);
        }
        return this.mappedDelete.deleteById(databaseConnection, ID, objectCache);
    }

    public int deleteIds(DatabaseConnection databaseConnection, Collection<ID> collection, ObjectCache objectCache) throws SQLException {
        return MappedDeleteCollection.deleteIds(this.databaseType, this.tableInfo, databaseConnection, collection, objectCache);
    }

    public int deleteObjects(DatabaseConnection databaseConnection, Collection<T> collection, ObjectCache objectCache) throws SQLException {
        return MappedDeleteCollection.deleteObjects(this.databaseType, this.tableInfo, databaseConnection, collection, objectCache);
    }

    public int executeRaw(DatabaseConnection object, String string, String[] arrstring) throws SQLException {
        logger.debug("running raw execute statement: {}", (Object)string);
        if (arrstring.length > 0) {
            logger.trace("execute arguments: {}", (Object)arrstring);
        }
        object = object.compileStatement(string, StatementBuilder.StatementType.EXECUTE, noFieldTypes, -1);
        try {
            this.assignStatementArguments((CompiledStatement)object, arrstring);
            int n = object.runExecute();
            return n;
        }
        finally {
            object.close();
        }
    }

    public int executeRawNoArgs(DatabaseConnection databaseConnection, String string) throws SQLException {
        logger.debug("running raw execute statement: {}", (Object)string);
        return databaseConnection.executeStatement(string, -1);
    }

    public RawRowMapper<T> getRawRowMapper() {
        if (this.rawRowMapper == null) {
            this.rawRowMapper = new RawRowMapperImpl<T, ID>(this.tableInfo);
        }
        return this.rawRowMapper;
    }

    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException {
        this.prepareQueryForAll();
        return this.preparedQueryForAll;
    }

    public boolean ifExists(DatabaseConnection databaseConnection, ID ID) throws SQLException {
        Object object = this.ifExistsQuery;
        boolean bl = false;
        if (object == null) {
            object = new QueryBuilder<T, ID>(this.databaseType, this.tableInfo, this.dao);
            object.selectRaw("COUNT(*)");
            object.where().eq(this.tableInfo.getIdField().getColumnName(), new SelectArg());
            this.ifExistsQuery = object.prepareStatementString();
            this.ifExistsFieldTypes = new FieldType[]{this.tableInfo.getIdField()};
        }
        object = this.ifExistsQuery;
        FieldType[] arrfieldType = this.ifExistsFieldTypes;
        long l = databaseConnection.queryForLong((String)object, new Object[]{ID}, arrfieldType);
        logger.debug("query of '{}' returned {}", (Object)this.ifExistsQuery, (Object)l);
        if (l != 0L) {
            bl = true;
        }
        return bl;
    }

    @Override
    public String[] mapRow(DatabaseResults databaseResults) throws SQLException {
        int n = databaseResults.getColumnCount();
        String[] arrstring = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring[i] = databaseResults.getString(i);
        }
        return arrstring;
    }

    public List<T> query(ConnectionSource object, PreparedStmt<T> preparedStmt, ObjectCache object2) throws SQLException {
        object = this.buildIterator(null, (ConnectionSource)object, preparedStmt, (ObjectCache)object2, -1);
        try {
            object2 = new ArrayList();
            while (object.hasNextThrow()) {
                object2.add(object.nextThrow());
            }
            logger.debug("query of '{}' returned {} results", (Object)preparedStmt.getStatement(), (Object)object2.size());
            return object2;
        }
        finally {
            object.close();
        }
    }

    public List<T> queryForAll(ConnectionSource connectionSource, ObjectCache objectCache) throws SQLException {
        this.prepareQueryForAll();
        return this.query(connectionSource, this.preparedQueryForAll, objectCache);
    }

    public long queryForCountStar(DatabaseConnection databaseConnection) throws SQLException {
        if (this.countStarQuery == null) {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("SELECT COUNT(*) FROM ");
            this.databaseType.appendEscapedEntityName(stringBuilder, this.tableInfo.getTableName());
            this.countStarQuery = stringBuilder.toString();
        }
        long l = databaseConnection.queryForLong(this.countStarQuery);
        logger.debug("query of '{}' returned {}", (Object)this.countStarQuery, (Object)l);
        return l;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public T queryForFirst(DatabaseConnection databaseConnection, PreparedStmt<T> object, ObjectCache object2) throws SQLException {
        CompiledStatement compiledStatement;
        void var1_4;
        block9 : {
            block8 : {
                block6 : {
                    block7 : {
                        compiledStatement = object.compile(databaseConnection, StatementBuilder.StatementType.SELECT);
                        object2 = compiledStatement.runQuery((ObjectCache)object2);
                        if (!object2.first()) break block6;
                        logger.debug("query-for-first of '{}' returned at least 1 result", (Object)object.getStatement());
                        databaseConnection = object.mapRow((DatabaseResults)object2);
                        if (object2 == null) break block7;
                        object2.close();
                    }
                    compiledStatement.close();
                    return (T)databaseConnection;
                }
                try {
                    logger.debug("query-for-first of '{}' returned at 0 results", (Object)object.getStatement());
                    if (object2 == null) break block8;
                }
                catch (Throwable throwable) {
                    object = object2;
                }
                object2.close();
            }
            compiledStatement.close();
            return null;
            break block9;
            catch (Throwable throwable) {
                object = null;
            }
        }
        if (object != null) {
            object.close();
        }
        compiledStatement.close();
        throw var1_4;
    }

    public T queryForId(DatabaseConnection databaseConnection, ID ID, ObjectCache objectCache) throws SQLException {
        if (this.mappedQueryForId == null) {
            this.mappedQueryForId = MappedQueryForId.build(this.databaseType, this.tableInfo, null);
        }
        return this.mappedQueryForId.execute(databaseConnection, ID, objectCache);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long queryForLong(DatabaseConnection object, PreparedStmt<T> preparedStmt) throws SQLException {
        void var2_5;
        CompiledStatement compiledStatement;
        block8 : {
            block6 : {
                long l;
                block7 : {
                    compiledStatement = preparedStmt.compile((DatabaseConnection)object, StatementBuilder.StatementType.SELECT_LONG);
                    object = compiledStatement.runQuery(null);
                    try {
                        if (!object.first()) break block6;
                        l = object.getLong(0);
                        if (object == null) break block7;
                    }
                    catch (Throwable throwable) {}
                    object.close();
                }
                compiledStatement.close();
                return l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No result found in queryForLong: ");
            stringBuilder.append(preparedStmt.getStatement());
            throw new SQLException(stringBuilder.toString());
            break block8;
            catch (Throwable throwable) {
                object = null;
            }
        }
        if (object != null) {
            object.close();
        }
        compiledStatement.close();
        throw var2_5;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long queryForLong(DatabaseConnection object, String object2, String[] object3) throws SQLException {
        block11 : {
            DatabaseResults databaseResults;
            block9 : {
                long l;
                block10 : {
                    logger.debug("executing raw query for long: {}", object2);
                    if (((Object)object3).length > 0) {
                        logger.trace("query arguments: {}", object3);
                    }
                    object = object.compileStatement((String)object2, StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
                    this.assignStatementArguments((CompiledStatement)object, (String[])object3);
                    databaseResults = object.runQuery(null);
                    if (!databaseResults.first()) break block9;
                    l = databaseResults.getLong(0);
                    if (databaseResults == null) break block10;
                    databaseResults.close();
                }
                if (object == null) return l;
                object.close();
                return l;
            }
            try {
                object3 = new StringBuilder();
                object3.append("No result found in queryForLong: ");
                object3.append((String)object2);
                throw new SQLException(object3.toString());
            }
            catch (Throwable throwable) {
                object3 = object;
                object = throwable;
                object2 = databaseResults;
            }
            break block11;
            catch (Throwable throwable) {
                object2 = null;
                object3 = object;
                object = throwable;
            }
            break block11;
            catch (Throwable throwable) {
                object2 = object3 = null;
            }
        }
        if (object2 != null) {
            object2.close();
        }
        if (object3 == null) throw object;
        object3.close();
        throw object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public <UO> GenericRawResults<UO> queryRaw(ConnectionSource connectionSource, String object, RawRowMapper<UO> object2, String[] arrstring, ObjectCache objectCache) throws SQLException {
        void var2_5;
        DatabaseConnection databaseConnection;
        block6 : {
            logger.debug("executing raw query for: {}", object);
            if (arrstring.length > 0) {
                logger.trace("query arguments: {}", (Object)arrstring);
            }
            databaseConnection = connectionSource.getReadOnlyConnection();
            CompiledStatement compiledStatement = databaseConnection.compileStatement((String)((Object)object), StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
            try {
                this.assignStatementArguments(compiledStatement, arrstring);
                return new RawResultsImpl(connectionSource, databaseConnection, (String)((Object)object), String[].class, compiledStatement, new UserRawRowMapper(object2, this), objectCache);
            }
            catch (Throwable throwable) {
                object2 = compiledStatement;
            }
            break block6;
            catch (Throwable throwable) {
                object2 = null;
            }
        }
        if (object2 != null) {
            object2.close();
        }
        if (databaseConnection == null) throw var2_5;
        connectionSource.releaseConnection(databaseConnection);
        throw var2_5;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public <UO> GenericRawResults<UO> queryRaw(ConnectionSource connectionSource, String object, DataType[] arrdataType, RawRowObjectMapper<UO> rawRowObjectMapper, String[] arrstring, ObjectCache objectCache) throws SQLException {
        void var2_5;
        DatabaseConnection databaseConnection;
        block6 : {
            logger.debug("executing raw query for: {}", object);
            if (arrstring.length > 0) {
                logger.trace("query arguments: {}", (Object)arrstring);
            }
            databaseConnection = connectionSource.getReadOnlyConnection();
            CompiledStatement compiledStatement = databaseConnection.compileStatement((String)((Object)object), StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
            try {
                this.assignStatementArguments(compiledStatement, arrstring);
                return new RawResultsImpl<UO>(connectionSource, databaseConnection, (String)((Object)object), String[].class, compiledStatement, new UserRawRowObjectMapper<UO>(rawRowObjectMapper, arrdataType), objectCache);
            }
            catch (Throwable throwable) {
                arrdataType = compiledStatement;
            }
            break block6;
            catch (Throwable throwable) {
                arrdataType = null;
            }
        }
        if (arrdataType != null) {
            arrdataType.close();
        }
        if (databaseConnection == null) throw var2_5;
        connectionSource.releaseConnection(databaseConnection);
        throw var2_5;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public GenericRawResults<Object[]> queryRaw(ConnectionSource connectionSource, String object, DataType[] arrdataType, String[] arrstring, ObjectCache objectCache) throws SQLException {
        void var2_5;
        DatabaseConnection databaseConnection;
        block6 : {
            logger.debug("executing raw query for: {}", object);
            if (arrstring.length > 0) {
                logger.trace("query arguments: {}", (Object)arrstring);
            }
            databaseConnection = connectionSource.getReadOnlyConnection();
            CompiledStatement compiledStatement = databaseConnection.compileStatement((String)((Object)object), StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
            try {
                this.assignStatementArguments(compiledStatement, arrstring);
                return new RawResultsImpl(connectionSource, databaseConnection, (String)((Object)object), Object[].class, compiledStatement, new ObjectArrayRowMapper(arrdataType), objectCache);
            }
            catch (Throwable throwable) {
                arrdataType = compiledStatement;
            }
            break block6;
            catch (Throwable throwable) {
                arrdataType = null;
            }
        }
        if (arrdataType != null) {
            arrdataType.close();
        }
        if (databaseConnection == null) throw var2_5;
        connectionSource.releaseConnection(databaseConnection);
        throw var2_5;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public GenericRawResults<String[]> queryRaw(ConnectionSource connectionSource, String rawResultsImpl, String[] arrstring, ObjectCache objectCache) throws SQLException {
        void var3_6;
        DatabaseConnection databaseConnection;
        block6 : {
            logger.debug("executing raw query for: {}", rawResultsImpl);
            if (arrstring.length > 0) {
                logger.trace("query arguments: {}", (Object)arrstring);
            }
            databaseConnection = connectionSource.getReadOnlyConnection();
            CompiledStatement compiledStatement = databaseConnection.compileStatement((String)((Object)rawResultsImpl), StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
            try {
                this.assignStatementArguments(compiledStatement, arrstring);
                return new RawResultsImpl<String[]>(connectionSource, databaseConnection, (String)((Object)rawResultsImpl), String[].class, compiledStatement, this, objectCache);
            }
            catch (Throwable throwable) {
                rawResultsImpl = compiledStatement;
            }
            break block6;
            catch (Throwable throwable) {
                rawResultsImpl = null;
            }
        }
        if (rawResultsImpl != null) {
            rawResultsImpl.close();
        }
        if (databaseConnection == null) throw var3_6;
        connectionSource.releaseConnection(databaseConnection);
        throw var3_6;
    }

    public int refresh(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) throws SQLException {
        if (this.mappedRefresh == null) {
            this.mappedRefresh = MappedRefresh.build(this.databaseType, this.tableInfo);
        }
        return this.mappedRefresh.executeRefresh(databaseConnection, t, objectCache);
    }

    public int update(DatabaseConnection object, PreparedUpdate<T> preparedUpdate) throws SQLException {
        object = preparedUpdate.compile((DatabaseConnection)object, StatementBuilder.StatementType.UPDATE);
        try {
            int n = object.runUpdate();
            return n;
        }
        finally {
            object.close();
        }
    }

    public int update(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) throws SQLException {
        if (this.mappedUpdate == null) {
            this.mappedUpdate = MappedUpdate.build(this.databaseType, this.tableInfo);
        }
        return this.mappedUpdate.update(databaseConnection, t, objectCache);
    }

    public int updateId(DatabaseConnection databaseConnection, T t, ID ID, ObjectCache objectCache) throws SQLException {
        if (this.mappedUpdateId == null) {
            this.mappedUpdateId = MappedUpdateId.build(this.databaseType, this.tableInfo);
        }
        return this.mappedUpdateId.execute(databaseConnection, t, ID, objectCache);
    }

    public int updateRaw(DatabaseConnection object, String string, String[] arrstring) throws SQLException {
        logger.debug("running raw update statement: {}", (Object)string);
        if (arrstring.length > 0) {
            logger.trace("update arguments: {}", (Object)arrstring);
        }
        object = object.compileStatement(string, StatementBuilder.StatementType.UPDATE, noFieldTypes, -1);
        try {
            this.assignStatementArguments((CompiledStatement)object, arrstring);
            int n = object.runUpdate();
            return n;
        }
        finally {
            object.close();
        }
    }

    private static class ObjectArrayRowMapper
    implements GenericRowMapper<Object[]> {
        private final DataType[] columnTypes;

        public ObjectArrayRowMapper(DataType[] arrdataType) {
            this.columnTypes = arrdataType;
        }

        @Override
        public Object[] mapRow(DatabaseResults databaseResults) throws SQLException {
            int n = databaseResults.getColumnCount();
            Object[] arrobject = new Object[n];
            for (int i = 0; i < n; ++i) {
                DataType dataType = i >= this.columnTypes.length ? DataType.STRING : this.columnTypes[i];
                arrobject[i] = dataType.getDataPersister().resultToJava(null, databaseResults, i);
            }
            return arrobject;
        }
    }

    private static class UserRawRowMapper<UO>
    implements GenericRowMapper<UO> {
        private String[] columnNames;
        private final RawRowMapper<UO> mapper;
        private final GenericRowMapper<String[]> stringRowMapper;

        public UserRawRowMapper(RawRowMapper<UO> rawRowMapper, GenericRowMapper<String[]> genericRowMapper) {
            this.mapper = rawRowMapper;
            this.stringRowMapper = genericRowMapper;
        }

        private String[] getColumnNames(DatabaseResults databaseResults) throws SQLException {
            if (this.columnNames != null) {
                return this.columnNames;
            }
            this.columnNames = databaseResults.getColumnNames();
            return this.columnNames;
        }

        @Override
        public UO mapRow(DatabaseResults databaseResults) throws SQLException {
            String[] arrstring = this.stringRowMapper.mapRow(databaseResults);
            return this.mapper.mapRow(this.getColumnNames(databaseResults), arrstring);
        }
    }

    private static class UserRawRowObjectMapper<UO>
    implements GenericRowMapper<UO> {
        private String[] columnNames;
        private final DataType[] columnTypes;
        private final RawRowObjectMapper<UO> mapper;

        public UserRawRowObjectMapper(RawRowObjectMapper<UO> rawRowObjectMapper, DataType[] arrdataType) {
            this.mapper = rawRowObjectMapper;
            this.columnTypes = arrdataType;
        }

        private String[] getColumnNames(DatabaseResults databaseResults) throws SQLException {
            if (this.columnNames != null) {
                return this.columnNames;
            }
            this.columnNames = databaseResults.getColumnNames();
            return this.columnNames;
        }

        @Override
        public UO mapRow(DatabaseResults databaseResults) throws SQLException {
            int n = databaseResults.getColumnCount();
            Object[] arrobject = new Object[n];
            for (int i = 0; i < n; ++i) {
                arrobject[i] = i >= this.columnTypes.length ? null : this.columnTypes[i].getDataPersister().resultToJava(null, databaseResults, i);
            }
            return this.mapper.mapRow(this.getColumnNames(databaseResults), this.columnTypes, arrobject);
        }
    }

}
