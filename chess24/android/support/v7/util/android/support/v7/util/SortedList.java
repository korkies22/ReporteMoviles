/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.ListUpdateCallback;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mNewDataStart;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public SortedList(Class<T> class_, Callback<T> callback) {
        this(class_, callback, 10);
    }

    public SortedList(Class<T> class_, Callback<T> callback, int n) {
        this.mTClass = class_;
        this.mData = (Object[])Array.newInstance(class_, n);
        this.mCallback = callback;
        this.mSize = 0;
    }

    private int add(T t, boolean bl) {
        int n;
        int n2 = this.findIndexOf(t, this.mData, 0, this.mSize, 1);
        if (n2 == -1) {
            n = 0;
        } else {
            n = n2;
            if (n2 < this.mSize) {
                T t2 = this.mData[n2];
                n = n2;
                if (this.mCallback.areItemsTheSame(t2, t)) {
                    if (this.mCallback.areContentsTheSame(t2, t)) {
                        this.mData[n2] = t;
                        return n2;
                    }
                    this.mData[n2] = t;
                    this.mCallback.onChanged(n2, 1, this.mCallback.getChangePayload(t2, t));
                    return n2;
                }
            }
        }
        this.addToData(n, t);
        if (bl) {
            this.mCallback.onInserted(n, 1);
        }
        return n;
    }

    private void addAllInternal(T[] arrT) {
        if (arrT.length < 1) {
            return;
        }
        int n = this.sortAndDedup(arrT);
        if (this.mSize == 0) {
            this.mData = arrT;
            this.mSize = n;
            this.mCallback.onInserted(0, n);
            return;
        }
        this.merge(arrT, n);
    }

    private void addToData(int n, T object) {
        if (n > this.mSize) {
            object = new StringBuilder();
            object.append("cannot add item to ");
            object.append(n);
            object.append(" because size is ");
            object.append(this.mSize);
            throw new IndexOutOfBoundsException(object.toString());
        }
        if (this.mSize == this.mData.length) {
            Object[] arrobject = (Object[])Array.newInstance(this.mTClass, this.mData.length + 10);
            System.arraycopy(this.mData, 0, arrobject, 0, n);
            arrobject[n] = object;
            System.arraycopy(this.mData, n, arrobject, n + 1, this.mSize - n);
            this.mData = arrobject;
        } else {
            System.arraycopy(this.mData, n, this.mData, n + 1, this.mSize - n);
            this.mData[n] = object;
        }
        ++this.mSize;
    }

    private T[] copyArray(T[] arrT) {
        Object[] arrobject = (Object[])Array.newInstance(this.mTClass, arrT.length);
        System.arraycopy(arrT, 0, arrobject, 0, arrT.length);
        return arrobject;
    }

    private int findIndexOf(T t, T[] arrT, int n, int n2, int n3) {
        int n4 = n;
        while (n4 < n2) {
            n = (n4 + n2) / 2;
            T t2 = arrT[n];
            int n5 = this.mCallback.compare(t2, t);
            if (n5 < 0) {
                n4 = n + 1;
                continue;
            }
            if (n5 == 0) {
                if (this.mCallback.areItemsTheSame(t2, t)) {
                    return n;
                }
                n4 = this.linearEqualitySearch(t, n, n4, n2);
                if (n3 == 1) {
                    n2 = n4;
                    if (n4 == -1) {
                        n2 = n;
                    }
                    return n2;
                }
                return n4;
            }
            n2 = n;
        }
        if (n3 == 1) {
            return n4;
        }
        return -1;
    }

    private int findSameItem(T t, T[] arrT, int n, int n2) {
        while (n < n2) {
            if (this.mCallback.areItemsTheSame(arrT[n], t)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private int linearEqualitySearch(T t, int n, int n2, int n3) {
        int n4;
        T t2;
        int n5 = n - 1;
        do {
            n4 = n;
            if (n5 < n2) break;
            t2 = this.mData[n5];
            if (this.mCallback.compare(t2, t) != 0) {
                n4 = n;
                break;
            }
            if (this.mCallback.areItemsTheSame(t2, t)) {
                return n5;
            }
            --n5;
        } while (true);
        while ((n = n4 + 1) < n3 && this.mCallback.compare(t2 = this.mData[n], t) == 0) {
            n4 = n;
            if (!this.mCallback.areItemsTheSame(t2, t)) continue;
            return n;
        }
        return -1;
    }

    private void merge(T[] arrT, int n) {
        boolean bl = this.mCallback instanceof BatchedCallback;
        int n2 = 0;
        boolean bl2 = !bl;
        if (bl2) {
            this.beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        int n3 = this.mSize;
        this.mData = (Object[])Array.newInstance(this.mTClass, n3 + n + 10);
        this.mNewDataStart = 0;
        do {
            block10 : {
                block8 : {
                    block9 : {
                        if (this.mOldDataStart >= this.mOldDataSize && n2 >= n) break block8;
                        if (this.mOldDataStart != this.mOldDataSize) break block9;
                        System.arraycopy(arrT, n2, this.mData, this.mNewDataStart, n -= n2);
                        this.mNewDataStart += n;
                        this.mSize += n;
                        this.mCallback.onInserted(this.mNewDataStart - n, n);
                        break block8;
                    }
                    if (n2 != n) break block10;
                    n = this.mOldDataSize - this.mOldDataStart;
                    System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mNewDataStart, n);
                    this.mNewDataStart += n;
                }
                this.mOldData = null;
                if (bl2) {
                    this.endBatchedUpdates();
                }
                return;
            }
            Object object = this.mOldData[this.mOldDataStart];
            Object object2 = arrT[n2];
            n3 = this.mCallback.compare(object, object2);
            if (n3 > 0) {
                object = this.mData;
                n3 = this.mNewDataStart;
                this.mNewDataStart = n3 + 1;
                object[n3] = object2;
                ++this.mSize;
                ++n2;
                this.mCallback.onInserted(this.mNewDataStart - 1, 1);
                continue;
            }
            if (n3 == 0 && this.mCallback.areItemsTheSame(object, object2)) {
                T[] arrT2 = this.mData;
                n3 = this.mNewDataStart;
                this.mNewDataStart = n3 + 1;
                arrT2[n3] = object2;
                n3 = n2 + 1;
                ++this.mOldDataStart;
                n2 = n3;
                if (this.mCallback.areContentsTheSame(object, object2)) continue;
                this.mCallback.onChanged(this.mNewDataStart - 1, 1, this.mCallback.getChangePayload(object, object2));
                n2 = n3;
                continue;
            }
            object2 = this.mData;
            n3 = this.mNewDataStart;
            this.mNewDataStart = n3 + 1;
            object2[n3] = object;
            ++this.mOldDataStart;
        } while (true);
    }

    private boolean remove(T t, boolean bl) {
        int n = this.findIndexOf(t, this.mData, 0, this.mSize, 2);
        if (n == -1) {
            return false;
        }
        this.removeItemAtIndex(n, bl);
        return true;
    }

    private void removeItemAtIndex(int n, boolean bl) {
        System.arraycopy(this.mData, n + 1, this.mData, n, this.mSize - n - 1);
        --this.mSize;
        this.mData[this.mSize] = null;
        if (bl) {
            this.mCallback.onRemoved(n, 1);
        }
    }

    private void replaceAllInsert(T t) {
        this.mData[this.mNewDataStart] = t;
        ++this.mNewDataStart;
        ++this.mSize;
        this.mCallback.onInserted(this.mNewDataStart - 1, 1);
    }

    private void replaceAllInternal(@NonNull T[] arrT) {
        boolean bl = !(this.mCallback instanceof BatchedCallback);
        if (bl) {
            this.beginBatchedUpdates();
        }
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        this.mOldData = this.mData;
        this.mNewDataStart = 0;
        int n = this.sortAndDedup(arrT);
        this.mData = (Object[])Array.newInstance(this.mTClass, n);
        do {
            int n2;
            block11 : {
                block9 : {
                    block10 : {
                        if (this.mNewDataStart >= n && this.mOldDataStart >= this.mOldDataSize) break block9;
                        if (this.mOldDataStart < this.mOldDataSize) break block10;
                        n2 = this.mNewDataStart;
                        System.arraycopy(arrT, n2, this.mData, n2, n -= this.mNewDataStart);
                        this.mNewDataStart += n;
                        this.mSize += n;
                        this.mCallback.onInserted(n2, n);
                        break block9;
                    }
                    if (this.mNewDataStart < n) break block11;
                    n = this.mOldDataSize - this.mOldDataStart;
                    this.mSize -= n;
                    this.mCallback.onRemoved(this.mNewDataStart, n);
                }
                this.mOldData = null;
                if (bl) {
                    this.endBatchedUpdates();
                }
                return;
            }
            T t = this.mOldData[this.mOldDataStart];
            T t2 = arrT[this.mNewDataStart];
            n2 = this.mCallback.compare(t, t2);
            if (n2 < 0) {
                this.replaceAllRemove();
                continue;
            }
            if (n2 > 0) {
                this.replaceAllInsert(t2);
                continue;
            }
            if (!this.mCallback.areItemsTheSame(t, t2)) {
                this.replaceAllRemove();
                this.replaceAllInsert(t2);
                continue;
            }
            this.mData[this.mNewDataStart] = t2;
            ++this.mOldDataStart;
            ++this.mNewDataStart;
            if (this.mCallback.areContentsTheSame(t, t2)) continue;
            this.mCallback.onChanged(this.mNewDataStart - 1, 1, this.mCallback.getChangePayload(t, t2));
        } while (true);
    }

    private void replaceAllRemove() {
        --this.mSize;
        ++this.mOldDataStart;
        this.mCallback.onRemoved(this.mNewDataStart, 1);
    }

    private int sortAndDedup(@NonNull T[] arrT) {
        int n = 0;
        if (arrT.length == 0) {
            return 0;
        }
        Arrays.sort(arrT, this.mCallback);
        int n2 = 1;
        for (int i = 1; i < arrT.length; ++i) {
            int n3;
            T t = arrT[i];
            if (this.mCallback.compare(arrT[n], t) == 0) {
                n3 = this.findSameItem(t, arrT, n, n2);
                if (n3 != -1) {
                    arrT[n3] = t;
                    continue;
                }
                if (n2 != i) {
                    arrT[n2] = t;
                }
                ++n2;
                continue;
            }
            if (n2 != i) {
                arrT[n2] = t;
            }
            n3 = n2 + 1;
            n = n2;
            n2 = n3;
        }
        return n2;
    }

    private void throwIfInMutationOperation() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public int add(T t) {
        this.throwIfInMutationOperation();
        return this.add(t, true);
    }

    public void addAll(Collection<T> collection) {
        this.addAll((T[])collection.toArray((Object[])Array.newInstance(this.mTClass, collection.size())), true);
    }

    public /* varargs */ void addAll(T ... arrT) {
        this.addAll(arrT, false);
    }

    public void addAll(T[] arrT, boolean bl) {
        this.throwIfInMutationOperation();
        if (arrT.length == 0) {
            return;
        }
        if (bl) {
            this.addAllInternal(arrT);
            return;
        }
        this.addAllInternal(this.copyArray(arrT));
    }

    public void beginBatchedUpdates() {
        this.throwIfInMutationOperation();
        if (this.mCallback instanceof BatchedCallback) {
            return;
        }
        if (this.mBatchedCallback == null) {
            this.mBatchedCallback = new BatchedCallback(this.mCallback);
        }
        this.mCallback = this.mBatchedCallback;
    }

    public void clear() {
        this.throwIfInMutationOperation();
        if (this.mSize == 0) {
            return;
        }
        int n = this.mSize;
        Arrays.fill(this.mData, 0, n, null);
        this.mSize = 0;
        this.mCallback.onRemoved(0, n);
    }

    public void endBatchedUpdates() {
        this.throwIfInMutationOperation();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback)this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    public T get(int n) throws IndexOutOfBoundsException {
        if (n < this.mSize && n >= 0) {
            if (this.mOldData != null && n >= this.mNewDataStart) {
                return this.mOldData[n - this.mNewDataStart + this.mOldDataStart];
            }
            return this.mData[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asked to get item at ");
        stringBuilder.append(n);
        stringBuilder.append(" but size is ");
        stringBuilder.append(this.mSize);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int indexOf(T t) {
        if (this.mOldData != null) {
            int n = this.findIndexOf(t, this.mData, 0, this.mNewDataStart, 4);
            if (n != -1) {
                return n;
            }
            n = this.findIndexOf(t, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            if (n != -1) {
                return n - this.mOldDataStart + this.mNewDataStart;
            }
            return -1;
        }
        return this.findIndexOf(t, this.mData, 0, this.mSize, 4);
    }

    public void recalculatePositionOfItemAt(int n) {
        this.throwIfInMutationOperation();
        T t = this.get(n);
        this.removeItemAtIndex(n, false);
        int n2 = this.add(t, false);
        if (n != n2) {
            this.mCallback.onMoved(n, n2);
        }
    }

    public boolean remove(T t) {
        this.throwIfInMutationOperation();
        return this.remove(t, true);
    }

    public T removeItemAt(int n) {
        this.throwIfInMutationOperation();
        T t = this.get(n);
        this.removeItemAtIndex(n, true);
        return t;
    }

    public void replaceAll(@NonNull Collection<T> collection) {
        this.replaceAll((T[])collection.toArray((Object[])Array.newInstance(this.mTClass, collection.size())), true);
    }

    public /* varargs */ void replaceAll(@NonNull T ... arrT) {
        this.replaceAll(arrT, false);
    }

    public void replaceAll(@NonNull T[] arrT, boolean bl) {
        this.throwIfInMutationOperation();
        if (bl) {
            this.replaceAllInternal(arrT);
            return;
        }
        this.replaceAllInternal(this.copyArray(arrT));
    }

    public int size() {
        return this.mSize;
    }

    public void updateItemAt(int n, T t) {
        this.throwIfInMutationOperation();
        T t2 = this.get(n);
        int n2 = t2 != t && this.mCallback.areContentsTheSame(t2, t) ? 0 : 1;
        if (t2 != t && this.mCallback.compare(t2, t) == 0) {
            this.mData[n] = t;
            if (n2 != 0) {
                this.mCallback.onChanged(n, 1, this.mCallback.getChangePayload(t2, t));
            }
            return;
        }
        if (n2 != 0) {
            this.mCallback.onChanged(n, 1, this.mCallback.getChangePayload(t2, t));
        }
        this.removeItemAtIndex(n, false);
        n2 = this.add(t, false);
        if (n != n2) {
            this.mCallback.onMoved(n, n2);
        }
    }

    public static class BatchedCallback<T2>
    extends Callback<T2> {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback;
        final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> callback) {
            this.mWrappedCallback = callback;
            this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
        }

        @Override
        public boolean areContentsTheSame(T2 T2, T2 T22) {
            return this.mWrappedCallback.areContentsTheSame(T2, T22);
        }

        @Override
        public boolean areItemsTheSame(T2 T2, T2 T22) {
            return this.mWrappedCallback.areItemsTheSame(T2, T22);
        }

        @Override
        public int compare(T2 T2, T2 T22) {
            return this.mWrappedCallback.compare(T2, T22);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }

        @Nullable
        @Override
        public Object getChangePayload(T2 T2, T2 T22) {
            return this.mWrappedCallback.getChangePayload(T2, T22);
        }

        @Override
        public void onChanged(int n, int n2) {
            this.mBatchingListUpdateCallback.onChanged(n, n2, null);
        }

        @Override
        public void onChanged(int n, int n2, Object object) {
            this.mBatchingListUpdateCallback.onChanged(n, n2, object);
        }

        @Override
        public void onInserted(int n, int n2) {
            this.mBatchingListUpdateCallback.onInserted(n, n2);
        }

        @Override
        public void onMoved(int n, int n2) {
            this.mBatchingListUpdateCallback.onMoved(n, n2);
        }

        @Override
        public void onRemoved(int n, int n2) {
            this.mBatchingListUpdateCallback.onRemoved(n, n2);
        }
    }

    public static abstract class Callback<T2>
    implements Comparator<T2>,
    ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 var1, T2 var2);

        public abstract boolean areItemsTheSame(T2 var1, T2 var2);

        @Override
        public abstract int compare(T2 var1, T2 var2);

        @Nullable
        public Object getChangePayload(T2 T2, T2 T22) {
            return null;
        }

        public abstract void onChanged(int var1, int var2);

        @Override
        public void onChanged(int n, int n2, Object object) {
            this.onChanged(n, n2);
        }
    }

}
