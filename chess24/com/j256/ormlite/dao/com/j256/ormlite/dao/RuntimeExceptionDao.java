/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class RuntimeExceptionDao<T, ID>
implements CloseableIterable<T> {
    private static final Log.Level LOG_LEVEL = Log.Level.DEBUG;
    private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptionDao.class);
    private Dao<T, ID> dao;

    public RuntimeExceptionDao(Dao<T, ID> dao) {
        this.dao = dao;
    }

    public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        return new RuntimeExceptionDao<T, ID>((Dao<T, ID>)DaoManager.createDao(connectionSource, databaseTableConfig));
    }

    public static <T, ID> RuntimeExceptionDao<T, ID> createDao(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        return new RuntimeExceptionDao<T, ID>((Dao<T, ID>)DaoManager.createDao(connectionSource, class_));
    }

    private void logMessage(Exception exception, String string) {
        logger.log(LOG_LEVEL, exception, string);
    }

    public void assignEmptyForeignCollection(T t, String string) {
        try {
            this.dao.assignEmptyForeignCollection(t, string);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("assignEmptyForeignCollection threw exception on ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public <CT> CT callBatchTasks(Callable<CT> callable) {
        CT CT;
        try {
            CT = this.dao.callBatchTasks(callable);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callBatchTasks threw exception on: ");
            stringBuilder.append(callable);
            this.logMessage(exception, stringBuilder.toString());
            throw new RuntimeException(exception);
        }
        return CT;
    }

    public void clearObjectCache() {
        this.dao.clearObjectCache();
    }

    public void closeLastIterator() {
        try {
            this.dao.closeLastIterator();
            return;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "closeLastIterator threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return this.dao.closeableIterator();
    }

    public void commit(DatabaseConnection databaseConnection) {
        try {
            this.dao.commit(databaseConnection);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("commit(");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public long countOf() {
        try {
            long l = this.dao.countOf();
            return l;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "countOf threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public long countOf(PreparedQuery<T> preparedQuery) {
        try {
            long l = this.dao.countOf(preparedQuery);
            return l;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("countOf threw exception on ");
            stringBuilder.append(preparedQuery);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int create(T t) {
        try {
            int n = this.dao.create(t);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("create threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public T createIfNotExists(T t) {
        T t2;
        try {
            t2 = this.dao.createIfNotExists(t);
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("createIfNotExists threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
        return t2;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(T t) {
        try {
            Dao.CreateOrUpdateStatus createOrUpdateStatus = this.dao.createOrUpdate(t);
            return createOrUpdateStatus;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("createOrUpdate threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int delete(PreparedDelete<T> preparedDelete) {
        try {
            int n = this.dao.delete(preparedDelete);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("delete threw exception on: ");
            stringBuilder.append(preparedDelete);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int delete(T t) {
        try {
            int n = this.dao.delete(t);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("delete threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int delete(Collection<T> collection) {
        try {
            int n = this.dao.delete(collection);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("delete threw exception on: ");
            stringBuilder.append(collection);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public DeleteBuilder<T, ID> deleteBuilder() {
        return this.dao.deleteBuilder();
    }

    public int deleteById(ID ID) {
        try {
            int n = this.dao.deleteById(ID);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("deleteById threw exception on: ");
            stringBuilder.append(ID);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int deleteIds(Collection<ID> collection) {
        try {
            int n = this.dao.deleteIds(collection);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("deleteIds threw exception on: ");
            stringBuilder.append(collection);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void endThreadConnection(DatabaseConnection databaseConnection) {
        try {
            this.dao.endThreadConnection(databaseConnection);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endThreadConnection(");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ int executeRaw(String string, String ... arrstring) {
        try {
            int n = this.dao.executeRaw(string, arrstring);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("executeRaw threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int executeRawNoArgs(String string) {
        try {
            int n = this.dao.executeRawNoArgs(string);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("executeRawNoArgs threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public ID extractId(T t) {
        ID ID;
        try {
            ID = this.dao.extractId(t);
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("extractId threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
        return ID;
    }

    public FieldType findForeignFieldType(Class<?> class_) {
        return this.dao.findForeignFieldType(class_);
    }

    public ConnectionSource getConnectionSource() {
        return this.dao.getConnectionSource();
    }

    public Class<T> getDataClass() {
        return this.dao.getDataClass();
    }

    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String string) {
        try {
            ForeignCollection foreignCollection = this.dao.getEmptyForeignCollection(string);
            return foreignCollection;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getEmptyForeignCollection threw exception on ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public ObjectCache getObjectCache() {
        return this.dao.getObjectCache();
    }

    public RawRowMapper<T> getRawRowMapper() {
        return this.dao.getRawRowMapper();
    }

    public GenericRowMapper<T> getSelectStarRowMapper() {
        try {
            GenericRowMapper<T> genericRowMapper = this.dao.getSelectStarRowMapper();
            return genericRowMapper;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "getSelectStarRowMapper threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this.dao.getWrappedIterable();
    }

    public CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> preparedQuery) {
        return this.dao.getWrappedIterable(preparedQuery);
    }

    public boolean idExists(ID ID) {
        try {
            boolean bl = this.dao.idExists(ID);
            return bl;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("idExists threw exception on ");
            stringBuilder.append(ID);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    @Deprecated
    public boolean isAutoCommit() {
        try {
            boolean bl = this.dao.isAutoCommit();
            return bl;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "isAutoCommit() threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public boolean isAutoCommit(DatabaseConnection databaseConnection) {
        try {
            boolean bl = this.dao.isAutoCommit(databaseConnection);
            return bl;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isAutoCommit(");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public boolean isTableExists() {
        try {
            boolean bl = this.dao.isTableExists();
            return bl;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "isTableExists threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public boolean isUpdatable() {
        return this.dao.isUpdatable();
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.dao.iterator();
    }

    public CloseableIterator<T> iterator(int n) {
        return this.dao.iterator(n);
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) {
        try {
            CloseableIterator<T> closeableIterator = this.dao.iterator(preparedQuery);
            return closeableIterator;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("iterator threw exception on: ");
            stringBuilder.append(preparedQuery);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int n) {
        try {
            CloseableIterator<T> closeableIterator = this.dao.iterator(preparedQuery, n);
            return closeableIterator;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("iterator threw exception on: ");
            stringBuilder.append(preparedQuery);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public T mapSelectStarRow(DatabaseResults databaseResults) {
        try {
            databaseResults = this.dao.mapSelectStarRow(databaseResults);
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "mapSelectStarRow threw exception on results");
            throw new RuntimeException(sQLException);
        }
        return (T)databaseResults;
    }

    public String objectToString(T t) {
        return this.dao.objectToString(t);
    }

    public boolean objectsEqual(T t, T t2) {
        try {
            boolean bl = this.dao.objectsEqual(t, t2);
            return bl;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("objectsEqual threw exception on: ");
            stringBuilder.append(t);
            stringBuilder.append(" and ");
            stringBuilder.append(t2);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public List<T> query(PreparedQuery<T> preparedQuery) {
        try {
            List<T> list = this.dao.query(preparedQuery);
            return list;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("query threw exception on: ");
            stringBuilder.append(preparedQuery);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public QueryBuilder<T, ID> queryBuilder() {
        return this.dao.queryBuilder();
    }

    public List<T> queryForAll() {
        try {
            List<T> list = this.dao.queryForAll();
            return list;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "queryForAll threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public List<T> queryForEq(String string, Object list) {
        try {
            list = this.dao.queryForEq(string, list);
            return list;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForEq threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public List<T> queryForFieldValues(Map<String, Object> object) {
        try {
            object = this.dao.queryForFieldValues((Map<String, Object>)object);
            return object;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "queryForFieldValues threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public List<T> queryForFieldValuesArgs(Map<String, Object> object) {
        try {
            object = this.dao.queryForFieldValuesArgs((Map<String, Object>)object);
            return object;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "queryForFieldValuesArgs threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public T queryForFirst(PreparedQuery<T> preparedQuery) {
        T t;
        try {
            t = this.dao.queryForFirst(preparedQuery);
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForFirst threw exception on: ");
            stringBuilder.append(preparedQuery);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
        return t;
    }

    public T queryForId(ID ID) {
        T t;
        try {
            t = this.dao.queryForId(ID);
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForId threw exception on: ");
            stringBuilder.append(ID);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
        return t;
    }

    public List<T> queryForMatching(T t) {
        try {
            List<T> list = this.dao.queryForMatching(t);
            return list;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForMatching threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public List<T> queryForMatchingArgs(T t) {
        try {
            List<T> list = this.dao.queryForMatchingArgs(t);
            return list;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForMatchingArgs threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public T queryForSameId(T t) {
        T t2;
        try {
            t2 = this.dao.queryForSameId(t);
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryForSameId threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
        return t2;
    }

    public /* varargs */ <UO> GenericRawResults<UO> queryRaw(String string, RawRowMapper<UO> object, String ... object2) {
        try {
            object = this.dao.queryRaw(string, object, (String[])object2);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("queryRaw threw exception on: ");
            object2.append(string);
            this.logMessage(sQLException, object2.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ <UO> GenericRawResults<UO> queryRaw(String string, DataType[] object, RawRowObjectMapper<UO> object2, String ... arrstring) {
        try {
            object = this.dao.queryRaw(string, (DataType[])object, object2, arrstring);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("queryRaw threw exception on: ");
            object2.append(string);
            this.logMessage(sQLException, object2.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ GenericRawResults<Object[]> queryRaw(String string, DataType[] object, String ... object2) {
        try {
            object = this.dao.queryRaw(string, (DataType[])object, (String[])object2);
            return object;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("queryRaw threw exception on: ");
            object2.append(string);
            this.logMessage(sQLException, object2.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ GenericRawResults<String[]> queryRaw(String string, String ... object) {
        try {
            object = this.dao.queryRaw(string, (String)object);
            return object;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryRaw threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ long queryRawValue(String string, String ... arrstring) {
        try {
            long l = this.dao.queryRawValue(string, arrstring);
            return l;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queryRawValue threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int refresh(T t) {
        try {
            int n = this.dao.refresh(t);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("refresh threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void rollBack(DatabaseConnection databaseConnection) {
        try {
            this.dao.rollBack(databaseConnection);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("rollBack(");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void setAutoCommit(DatabaseConnection databaseConnection, boolean bl) {
        try {
            this.dao.setAutoCommit(databaseConnection, bl);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAutoCommit(");
            stringBuilder.append(databaseConnection);
            stringBuilder.append(",");
            stringBuilder.append(bl);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    @Deprecated
    public void setAutoCommit(boolean bl) {
        try {
            this.dao.setAutoCommit(bl);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAutoCommit(");
            stringBuilder.append(bl);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void setObjectCache(ObjectCache objectCache) {
        try {
            this.dao.setObjectCache(objectCache);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setObjectCache threw exception on ");
            stringBuilder.append(objectCache);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void setObjectCache(boolean bl) {
        try {
            this.dao.setObjectCache(bl);
            return;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setObjectCache(");
            stringBuilder.append(bl);
            stringBuilder.append(") threw exception");
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public void setObjectFactory(ObjectFactory<T> objectFactory) {
        this.dao.setObjectFactory(objectFactory);
    }

    public DatabaseConnection startThreadConnection() {
        try {
            DatabaseConnection databaseConnection = this.dao.startThreadConnection();
            return databaseConnection;
        }
        catch (SQLException sQLException) {
            this.logMessage(sQLException, "startThreadConnection() threw exception");
            throw new RuntimeException(sQLException);
        }
    }

    public int update(PreparedUpdate<T> preparedUpdate) {
        try {
            int n = this.dao.update(preparedUpdate);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update threw exception on: ");
            stringBuilder.append(preparedUpdate);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public int update(T t) {
        try {
            int n = this.dao.update(t);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public UpdateBuilder<T, ID> updateBuilder() {
        return this.dao.updateBuilder();
    }

    public int updateId(T t, ID ID) {
        try {
            int n = this.dao.updateId(t, ID);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateId threw exception on: ");
            stringBuilder.append(t);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }

    public /* varargs */ int updateRaw(String string, String ... arrstring) {
        try {
            int n = this.dao.updateRaw(string, arrstring);
            return n;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateRaw threw exception on: ");
            stringBuilder.append(string);
            this.logMessage(sQLException, stringBuilder.toString());
            throw new RuntimeException(sQLException);
        }
    }
}
