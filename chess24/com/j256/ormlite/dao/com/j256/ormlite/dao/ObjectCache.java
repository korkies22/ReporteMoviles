/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

public interface ObjectCache {
    public <T> void clear(Class<T> var1);

    public void clearAll();

    public <T, ID> T get(Class<T> var1, ID var2);

    public <T, ID> void put(Class<T> var1, ID var2, T var3);

    public <T> void registerClass(Class<T> var1);

    public <T, ID> void remove(Class<T> var1, ID var2);

    public <T> int size(Class<T> var1);

    public int sizeAll();

    public <T, ID> T updateId(Class<T> var1, ID var2, ID var3);
}
