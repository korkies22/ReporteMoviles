/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.CloseableWrappedIterableImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.dao.ReferenceObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.SelectIterator;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public abstract class BaseDaoImpl<T, ID>
implements Dao<T, ID> {
    private static final ThreadLocal<List<BaseDaoImpl<?, ?>>> daoConfigLevelLocal = new ThreadLocal<List<BaseDaoImpl<?, ?>>>(){

        @Override
        protected List<BaseDaoImpl<?, ?>> initialValue() {
            return new ArrayList(10);
        }
    };
    private static ReferenceObjectCache defaultObjectCache;
    protected ConnectionSource connectionSource;
    protected final Class<T> dataClass;
    protected DatabaseType databaseType;
    private boolean initialized;
    protected CloseableIterator<T> lastIterator;
    private ObjectCache objectCache;
    protected ObjectFactory<T> objectFactory;
    protected StatementExecutor<T, ID> statementExecutor;
    protected DatabaseTableConfig<T> tableConfig;
    protected TableInfo<T, ID> tableInfo;

    protected BaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        this(connectionSource, databaseTableConfig.getDataClass(), databaseTableConfig);
    }

    protected BaseDaoImpl(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        this(connectionSource, class_, null);
    }

    private BaseDaoImpl(ConnectionSource connectionSource, Class<T> class_, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        this.dataClass = class_;
        this.tableConfig = databaseTableConfig;
        if (connectionSource != null) {
            this.connectionSource = connectionSource;
            this.initialize();
        }
    }

    protected BaseDaoImpl(Class<T> class_) throws SQLException {
        this(null, class_, null);
    }

    public static void clearAllInternalObjectCaches() {
        synchronized (BaseDaoImpl.class) {
            if (defaultObjectCache != null) {
                defaultObjectCache.clearAll();
                defaultObjectCache = null;
            }
            return;
        }
    }

    static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        return new BaseDaoImpl<T, ID>(connectionSource, databaseTableConfig){};
    }

    static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return new BaseDaoImpl<T, ID>(connectionSource, class_){};
    }

    private CloseableIterator<T> createIterator(int n) {
        try {
            SelectIterator<T, ID> selectIterator = this.statementExecutor.buildIterator(this, this.connectionSource, n, this.objectCache);
            return selectIterator;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build iterator for ");
            stringBuilder.append(this.dataClass);
            throw new IllegalStateException(stringBuilder.toString(), exception);
        }
    }

    private CloseableIterator<T> createIterator(PreparedQuery<T> object, int n) throws SQLException {
        try {
            object = this.statementExecutor.buildIterator(this, this.connectionSource, (PreparedStmt<T>)object, this.objectCache, n);
            return object;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build prepared-query iterator for ");
            stringBuilder.append(this.dataClass);
            throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
        }
    }

    private <FT> ForeignCollection<FT> makeEmptyForeignCollection(T object, String object2) throws SQLException {
        this.checkForInitialized();
        Object FID = object == null ? null : (Object)this.extractId(object);
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (!fieldType.getColumnName().equals(object2)) continue;
            object2 = fieldType.buildForeignCollection(object, FID);
            if (object != null) {
                fieldType.assignField(object, object2, true, null);
            }
            return object2;
        }
        object = new StringBuilder();
        object.append("Could not find a field named ");
        object.append((String)object2);
        throw new IllegalArgumentException(object.toString());
    }

    private List<T> queryForFieldValues(Map<String, Object> map, boolean bl) throws SQLException {
        this.checkForInitialized();
        QueryBuilder<T, ID> queryBuilder = this.queryBuilder();
        Where where = queryBuilder.where();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object object;
            Object object2 = object = entry.getValue();
            if (bl) {
                object2 = new SelectArg(object);
            }
            where.eq(entry.getKey(), object2);
        }
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        where.and(map.size());
        return queryBuilder.query();
    }

    private List<T> queryForMatching(T t, boolean bl) throws SQLException {
        this.checkForInitialized();
        QueryBuilder<T, ID> queryBuilder = this.queryBuilder();
        Where where = queryBuilder.where();
        FieldType[] arrfieldType = this.tableInfo.getFieldTypes();
        int n = arrfieldType.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            FieldType fieldType = arrfieldType[i];
            Object FV = fieldType.getFieldValueIfNotDefault(t);
            int n3 = n2;
            if (FV != null) {
                Object object = FV;
                if (bl) {
                    object = new SelectArg(FV);
                }
                where.eq(fieldType.getColumnName(), object);
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 == 0) {
            return Collections.emptyList();
        }
        where.and(n2);
        return queryBuilder.query();
    }

    @Override
    public void assignEmptyForeignCollection(T t, String string) throws SQLException {
        this.makeEmptyForeignCollection(t, string);
    }

    @Override
    public <CT> CT callBatchTasks(Callable<CT> callable) throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            boolean bl = this.connectionSource.saveSpecialConnection(databaseConnection);
            callable = this.statementExecutor.callBatchTasks(databaseConnection, bl, callable);
            return (CT)callable;
        }
        finally {
            this.connectionSource.clearSpecialConnection(databaseConnection);
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    protected void checkForInitialized() {
        if (!this.initialized) {
            throw new IllegalStateException("you must call initialize() before you can use the dao");
        }
    }

    @Override
    public void clearObjectCache() {
        if (this.objectCache != null) {
            this.objectCache.clear(this.dataClass);
        }
    }

    @Override
    public void closeLastIterator() throws SQLException {
        if (this.lastIterator != null) {
            this.lastIterator.close();
            this.lastIterator = null;
        }
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return this.iterator(-1);
    }

    @Override
    public void commit(DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.commit(null);
    }

    @Override
    public long countOf() throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            long l = this.statementExecutor.queryForCountStar(databaseConnection);
            return l;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public long countOf(PreparedQuery<T> object) throws SQLException {
        this.checkForInitialized();
        if (object.getType() != StatementBuilder.StatementType.SELECT_LONG) {
            object = new StringBuilder();
            object.append("Prepared query is not of type ");
            object.append((Object)StatementBuilder.StatementType.SELECT_LONG);
            object.append(", did you call QueryBuilder.setCountOf(true)?");
            throw new IllegalArgumentException(object.toString());
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            long l = this.statementExecutor.queryForLong(databaseConnection, (PreparedStmt<T>)object);
            return l;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public int create(T t) throws SQLException {
        this.checkForInitialized();
        if (t == null) {
            return 0;
        }
        if (t instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled)t).setDao(this);
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.create(databaseConnection, t, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public T createIfNotExists(T t) throws SQLException {
        if (t == null) {
            return null;
        }
        T t2 = this.queryForSameId(t);
        if (t2 == null) {
            this.create(t);
            return t;
        }
        return t2;
    }

    @Override
    public Dao.CreateOrUpdateStatus createOrUpdate(T t) throws SQLException {
        if (t == null) {
            return new Dao.CreateOrUpdateStatus(false, false, 0);
        }
        ID ID = this.extractId(t);
        if (ID != null && this.idExists(ID)) {
            return new Dao.CreateOrUpdateStatus(false, true, this.update(t));
        }
        return new Dao.CreateOrUpdateStatus(true, false, this.create(t));
    }

    @Override
    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.delete(databaseConnection, preparedDelete);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public int delete(T t) throws SQLException {
        this.checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.delete(databaseConnection, t, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public int delete(Collection<T> collection) throws SQLException {
        this.checkForInitialized();
        if (collection != null && !collection.isEmpty()) {
            DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
            try {
                int n = this.statementExecutor.deleteObjects(databaseConnection, collection, this.objectCache);
                return n;
            }
            finally {
                this.connectionSource.releaseConnection(databaseConnection);
            }
        }
        return 0;
    }

    @Override
    public DeleteBuilder<T, ID> deleteBuilder() {
        this.checkForInitialized();
        return new DeleteBuilder<T, ID>(this.databaseType, this.tableInfo, this);
    }

    @Override
    public int deleteById(ID ID) throws SQLException {
        this.checkForInitialized();
        if (ID == null) {
            return 0;
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.deleteById(databaseConnection, ID, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public int deleteIds(Collection<ID> collection) throws SQLException {
        this.checkForInitialized();
        if (collection != null && !collection.isEmpty()) {
            DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
            try {
                int n = this.statementExecutor.deleteIds(databaseConnection, collection, this.objectCache);
                return n;
            }
            finally {
                this.connectionSource.releaseConnection(databaseConnection);
            }
        }
        return 0;
    }

    @Override
    public void endThreadConnection(DatabaseConnection databaseConnection) throws SQLException {
        this.connectionSource.clearSpecialConnection(databaseConnection);
        this.connectionSource.releaseConnection(databaseConnection);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public /* varargs */ int executeRaw(String string, String ... arrstring) throws SQLException {
        Throwable throwable2222;
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        int n = this.statementExecutor.executeRaw(databaseConnection, string, arrstring);
        this.connectionSource.releaseConnection(databaseConnection);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLException sQLException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not run raw execute statement ");
                stringBuilder.append(string);
                throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
            }
        }
        this.connectionSource.releaseConnection(databaseConnection);
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int executeRawNoArgs(String string) throws SQLException {
        Throwable throwable2222;
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        int n = this.statementExecutor.executeRawNoArgs(databaseConnection, string);
        this.connectionSource.releaseConnection(databaseConnection);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLException sQLException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not run raw execute statement ");
                stringBuilder.append(string);
                throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
            }
        }
        this.connectionSource.releaseConnection(databaseConnection);
        throw throwable2222;
    }

    @Override
    public ID extractId(T object) throws SQLException {
        this.checkForInitialized();
        FieldType fieldType = this.tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Class ");
            object.append(this.dataClass);
            object.append(" does not have an id field");
            throw new SQLException(object.toString());
        }
        return (ID)fieldType.extractJavaFieldValue(object);
    }

    @Override
    public FieldType findForeignFieldType(Class<?> class_) {
        this.checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (fieldType.getType() != class_) continue;
            return fieldType;
        }
        return null;
    }

    @Override
    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    @Override
    public Class<T> getDataClass() {
        return this.dataClass;
    }

    @Override
    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String string) throws SQLException {
        return this.makeEmptyForeignCollection(null, string);
    }

    @Override
    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public ObjectFactory<T> getObjectFactory() {
        return this.objectFactory;
    }

    @Override
    public RawRowMapper<T> getRawRowMapper() {
        return this.statementExecutor.getRawRowMapper();
    }

    @Override
    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException {
        return this.statementExecutor.getSelectStarRowMapper();
    }

    public DatabaseTableConfig<T> getTableConfig() {
        return this.tableConfig;
    }

    public TableInfo<T, ID> getTableInfo() {
        return this.tableInfo;
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable() {
        this.checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>(){

            @Override
            public CloseableIterator<T> closeableIterator() {
                try {
                    CloseableIterator closeableIterator = BaseDaoImpl.this.createIterator(-1);
                    return closeableIterator;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not build iterator for ");
                    stringBuilder.append(BaseDaoImpl.this.dataClass);
                    throw new IllegalStateException(stringBuilder.toString(), exception);
                }
            }

            @Override
            public Iterator<T> iterator() {
                return this.closeableIterator();
            }
        });
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable(final PreparedQuery<T> preparedQuery) {
        this.checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>(){

            @Override
            public CloseableIterator<T> closeableIterator() {
                try {
                    CloseableIterator closeableIterator = BaseDaoImpl.this.createIterator(preparedQuery, -1);
                    return closeableIterator;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not build prepared-query iterator for ");
                    stringBuilder.append(BaseDaoImpl.this.dataClass);
                    throw new IllegalStateException(stringBuilder.toString(), exception);
                }
            }

            @Override
            public Iterator<T> iterator() {
                return this.closeableIterator();
            }
        });
    }

    @Override
    public boolean idExists(ID ID) throws SQLException {
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            boolean bl = this.statementExecutor.ifExists(databaseConnection, ID);
            return bl;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    /*
     * Exception decompiling
     */
    public void initialize() throws SQLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public boolean isAutoCommit() throws SQLException {
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            boolean bl = this.isAutoCommit(databaseConnection);
            return bl;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public boolean isAutoCommit(DatabaseConnection databaseConnection) throws SQLException {
        return databaseConnection.isAutoCommit();
    }

    @Override
    public boolean isTableExists() throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            boolean bl = databaseConnection.isTableExists(this.tableInfo.getTableName());
            return bl;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public boolean isUpdatable() {
        return this.tableInfo.isUpdatable();
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.iterator(-1);
    }

    @Override
    public CloseableIterator<T> iterator(int n) {
        this.checkForInitialized();
        this.lastIterator = this.createIterator(n);
        return this.lastIterator;
    }

    @Override
    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) throws SQLException {
        return this.iterator(preparedQuery, -1);
    }

    @Override
    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int n) throws SQLException {
        this.checkForInitialized();
        this.lastIterator = this.createIterator(preparedQuery, n);
        return this.lastIterator;
    }

    @Override
    public T mapSelectStarRow(DatabaseResults databaseResults) throws SQLException {
        return this.statementExecutor.getSelectStarRowMapper().mapRow(databaseResults);
    }

    @Override
    public String objectToString(T t) {
        this.checkForInitialized();
        return this.tableInfo.objectToString(t);
    }

    @Override
    public boolean objectsEqual(T t, T t2) throws SQLException {
        this.checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            Object object = fieldType.extractJavaFieldValue(t);
            Object object2 = fieldType.extractJavaFieldValue(t2);
            if (fieldType.getDataPersister().dataIsEqual(object, object2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        this.checkForInitialized();
        return this.statementExecutor.query(this.connectionSource, preparedQuery, this.objectCache);
    }

    @Override
    public QueryBuilder<T, ID> queryBuilder() {
        this.checkForInitialized();
        return new QueryBuilder<T, ID>(this.databaseType, this.tableInfo, this);
    }

    @Override
    public List<T> queryForAll() throws SQLException {
        this.checkForInitialized();
        return this.statementExecutor.queryForAll(this.connectionSource, this.objectCache);
    }

    @Override
    public List<T> queryForEq(String string, Object object) throws SQLException {
        return this.queryBuilder().where().eq(string, object).query();
    }

    @Override
    public List<T> queryForFieldValues(Map<String, Object> map) throws SQLException {
        return this.queryForFieldValues(map, false);
    }

    @Override
    public List<T> queryForFieldValuesArgs(Map<String, Object> map) throws SQLException {
        return this.queryForFieldValues(map, true);
    }

    @Override
    public T queryForFirst(PreparedQuery<T> preparedQuery) throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            preparedQuery = this.statementExecutor.queryForFirst(databaseConnection, preparedQuery, this.objectCache);
            return (T)preparedQuery;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public T queryForId(ID object) throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            object = this.statementExecutor.queryForId(databaseConnection, object, this.objectCache);
            return (T)object;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public List<T> queryForMatching(T t) throws SQLException {
        return this.queryForMatching(t, false);
    }

    @Override
    public List<T> queryForMatchingArgs(T t) throws SQLException {
        return this.queryForMatching(t, true);
    }

    @Override
    public T queryForSameId(T object) throws SQLException {
        this.checkForInitialized();
        if (object == null) {
            return null;
        }
        if ((object = this.extractId(object)) == null) {
            return null;
        }
        return this.queryForId(object);
    }

    @Override
    public /* varargs */ <GR> GenericRawResults<GR> queryRaw(String string, RawRowMapper<GR> object, String ... object2) throws SQLException {
        this.checkForInitialized();
        try {
            object = this.statementExecutor.queryRaw(this.connectionSource, string, object, (String[])object2, this.objectCache);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("Could not perform raw query for ");
            object2.append(string);
            throw SqlExceptionUtil.create(object2.toString(), sQLException);
        }
    }

    @Override
    public /* varargs */ <UO> GenericRawResults<UO> queryRaw(String string, DataType[] object, RawRowObjectMapper<UO> object2, String ... arrstring) throws SQLException {
        this.checkForInitialized();
        try {
            object = this.statementExecutor.queryRaw(this.connectionSource, string, (DataType[])object, object2, arrstring, this.objectCache);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("Could not perform raw query for ");
            object2.append(string);
            throw SqlExceptionUtil.create(object2.toString(), sQLException);
        }
    }

    @Override
    public /* varargs */ GenericRawResults<Object[]> queryRaw(String string, DataType[] object, String ... object2) throws SQLException {
        this.checkForInitialized();
        try {
            object = this.statementExecutor.queryRaw(this.connectionSource, string, (DataType[])object, (String[])object2, this.objectCache);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("Could not perform raw query for ");
            object2.append(string);
            throw SqlExceptionUtil.create(object2.toString(), sQLException);
        }
    }

    @Override
    public /* varargs */ GenericRawResults<String[]> queryRaw(String string, String ... object) throws SQLException {
        this.checkForInitialized();
        try {
            object = this.statementExecutor.queryRaw(this.connectionSource, string, (String[])object, this.objectCache);
            return object;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not perform raw query for ");
            stringBuilder.append(string);
            throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public /* varargs */ long queryRawValue(String string, String ... arrstring) throws SQLException {
        Throwable throwable2222;
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        long l = this.statementExecutor.queryForLong(databaseConnection, string, arrstring);
        this.connectionSource.releaseConnection(databaseConnection);
        return l;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLException sQLException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not perform raw value query for ");
                stringBuilder.append(string);
                throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
            }
        }
        this.connectionSource.releaseConnection(databaseConnection);
        throw throwable2222;
    }

    @Override
    public int refresh(T t) throws SQLException {
        this.checkForInitialized();
        if (t == null) {
            return 0;
        }
        if (t instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled)t).setDao(this);
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadOnlyConnection();
        try {
            int n = this.statementExecutor.refresh(databaseConnection, t, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public void rollBack(DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.rollback(null);
    }

    @Override
    public void setAutoCommit(DatabaseConnection databaseConnection, boolean bl) throws SQLException {
        databaseConnection.setAutoCommit(bl);
    }

    @Override
    public void setAutoCommit(boolean bl) throws SQLException {
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            this.setAutoCommit(databaseConnection, bl);
            return;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void setObjectCache(ObjectCache object) throws SQLException {
        if (object == null) {
            if (this.objectCache != null) {
                this.objectCache.clear(this.dataClass);
                this.objectCache = null;
                return;
            }
        } else {
            if (this.objectCache != null && this.objectCache != object) {
                this.objectCache.clear(this.dataClass);
            }
            if (this.tableInfo.getIdField() == null) {
                object = new StringBuilder();
                object.append("Class ");
                object.append(this.dataClass);
                object.append(" must have an id field to enable the object cache");
                throw new SQLException(object.toString());
            }
            this.objectCache = object;
            this.objectCache.registerClass(this.dataClass);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setObjectCache(boolean bl) throws SQLException {
        if (bl) {
            if (this.objectCache != null) return;
            {
                if (this.tableInfo.getIdField() == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Class ");
                    stringBuilder.append(this.dataClass);
                    stringBuilder.append(" must have an id field to enable the object cache");
                    throw new SQLException(stringBuilder.toString());
                }
                synchronized (BaseDaoImpl.class) {
                    if (defaultObjectCache == null) {
                        defaultObjectCache = ReferenceObjectCache.makeWeakCache();
                    }
                    this.objectCache = defaultObjectCache;
                }
                this.objectCache.registerClass(this.dataClass);
                return;
            }
        } else {
            if (this.objectCache == null) return;
            {
                this.objectCache.clear(this.dataClass);
                this.objectCache = null;
            }
        }
    }

    @Override
    public void setObjectFactory(ObjectFactory<T> objectFactory) {
        this.checkForInitialized();
        this.objectFactory = objectFactory;
    }

    public void setTableConfig(DatabaseTableConfig<T> databaseTableConfig) {
        this.tableConfig = databaseTableConfig;
    }

    @Override
    public DatabaseConnection startThreadConnection() throws SQLException {
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        this.connectionSource.saveSpecialConnection(databaseConnection);
        return databaseConnection;
    }

    @Override
    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException {
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.update(databaseConnection, preparedUpdate);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public int update(T t) throws SQLException {
        this.checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.update(databaseConnection, t, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    @Override
    public UpdateBuilder<T, ID> updateBuilder() {
        this.checkForInitialized();
        return new UpdateBuilder<T, ID>(this.databaseType, this.tableInfo, this);
    }

    @Override
    public int updateId(T t, ID ID) throws SQLException {
        this.checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        try {
            int n = this.statementExecutor.updateId(databaseConnection, t, ID, this.objectCache);
            return n;
        }
        finally {
            this.connectionSource.releaseConnection(databaseConnection);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public /* varargs */ int updateRaw(String string, String ... arrstring) throws SQLException {
        Throwable throwable2222;
        this.checkForInitialized();
        DatabaseConnection databaseConnection = this.connectionSource.getReadWriteConnection();
        int n = this.statementExecutor.updateRaw(databaseConnection, string, arrstring);
        this.connectionSource.releaseConnection(databaseConnection);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLException sQLException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not run raw update statement ");
                stringBuilder.append(string);
                throw SqlExceptionUtil.create(stringBuilder.toString(), sQLException);
            }
        }
        this.connectionSource.releaseConnection(databaseConnection);
        throw throwable2222;
    }

}
