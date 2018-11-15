/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools;

public static class Pools.SynchronizedPool<T>
extends Pools.SimplePool<T> {
    private final Object mLock = new Object();

    public Pools.SynchronizedPool(int n) {
        super(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public T acquire() {
        Object object = this.mLock;
        synchronized (object) {
            Object t = super.acquire();
            return t;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean release(@NonNull T t) {
        Object object = this.mLock;
        synchronized (object) {
            return super.release(t);
        }
    }
}
