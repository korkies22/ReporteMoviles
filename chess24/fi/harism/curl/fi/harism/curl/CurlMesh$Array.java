/*
 * Decompiled with CFR 0_134.
 */
package fi.harism.curl;

import fi.harism.curl.CurlMesh;

private class CurlMesh.Array<T> {
    private Object[] mArray;
    private int mCapacity;
    private int mSize;

    public CurlMesh.Array(int n) {
        this.mCapacity = n;
        this.mArray = new Object[n];
    }

    public void add(int n, T t) {
        if (n >= 0 && n <= this.mSize && this.mSize < this.mCapacity) {
            for (int i = this.mSize; i > n; --i) {
                this.mArray[i] = this.mArray[i - 1];
            }
            this.mArray[n] = t;
            ++this.mSize;
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void add(T t) {
        if (this.mSize >= this.mCapacity) {
            throw new IndexOutOfBoundsException();
        }
        Object[] arrobject = this.mArray;
        int n = this.mSize;
        this.mSize = n + 1;
        arrobject[n] = t;
    }

    public void addAll(CurlMesh.Array<T> array) {
        if (this.mSize + array.size() > this.mCapacity) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < array.size(); ++i) {
            Object[] arrobject = this.mArray;
            int n = this.mSize;
            this.mSize = n + 1;
            arrobject[n] = array.get(i);
        }
    }

    public void clear() {
        this.mSize = 0;
    }

    public T get(int n) {
        if (n >= 0 && n < this.mSize) {
            return (T)this.mArray[n];
        }
        throw new IndexOutOfBoundsException();
    }

    public T remove(int n) {
        if (n >= 0 && n < this.mSize) {
            Object object = this.mArray[n];
            while (n < this.mSize - 1) {
                Object[] arrobject = this.mArray;
                Object[] arrobject2 = this.mArray;
                int n2 = n + 1;
                arrobject[n] = arrobject2[n2];
                n = n2;
            }
            --this.mSize;
            return (T)object;
        }
        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return this.mSize;
    }
}
