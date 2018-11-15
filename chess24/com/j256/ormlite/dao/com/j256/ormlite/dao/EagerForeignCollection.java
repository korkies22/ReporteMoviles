/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EagerForeignCollection<T, ID>
extends BaseForeignCollection<T, ID>
implements ForeignCollection<T>,
CloseableWrappedIterable<T>,
Serializable {
    private static final long serialVersionUID = -2523335606983317721L;
    private List<T> results;

    public EagerForeignCollection(Dao<T, ID> dao, Object object, Object object2, FieldType fieldType, String string, boolean bl) throws SQLException {
        super(dao, object, object2, fieldType, string, bl);
        if (object2 == null) {
            this.results = new ArrayList<T>();
            return;
        }
        this.results = dao.query(this.getPreparedQuery());
    }

    @Override
    public boolean add(T t) {
        if (this.results.add(t)) {
            return super.add(t);
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (this.results.addAll(collection)) {
            return super.addAll(collection);
        }
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public void closeLastIterator() {
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return this.iteratorThrow(-1);
    }

    @Override
    public CloseableIterator<T> closeableIterator(int n) {
        return this.iteratorThrow(-1);
    }

    @Override
    public boolean contains(Object object) {
        return this.results.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.results.containsAll(collection);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EagerForeignCollection)) {
            return false;
        }
        object = (EagerForeignCollection)object;
        return ((Object)this.results).equals(object.results);
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this;
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable(int n) {
        return this;
    }

    @Override
    public int hashCode() {
        return ((Object)this.results).hashCode();
    }

    @Override
    public boolean isEager() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.results.isEmpty();
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.iteratorThrow(-1);
    }

    @Override
    public CloseableIterator<T> iterator(int n) {
        return this.iteratorThrow(n);
    }

    @Override
    public CloseableIterator<T> iteratorThrow() {
        return this.iteratorThrow(-1);
    }

    @Override
    public CloseableIterator<T> iteratorThrow(int n) {
        return new CloseableIterator<T>(){
            private int offset = -1;

            @Override
            public void close() {
            }

            @Override
            public void closeQuietly() {
            }

            @Override
            public T current() {
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T)EagerForeignCollection.this.results.get(this.offset);
            }

            @Override
            public T first() {
                this.offset = 0;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T)EagerForeignCollection.this.results.get(0);
            }

            @Override
            public DatabaseResults getRawResults() {
                return null;
            }

            @Override
            public boolean hasNext() {
                if (this.offset + 1 < EagerForeignCollection.this.results.size()) {
                    return true;
                }
                return false;
            }

            @Override
            public T moveRelative(int n) {
                this.offset += n;
                if (this.offset >= 0 && this.offset < EagerForeignCollection.this.results.size()) {
                    return (T)EagerForeignCollection.this.results.get(this.offset);
                }
                return null;
            }

            @Override
            public void moveToNext() {
                ++this.offset;
            }

            @Override
            public T next() {
                ++this.offset;
                return (T)EagerForeignCollection.this.results.get(this.offset);
            }

            @Override
            public T nextThrow() {
                ++this.offset;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T)EagerForeignCollection.this.results.get(this.offset);
            }

            @Override
            public T previous() {
                --this.offset;
                if (this.offset >= 0 && this.offset < EagerForeignCollection.this.results.size()) {
                    return (T)EagerForeignCollection.this.results.get(this.offset);
                }
                return null;
            }

            @Override
            public void remove() {
                if (this.offset < 0) {
                    throw new IllegalStateException("next() must be called before remove()");
                }
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("current results position (");
                    stringBuilder.append(this.offset);
                    stringBuilder.append(") is out of bounds");
                    throw new IllegalStateException(stringBuilder.toString());
                }
                Object e = EagerForeignCollection.this.results.remove(this.offset);
                --this.offset;
                if (EagerForeignCollection.this.dao != null) {
                    try {
                        EagerForeignCollection.this.dao.delete(e);
                        return;
                    }
                    catch (SQLException sQLException) {
                        throw new RuntimeException(sQLException);
                    }
                }
            }
        };
    }

    @Override
    public int refreshAll() throws SQLException {
        Iterator<T> iterator = this.results.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            T t = iterator.next();
            n += this.dao.refresh(t);
        }
        return n;
    }

    @Override
    public int refreshCollection() throws SQLException {
        this.results = this.dao.query(this.getPreparedQuery());
        return this.results.size();
    }

    @Override
    public boolean remove(Object object) {
        if (this.results.remove(object)) {
            if (this.dao == null) {
                return false;
            }
            try {
                int n = this.dao.delete(object);
                if (n == 1) {
                    return true;
                }
                return false;
            }
            catch (SQLException sQLException) {
                throw new IllegalStateException("Could not delete data element from dao", sQLException);
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            if (!this.remove(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return super.retainAll(collection);
    }

    @Override
    public int size() {
        return this.results.size();
    }

    @Override
    public Object[] toArray() {
        return this.results.toArray();
    }

    @Override
    public <E> E[] toArray(E[] arrE) {
        return this.results.toArray(arrE);
    }

    @Override
    public int updateAll() throws SQLException {
        Iterator<T> iterator = this.results.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            T t = iterator.next();
            n += this.dao.update(t);
        }
        return n;
    }

}
