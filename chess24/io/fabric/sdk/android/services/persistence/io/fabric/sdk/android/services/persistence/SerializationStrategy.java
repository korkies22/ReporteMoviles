/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.persistence;

public interface SerializationStrategy<T> {
    public T deserialize(String var1);

    public String serialize(T var1);
}
