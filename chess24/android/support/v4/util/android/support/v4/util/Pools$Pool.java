/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;

public static interface Pools.Pool<T> {
    @Nullable
    public T acquire();

    public boolean release(@NonNull T var1);
}
