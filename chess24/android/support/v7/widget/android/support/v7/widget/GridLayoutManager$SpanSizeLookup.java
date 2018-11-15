/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 */
package android.support.v7.widget;

import android.support.v7.widget.GridLayoutManager;
import android.util.SparseIntArray;

public static abstract class GridLayoutManager.SpanSizeLookup {
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanIndexCache = new SparseIntArray();

    int findReferenceIndexFromCache(int n) {
        int n2 = this.mSpanIndexCache.size() - 1;
        int n3 = 0;
        while (n3 <= n2) {
            int n4 = n3 + n2 >>> 1;
            if (this.mSpanIndexCache.keyAt(n4) < n) {
                n3 = n4 + 1;
                continue;
            }
            n2 = n4 - 1;
        }
        n = n3 - 1;
        if (n >= 0 && n < this.mSpanIndexCache.size()) {
            return this.mSpanIndexCache.keyAt(n);
        }
        return -1;
    }

    int getCachedSpanIndex(int n, int n2) {
        if (!this.mCacheSpanIndices) {
            return this.getSpanIndex(n, n2);
        }
        int n3 = this.mSpanIndexCache.get(n, -1);
        if (n3 != -1) {
            return n3;
        }
        n2 = this.getSpanIndex(n, n2);
        this.mSpanIndexCache.put(n, n2);
        return n2;
    }

    public int getSpanGroupIndex(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.getSpanSize(n);
        int n6 = n3 = (n4 = 0);
        while (n4 < n) {
            int n7;
            int n8 = this.getSpanSize(n4);
            int n9 = n3 + n8;
            if (n9 == n2) {
                n7 = n6 + 1;
                n3 = 0;
            } else {
                n3 = n9;
                n7 = n6;
                if (n9 > n2) {
                    n7 = n6 + 1;
                    n3 = n8;
                }
            }
            ++n4;
            n6 = n7;
        }
        n = n6;
        if (n3 + n5 > n2) {
            n = n6 + 1;
        }
        return n;
    }

    public int getSpanIndex(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.getSpanSize(n);
        if (n5 == n2) {
            return 0;
        }
        if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0 && (n4 = this.findReferenceIndexFromCache(n)) >= 0) {
            n3 = this.mSpanIndexCache.get(n4) + this.getSpanSize(n4);
            ++n4;
        } else {
            n3 = n4 = 0;
        }
        while (n4 < n) {
            int n6 = this.getSpanSize(n4);
            int n7 = n3 + n6;
            if (n7 == n2) {
                n3 = 0;
            } else {
                n3 = n7;
                if (n7 > n2) {
                    n3 = n6;
                }
            }
            ++n4;
        }
        if (n5 + n3 <= n2) {
            return n3;
        }
        return 0;
    }

    public abstract int getSpanSize(int var1);

    public void invalidateSpanIndexCache() {
        this.mSpanIndexCache.clear();
    }

    public boolean isSpanIndexCacheEnabled() {
        return this.mCacheSpanIndices;
    }

    public void setSpanIndexCacheEnabled(boolean bl) {
        this.mCacheSpanIndices = bl;
    }
}
