/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.ViewBoundsCheck;

static class ViewBoundsCheck.BoundFlags {
    int mBoundFlags = 0;
    int mChildEnd;
    int mChildStart;
    int mRvEnd;
    int mRvStart;

    ViewBoundsCheck.BoundFlags() {
    }

    void addFlags(int n) {
        this.mBoundFlags = n | this.mBoundFlags;
    }

    boolean boundsMatch() {
        if ((this.mBoundFlags & 7) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvStart) << 0) == 0) {
            return false;
        }
        if ((this.mBoundFlags & 112) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvEnd) << 4) == 0) {
            return false;
        }
        if ((this.mBoundFlags & 1792) != 0 && (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvStart) << 8) == 0) {
            return false;
        }
        if ((this.mBoundFlags & 28672) != 0 && (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvEnd) << 12) == 0) {
            return false;
        }
        return true;
    }

    int compare(int n, int n2) {
        if (n > n2) {
            return 1;
        }
        if (n == n2) {
            return 2;
        }
        return 4;
    }

    void resetFlags() {
        this.mBoundFlags = 0;
    }

    void setBounds(int n, int n2, int n3, int n4) {
        this.mRvStart = n;
        this.mRvEnd = n2;
        this.mChildStart = n3;
        this.mChildEnd = n4;
    }

    void setFlags(int n, int n2) {
        this.mBoundFlags = n & n2 | this.mBoundFlags & ~ n2;
    }
}
