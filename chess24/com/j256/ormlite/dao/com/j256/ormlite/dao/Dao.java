/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
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
import com.j256.ormlite.table.ObjectFactory;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface Dao<T, ID>
extends CloseableIterable<T> {
    public void assignEmptyForeignCollection(T var1, String var2) throws SQLException;

    public <CT> CT callBatchTasks(Callable<CT> var1) throws Exception;

    public void clearObjectCache();

    public void closeLastIterator() throws SQLException;

    public void commit(DatabaseConnection var1) throws SQLException;

    public long countOf() throws SQLException;

    public long countOf(PreparedQuery<T> var1) throws SQLException;

    public int create(T var1) throws SQLException;

    public T createIfNotExists(T var1) throws SQLException;

    public CreateOrUpdateStatus createOrUpdate(T var1) throws SQLException;

    public int delete(PreparedDelete<T> var1) throws SQLException;

    public int delete(T var1) throws SQLException;

    public int delete(Collection<T> var1) throws SQLException;

    public DeleteBuilder<T, ID> deleteBuilder();

    public int deleteById(ID var1) throws SQLException;

    public int deleteIds(Collection<ID> var1) throws SQLException;

    public void endThreadConnection(DatabaseConnection var1) throws SQLException;

    public /* varargs */ int executeRaw(String var1, String ... var2) throws SQLException;

    public int executeRawNoArgs(String var1) throws SQLException;

    public ID extractId(T var1) throws SQLException;

    public FieldType findForeignFieldType(Class<?> var1);

    public ConnectionSource getConnectionSource();

    public Class<T> getDataClass();

    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String var1) throws SQLException;

    public ObjectCache getObjectCache();

    public RawRowMapper<T> getRawRowMapper();

    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException;

    public CloseableWrappedIterable<T> getWrappedIterable();

    public CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> var1);

    public boolean idExists(ID var1) throws SQLException;

    @Deprecated
    public boolean isAutoCommit() throws SQLException;

    public boolean isAutoCommit(DatabaseConnection var1) throws SQLException;

    public boolean isTableExists() throws SQLException;

    public boolean isUpdatable();

    @Override
    public CloseableIterator<T> iterator();

    public CloseableIterator<T> iterator(int var1);

    public CloseableIterator<T> iterator(PreparedQuery<T> var1) throws SQLException;

    public CloseableIterator<T> iterator(PreparedQuery<T> var1, int var2) throws SQLException;

    public T mapSelectStarRow(DatabaseResults var1) throws SQLException;

    public String objectToString(T var1);

    public boolean objectsEqual(T var1, T var2) throws SQLException;

    public List<T> query(PreparedQuery<T> var1) throws SQLException;

    public QueryBuilder<T, ID> queryBuilder();

    public List<T> queryForAll() throws SQLException;

    public List<T> queryForEq(String var1, Object var2) throws SQLException;

    public List<T> queryForFieldValues(Map<String, Object> var1) throws SQLException;

    public List<T> queryForFieldValuesArgs(Map<String, Object> var1) throws SQLException;

    public T queryForFirst(PreparedQuery<T> var1) throws SQLException;

    public T queryForId(ID var1) throws SQLException;

    public List<T> queryForMatching(T var1) throws SQLException;

    public List<T> queryForMatchingArgs(T var1) throws SQLException;

    public T queryForSameId(T var1) throws SQLException;

    public /* varargs */ <UO> GenericRawResults<UO> queryRaw(String var1, RawRowMapper<UO> var2, String ... var3) throws SQLException;

    public /* varargs */ <UO> GenericRawResults<UO> queryRaw(String var1, DataType[] var2, RawRowObjectMapper<UO> var3, String ... var4) throws SQLException;

    public /* varargs */ GenericRawResults<Object[]> queryRaw(String var1, DataType[] var2, String ... var3) throws SQLException;

    public /* varargs */ GenericRawResults<String[]> queryRaw(String var1, String ... var2) throws SQLException;

    public /* varargs */ long queryRawValue(String var1, String ... var2) throws SQLException;

    public int refresh(T var1) throws SQLException;

    public void rollBack(DatabaseConnection var1) throws SQLException;

    public void setAutoCommit(DatabaseConnection var1, boolean var2) throws SQLException;

    @Deprecated
    public void setAutoCommit(boolean var1) throws SQLException;

    public void setObjectCache(ObjectCache var1) throws SQLException;

    public void setObjectCache(boolean var1) throws SQLException;

    public void setObjectFactory(ObjectFactory<T> var1);

    public DatabaseConnection startThreadConnection() throws SQLException;

    public int update(PreparedUpdate<T> var1) throws SQLException;

    public int update(T var1) throws SQLException;

    public UpdateBuilder<T, ID> updateBuilder();

    public int updateId(T var1, ID var2) throws SQLException;

    public /* varargs */ int updateRaw(String var1, String ... var2) throws SQLException;

    public static class CreateOrUpdateStatus {
        private boolean created;
        private int numLinesChanged;
        private boolean updated;

        public CreateOrUpdateStatus(boolean bl, boolean bl2, int n) {
            this.created = bl;
            this.updated = bl2;
            this.numLinesChanged = n;
        }

        public int getNumLinesChanged() {
            return this.numLinesChanged;
        }

        public boolean isCreated() {
            return this.created;
        }

        public boolean isUpdated() {
            return this.updated;
        }
    }

}
