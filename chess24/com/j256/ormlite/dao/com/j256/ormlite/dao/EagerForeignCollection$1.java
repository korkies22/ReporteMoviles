/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.List;

class EagerForeignCollection
implements CloseableIterator<T> {
    private int offset = -1;

    EagerForeignCollection() {
    }

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
}
