/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.cache;

import android.content.Context;
import io.fabric.sdk.android.services.cache.ValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public abstract class AbstractValueCache<T>
implements ValueCache<T> {
    private final ValueCache<T> childCache;

    public AbstractValueCache() {
        this(null);
    }

    public AbstractValueCache(ValueCache<T> valueCache) {
        this.childCache = valueCache;
    }

    private void cache(Context context, T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        this.cacheValue(context, t);
    }

    protected abstract void cacheValue(Context var1, T var2);

    protected abstract void doInvalidate(Context var1);

    @Override
    public final T get(Context context, ValueLoader<T> valueLoader) throws Exception {
        synchronized (this) {
            Object object;
            block4 : {
                T t;
                object = t = this.getCached(context);
                if (t != null) break block4;
                valueLoader = this.childCache != null ? this.childCache.get(context, valueLoader) : valueLoader.load(context);
                this.cache(context, valueLoader);
                object = valueLoader;
            }
            return object;
        }
    }

    protected abstract T getCached(Context var1);

    @Override
    public final void invalidate(Context context) {
        synchronized (this) {
            this.doInvalidate(context);
            return;
        }
    }
}
