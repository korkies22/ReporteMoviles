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
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.PreparedQuery;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class LazyForeignCollection<T, ID>
extends BaseForeignCollection<T, ID>
implements ForeignCollection<T>,
Serializable {
    private static final long serialVersionUID = -5460708106909626233L;
    private transient CloseableIterator<T> lastIterator;

    public LazyForeignCollection(Dao<T, ID> dao, Object object, Object object2, FieldType fieldType, String string, boolean bl) {
        super(dao, object, object2, fieldType, string, bl);
    }

    private CloseableIterator<T> seperateIteratorThrow(int n) throws SQLException {
        if (this.dao == null) {
            throw new IllegalStateException("Internal DAO object is null.  Lazy collections cannot be used if they have been deserialized.");
        }
        return this.dao.iterator(this.getPreparedQuery(), n);
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
        return this.closeableIterator(-1);
    }

    @Override
    public CloseableIterator<T> closeableIterator(int n) {
        try {
            CloseableIterator<T> closeableIterator = this.iteratorThrow(n);
            return closeableIterator;
        }
        catch (SQLException sQLException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not build lazy iterator for ");
            stringBuilder.append(this.dao.getDataClass());
            throw new IllegalStateException(stringBuilder.toString(), sQLException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean contains(Object object) {
        block10 : {
            iterator = this.iterator();
            do {
                if (!iterator.hasNext()) break block10;
                if (!(bl = iterator.next().equals(object))) continue;
                break;
            } while (true);
            iterator.close();
            return true;
        }
        iterator.close();
        return false;
        catch (Throwable throwable) {
            iterator.close();
lbl16: // 2 sources:
            do {
                throw throwable;
                break;
            } while (true);
        }
        catch (SQLException sQLException) {
            return true;
        }
        catch (SQLException sQLException) {
            return false;
        }
        {
            catch (SQLException sQLException) {
                ** continue;
            }
        }
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        HashSet hashSet = new HashSet((Collection<?>)object);
        object = this.iterator();
        try {
            while (object.hasNext()) {
                hashSet.remove(object.next());
            }
            boolean bl = hashSet.isEmpty();
            return bl;
        }
        finally {
            object.close();
        }
    }

    @Override
    public boolean equals(Object object) {
        return Object.super.equals(object);
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this.getWrappedIterable(-1);
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable(final int n) {
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>(){

            @Override
            public CloseableIterator<T> closeableIterator() {
                try {
                    CloseableIterator closeableIterator = LazyForeignCollection.this.seperateIteratorThrow(n);
                    return closeableIterator;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not build lazy iterator for ");
                    stringBuilder.append(LazyForeignCollection.this.dao.getDataClass());
                    throw new IllegalStateException(stringBuilder.toString(), exception);
                }
            }

            @Override
            public CloseableIterator<T> iterator() {
                return this.closeableIterator();
            }
        });
    }

    @Override
    public int hashCode() {
        return Object.super.hashCode();
    }

    @Override
    public boolean isEager() {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isEmpty() {
        boolean bl;
        Iterator iterator = this.iterator();
        try {
            bl = iterator.hasNext();
        }
        catch (Throwable throwable) {
            try {
                iterator.close();
            }
            catch (SQLException sQLException) {
                throw throwable;
            }
            do {
                throw throwable;
                break;
            } while (true);
        }
        bl ^= true;
        try {
            iterator.close();
            return bl;
        }
        catch (SQLException sQLException) {
            return bl;
        }
    }

    @Override
    public CloseableIterator<T> iterator() {
        return this.closeableIterator(-1);
    }

    @Override
    public CloseableIterator<T> iterator(int n) {
        return this.closeableIterator(n);
    }

    @Override
    public CloseableIterator<T> iteratorThrow() throws SQLException {
        return this.iteratorThrow(-1);
    }

    @Override
    public CloseableIterator<T> iteratorThrow(int n) throws SQLException {
        this.lastIterator = this.seperateIteratorThrow(n);
        return this.lastIterator;
    }

    @Override
    public int refreshAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }

    @Override
    public int refreshCollection() {
        return 0;
    }

    @Override
    public boolean remove(Object object) {
        Iterator iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                if (!iterator.next().equals(object)) continue;
                iterator.remove();
                return true;
            }
            return false;
        }
        finally {
            iterator.close();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        Iterator iterator = this.iterator();
        boolean bl = false;
        try {
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) continue;
                iterator.remove();
                bl = true;
            }
        }
        catch (Throwable throwable) {
            try {
                iterator.close();
            }
            catch (SQLException sQLException) {
                throw throwable;
            }
            throw throwable;
        }
        iterator.close();
        return bl;
        {
            catch (SQLException sQLException) {
                return bl;
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int size() {
        Iterator iterator = this.iterator();
        int n = 0;
        try {
            while (iterator.hasNext()) {
                iterator.moveToNext();
                ++n;
            }
        }
        catch (Throwable throwable) {
            try {
                iterator.close();
            }
            catch (SQLException sQLException) {
                throw throwable;
            }
            throw throwable;
        }
        iterator.close();
        return n;
        {
            catch (SQLException sQLException) {
                return n;
            }
        }
    }

    @Override
    public Object[] toArray() {
        Object[] arrobject = new Object[]();
        Iterator iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                arrobject.add(iterator.next());
            }
            arrobject = arrobject.toArray();
            return arrobject;
        }
        finally {
            iterator.close();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public <E> E[] toArray(E[] var1_1) {
        var7_3 = this.iterator();
        var2_4 = 0;
        var5_5 = null;
        do lbl-1000: // 2 sources:
        {
            block13 : {
                block14 : {
                    block11 : {
                        block12 : {
                            if (!var7_3.hasNext()) break block11;
                            var8_11 = var7_3.next();
                            if (var2_4 < var1_1.length) break block12;
                            var6_9 = var5_5;
                            if (var5_5 != null) break block13;
                            break block14;
                        }
                        var1_1[var2_4] = var8_11;
                        ** break block9
                    }
                    try {
                        var7_3.close();
                    }
                    catch (SQLException var6_10) {}
                    if (var5_5 != null) return var5_5.toArray(var1_1);
                    if (var2_4 >= var1_1.length - 1) return var1_1;
                    var1_1[var2_4] = null;
                    return var1_1;
                }
                var5_5 = new ArrayList<E>();
                var4_8 = var1_1.length;
                var3_7 = 0;
                do {
                    var6_9 = var5_5;
                    if (var3_7 >= var4_8) break;
                    var5_5.add(var1_1[var3_7]);
                    ++var3_7;
                } while (true);
            }
            var6_9.add(var8_11);
            var5_5 = var6_9;
            break;
        } while (true);
        catch (Throwable var1_2) {
            try {
                var7_3.close();
            }
            catch (SQLException var5_6) {
                throw var1_2;
            }
            throw var1_2;
        }
lbl-1000: // 2 sources:
        {
            
            ++var2_4;
            ** while (true)
        }
    }

    @Override
    public int updateAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }

}
