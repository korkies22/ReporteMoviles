/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.cache;

import android.content.Context;
import io.fabric.sdk.android.services.cache.ValueLoader;

public interface ValueCache<T> {
    public T get(Context var1, ValueLoader<T> var2) throws Exception;

    public void invalidate(Context var1);
}
