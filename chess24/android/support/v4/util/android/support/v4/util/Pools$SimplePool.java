/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;

public static class Pools.SimplePool<T>
implements Pools.Pool<T> {
    private final Object[] mPool;
    private int mPoolSize;

    public Pools.SimplePool(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.mPool = new Object[n];
    }

    private boolean isInPool(@NonNull T t) {
        for (int i = 0; i < this.mPoolSize; ++i) {
            if (this.mPool[i] != t) continue;
            return true;
        }
        return false;
    }

    @Override
    public T acquire() {
        if (this.mPoolSize > 0) {
            int n = this.mPoolSize - 1;
            Object object = this.mPool[n];
            this.mPool[n] = null;
            --this.mPoolSize;
            return (T)object;
        }
        return null;
    }

    @Override
    public boolean release(@NonNull T t) {
        if (this.isInPool(t)) {
            throw new IllegalStateException("Already in the pool!");
        }
        if (this.mPoolSize < this.mPool.length) {
            this.mPool[this.mPoolSize] = t;
            ++this.mPoolSize;
            return true;
        }
        return false;
    }
}
