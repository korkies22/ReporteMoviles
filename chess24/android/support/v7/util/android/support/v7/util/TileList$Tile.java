/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.TileList;
import java.lang.reflect.Array;

public static class TileList.Tile<T> {
    public int mItemCount;
    public final T[] mItems;
    TileList.Tile<T> mNext;
    public int mStartPosition;

    public TileList.Tile(Class<T> class_, int n) {
        this.mItems = (Object[])Array.newInstance(class_, n);
    }

    boolean containsPosition(int n) {
        if (this.mStartPosition <= n && n < this.mStartPosition + this.mItemCount) {
            return true;
        }
        return false;
    }

    T getByPosition(int n) {
        return this.mItems[n - this.mStartPosition];
    }
}
