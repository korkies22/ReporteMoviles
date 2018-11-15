/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.CollectionMapper;
import java.util.Iterator;

public static interface CollectionMapper.Collection<T> {
    public Object get(T var1);

    public Iterator<T> keyIterator();

    public void set(T var1, Object var2, CollectionMapper.OnErrorListener var3);
}
