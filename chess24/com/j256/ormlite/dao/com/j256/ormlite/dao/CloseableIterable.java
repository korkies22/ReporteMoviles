/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.CloseableIterator;

public interface CloseableIterable<T>
extends Iterable<T> {
    public CloseableIterator<T> closeableIterator();
}
