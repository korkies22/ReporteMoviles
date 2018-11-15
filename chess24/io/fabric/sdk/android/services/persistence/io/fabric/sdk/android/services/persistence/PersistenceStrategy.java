/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.persistence;

public interface PersistenceStrategy<T> {
    public void clear();

    public T restore();

    public void save(T var1);
}
