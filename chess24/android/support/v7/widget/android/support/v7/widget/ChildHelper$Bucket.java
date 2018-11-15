/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.ChildHelper;

static class ChildHelper.Bucket {
    static final int BITS_PER_WORD = 64;
    static final long LAST_BIT = Long.MIN_VALUE;
    long mData = 0L;
    ChildHelper.Bucket mNext;

    ChildHelper.Bucket() {
    }

    private void ensureNext() {
        if (this.mNext == null) {
            this.mNext = new ChildHelper.Bucket();
        }
    }

    void clear(int n) {
        if (n >= 64) {
            if (this.mNext != null) {
                this.mNext.clear(n - 64);
                return;
            }
        } else {
            this.mData &= 1L << n ^ -1L;
        }
    }

    int countOnesBefore(int n) {
        if (this.mNext == null) {
            if (n >= 64) {
                return Long.bitCount(this.mData);
            }
            return Long.bitCount(this.mData & (1L << n) - 1L);
        }
        if (n < 64) {
            return Long.bitCount(this.mData & (1L << n) - 1L);
        }
        return this.mNext.countOnesBefore(n - 64) + Long.bitCount(this.mData);
    }

    boolean get(int n) {
        if (n >= 64) {
            this.ensureNext();
            return this.mNext.get(n - 64);
        }
        if ((this.mData & 1L << n) != 0L) {
            return true;
        }
        return false;
    }

    void insert(int n, boolean bl) {
        if (n >= 64) {
            this.ensureNext();
            this.mNext.insert(n - 64, bl);
            return;
        }
        boolean bl2 = (this.mData & Long.MIN_VALUE) != 0L;
        long l = (1L << n) - 1L;
        this.mData = this.mData & l | (this.mData & (l ^ -1L)) << 1;
        if (bl) {
            this.set(n);
        } else {
            this.clear(n);
        }
        if (bl2 || this.mNext != null) {
            this.ensureNext();
            this.mNext.insert(0, bl2);
        }
    }

    boolean remove(int n) {
        if (n >= 64) {
            this.ensureNext();
            return this.mNext.remove(n - 64);
        }
        long l = 1L << n;
        boolean bl = (this.mData & l) != 0L;
        this.mData &= l ^ -1L;
        this.mData = this.mData & l | Long.rotateRight(this.mData & (--l ^ -1L), 1);
        if (this.mNext != null) {
            if (this.mNext.get(0)) {
                this.set(63);
            }
            this.mNext.remove(0);
        }
        return bl;
    }

    void reset() {
        this.mData = 0L;
        if (this.mNext != null) {
            this.mNext.reset();
        }
    }

    void set(int n) {
        if (n >= 64) {
            this.ensureNext();
            this.mNext.set(n - 64);
            return;
        }
        this.mData |= 1L << n;
    }

    public String toString() {
        if (this.mNext == null) {
            return Long.toBinaryString(this.mData);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mNext.toString());
        stringBuilder.append("xx");
        stringBuilder.append(Long.toBinaryString(this.mData));
        return stringBuilder.toString();
    }
}
