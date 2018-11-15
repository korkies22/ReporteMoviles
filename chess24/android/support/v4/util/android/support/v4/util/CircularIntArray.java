/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

public final class CircularIntArray {
    private int mCapacityBitmask;
    private int[] mElements;
    private int mHead;
    private int mTail;

    public CircularIntArray() {
        this(8);
    }

    public CircularIntArray(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        if (n > 1073741824) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
        int n2 = n;
        if (Integer.bitCount(n) != 1) {
            n2 = Integer.highestOneBit(n - 1) << 1;
        }
        this.mCapacityBitmask = n2 - 1;
        this.mElements = new int[n2];
    }

    private void doubleCapacity() {
        int n = this.mElements.length;
        int n2 = n - this.mHead;
        int n3 = n << 1;
        if (n3 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        int[] arrn = new int[n3];
        System.arraycopy(this.mElements, this.mHead, arrn, 0, n2);
        System.arraycopy(this.mElements, 0, arrn, n2, this.mHead);
        this.mElements = arrn;
        this.mHead = 0;
        this.mTail = n;
        this.mCapacityBitmask = n3 - 1;
    }

    public void addFirst(int n) {
        this.mHead = this.mHead - 1 & this.mCapacityBitmask;
        this.mElements[this.mHead] = n;
        if (this.mHead == this.mTail) {
            this.doubleCapacity();
        }
    }

    public void addLast(int n) {
        this.mElements[this.mTail] = n;
        this.mTail = this.mTail + 1 & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            this.doubleCapacity();
        }
    }

    public void clear() {
        this.mTail = this.mHead;
    }

    public int get(int n) {
        if (n >= 0 && n < this.size()) {
            int[] arrn = this.mElements;
            int n2 = this.mHead;
            return arrn[this.mCapacityBitmask & n2 + n];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getFirst() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[this.mHead];
    }

    public int getLast() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[this.mTail - 1 & this.mCapacityBitmask];
    }

    public boolean isEmpty() {
        if (this.mHead == this.mTail) {
            return true;
        }
        return false;
    }

    public int popFirst() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n = this.mElements[this.mHead];
        this.mHead = this.mHead + 1 & this.mCapacityBitmask;
        return n;
    }

    public int popLast() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n = this.mTail - 1 & this.mCapacityBitmask;
        int n2 = this.mElements[n];
        this.mTail = n;
        return n2;
    }

    public void removeFromEnd(int n) {
        if (n <= 0) {
            return;
        }
        if (n > this.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n2 = this.mTail;
        this.mTail = this.mCapacityBitmask & n2 - n;
    }

    public void removeFromStart(int n) {
        if (n <= 0) {
            return;
        }
        if (n > this.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n2 = this.mHead;
        this.mHead = this.mCapacityBitmask & n2 + n;
    }

    public int size() {
        return this.mTail - this.mHead & this.mCapacityBitmask;
    }
}
