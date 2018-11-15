/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public abstract class BaseForeignCollection<T, ID>
implements ForeignCollection<T>,
Serializable {
    private static final long serialVersionUID = -5158840898186237589L;
    protected final transient Dao<T, ID> dao;
    private final transient FieldType foreignFieldType;
    private final transient boolean orderAscending;
    private final transient String orderColumn;
    private final transient Object parent;
    private final transient Object parentId;
    private transient PreparedQuery<T> preparedQuery;

    protected BaseForeignCollection(Dao<T, ID> dao, Object object, Object object2, FieldType fieldType, String string, boolean bl) {
        this.dao = dao;
        this.foreignFieldType = fieldType;
        this.parentId = object2;
        this.orderColumn = string;
        this.orderAscending = bl;
        this.parent = object;
    }

    private boolean addElement(T t) throws SQLException {
        if (this.dao == null) {
            return false;
        }
        if (this.parent != null && this.foreignFieldType.getFieldValueIfNotDefault(t) == null) {
            this.foreignFieldType.assignField(t, this.parent, true, null);
        }
        this.dao.create(t);
        return true;
    }

    @Override
    public boolean add(T t) {
        try {
            boolean bl = this.addElement(t);
            return bl;
        }
        catch (SQLException sQLException) {
            throw new IllegalStateException("Could not create data element in dao", sQLException);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> object) {
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Object e = object.next();
            try {
                boolean bl2 = this.addElement(e);
                if (!bl2) continue;
                bl = true;
            }
            catch (SQLException sQLException) {
                throw new IllegalStateException("Could not create data elements in dao", sQLException);
            }
        }
        return bl;
    }

    @Override
    public void clear() {
        if (this.dao == null) {
            return;
        }
        CloseableIterator closeableIterator = this.closeableIterator();
        try {
            while (closeableIterator.hasNext()) {
                closeableIterator.next();
                closeableIterator.remove();
            }
            return;
        }
        finally {
            closeableIterator.close();
        }
    }

    protected PreparedQuery<T> getPreparedQuery() throws SQLException {
        if (this.dao == null) {
            return null;
        }
        if (this.preparedQuery == null) {
            SelectArg selectArg = new SelectArg();
            selectArg.setValue(this.parentId);
            QueryBuilder<T, ID> queryBuilder = this.dao.queryBuilder();
            if (this.orderColumn != null) {
                queryBuilder.orderBy(this.orderColumn, this.orderAscending);
            }
            this.preparedQuery = queryBuilder.where().eq(this.foreignFieldType.getColumnName(), selectArg).prepare();
            if (this.preparedQuery instanceof MappedPreparedStmt) {
                ((MappedPreparedStmt)this.preparedQuery).setParentInformation(this.parent, this.parentId);
            }
        }
        return this.preparedQuery;
    }

    @Override
    public int refresh(T t) throws SQLException {
        if (this.dao == null) {
            return 0;
        }
        return this.dao.refresh(t);
    }

    @Override
    public abstract boolean remove(Object var1);

    @Override
    public abstract boolean removeAll(Collection<?> var1);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        Object object = this.dao;
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = this.closeableIterator();
        try {
            while (object.hasNext()) {
                if (collection.contains(object.next())) continue;
                object.remove();
                bl = true;
            }
        }
        catch (Throwable throwable) {
            try {
                object.close();
            }
            catch (SQLException sQLException) {
                throw throwable;
            }
            throw throwable;
        }
        object.close();
        return bl;
        {
            catch (SQLException sQLException) {
                return bl;
            }
        }
    }

    @Override
    public int update(T t) throws SQLException {
        if (this.dao == null) {
            return 0;
        }
        return this.dao.update(t);
    }
}
