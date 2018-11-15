/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.WorkerThread;
import android.support.v7.util.AsyncListUtil;

public static abstract class AsyncListUtil.DataCallback<T> {
    @WorkerThread
    public abstract void fillData(T[] var1, int var2, int var3);

    @WorkerThread
    public int getMaxCachedTiles() {
        return 10;
    }

    @WorkerThread
    public void recycleData(T[] arrT, int n) {
    }

    @WorkerThread
    public abstract int refreshData();
}
