/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.cache;

import android.content.Context;

public interface ValueLoader<T> {
    public T load(Context var1) throws Exception;
}
