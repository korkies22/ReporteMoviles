/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import java.util.ArrayList;

class PositionMap<E>
implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    PositionMap() {
        this(10);
    }

    PositionMap(int n) {
        if (n == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            n = PositionMap.idealIntArraySize(n);
            this.mKeys = new int[n];
            this.mValues = new Object[n];
        }
        this.mSize = 0;
    }

    private void gc() {
        int n;
        int n2 = this.mSize;
        int[] arrn = this.mKeys;
        Object[] arrobject = this.mValues;
        int n3 = n = 0;
        while (n < n2) {
            Object object = arrobject[n];
            int n4 = n3;
            if (object != DELETED) {
                if (n != n3) {
                    arrn[n3] = arrn[n];
                    arrobject[n3] = object;
                    arrobject[n] = null;
                }
                n4 = n3 + 1;
            }
            ++n;
            n3 = n4;
        }
        this.mGarbage = false;
        this.mSize = n3;
    }

    static int idealBooleanArraySize(int n) {
        return PositionMap.idealByteArraySize(n);
    }

    static int idealByteArraySize(int n) {
        for (int i = 4; i < 32; ++i) {
            int n2 = (1 << i) - 12;
            if (n > n2) continue;
            return n2;
        }
        return n;
    }

    static int idealCharArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 2) / 2;
    }

    static int idealFloatArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 4) / 4;
    }

    static int idealIntArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 4) / 4;
    }

    static int idealLongArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 8) / 8;
    }

    static int idealObjectArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 4) / 4;
    }

    static int idealShortArraySize(int n) {
        return PositionMap.idealByteArraySize(n * 2) / 2;
    }

    public void append(int n, E e) {
        int n2;
        if (this.mSize != 0 && n <= this.mKeys[this.mSize - 1]) {
            this.put(n, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        if ((n2 = this.mSize) >= this.mKeys.length) {
            int n3 = PositionMap.idealIntArraySize(n2 + 1);
            int[] arrn = new int[n3];
            Object[] arrobject = new Object[n3];
            System.arraycopy(this.mKeys, 0, arrn, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, arrobject, 0, this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
        this.mKeys[n2] = n;
        this.mValues[n2] = e;
        this.mSize = n2 + 1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            arrobject[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public PositionMap<E> clone() {
        PositionMap positionMap;
        try {
            positionMap = (PositionMap)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
        try {
            positionMap.mKeys = (int[])this.mKeys.clone();
            positionMap.mValues = (Object[])this.mValues.clone();
            return positionMap;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return positionMap;
        }
    }

    public void delete(int n) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0 && this.mValues[n] != DELETED) {
            this.mValues[n] = DELETED;
            this.mGarbage = true;
        }
    }

    public E get(int n) {
        return this.get(n, null);
    }

    public E get(int n, E e) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0) {
            if (this.mValues[n] == DELETED) {
                return e;
            }
            return (E)this.mValues[n];
        }
        return e;
    }

    public int indexOfKey(int n) {
        if (this.mGarbage) {
            this.gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            this.gc();
        }
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != e) continue;
            return i;
        }
        return -1;
    }

    public void insertKeyRange(int n, int n2) {
    }

    public int keyAt(int n) {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mKeys[n];
    }

    public void put(int n, E e) {
        int[] arrn;
        Object[] arrobject;
        int n2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n2 >= 0) {
            this.mValues[n2] = e;
            return;
        }
        int n3 = ~ n2;
        if (n3 < this.mSize && this.mValues[n3] == DELETED) {
            this.mKeys[n3] = n;
            this.mValues[n3] = e;
            return;
        }
        n2 = n3;
        if (this.mGarbage) {
            n2 = n3;
            if (this.mSize >= this.mKeys.length) {
                this.gc();
                n2 = ~ ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
            }
        }
        if (this.mSize >= this.mKeys.length) {
            n3 = PositionMap.idealIntArraySize(this.mSize + 1);
            arrn = new int[n3];
            arrobject = new Object[n3];
            System.arraycopy(this.mKeys, 0, arrn, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, arrobject, 0, this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
        if (this.mSize - n2 != 0) {
            arrn = this.mKeys;
            arrobject = this.mKeys;
            n3 = n2 + 1;
            System.arraycopy(arrn, n2, arrobject, n3, this.mSize - n2);
            System.arraycopy(this.mValues, n2, this.mValues, n3, this.mSize - n2);
        }
        this.mKeys[n2] = n;
        this.mValues[n2] = e;
        ++this.mSize;
    }

    public void remove(int n) {
        this.delete(n);
    }

    public void removeAt(int n) {
        if (this.mValues[n] != DELETED) {
            this.mValues[n] = DELETED;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int n, int n2) {
        n2 = Math.min(this.mSize, n2 + n);
        while (n < n2) {
            this.removeAt(n);
            ++n;
        }
    }

    public void removeKeyRange(ArrayList<E> arrayList, int n, int n2) {
    }

    public void setValueAt(int n, E e) {
        if (this.mGarbage) {
            this.gc();
        }
        this.mValues[n] = e;
    }

    public int size() {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (this.size() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.keyAt(i));
            stringBuilder.append('=');
            E e = this.valueAt(i);
            if (e != this) {
                stringBuilder.append(e);
                continue;
            }
            stringBuilder.append("(this Map)");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public E valueAt(int n) {
        if (this.mGarbage) {
            this.gc();
        }
        return (E)this.mValues[n];
    }

    static class ContainerHelpers {
        static final boolean[] EMPTY_BOOLEANS = new boolean[0];
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        ContainerHelpers() {
        }

        static int binarySearch(int[] arrn, int n, int n2) {
            --n;
            int n3 = 0;
            while (n3 <= n) {
                int n4 = n3 + n >>> 1;
                int n5 = arrn[n4];
                if (n5 < n2) {
                    n3 = n4 + 1;
                    continue;
                }
                if (n5 > n2) {
                    n = n4 - 1;
                    continue;
                }
                return n4;
            }
            return ~ n3;
        }
    }

}
