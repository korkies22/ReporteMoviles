/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package io.fabric.sdk.android.services.persistence;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import io.fabric.sdk.android.services.persistence.PersistenceStrategy;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;

public class PreferenceStoreStrategy<T>
implements PersistenceStrategy<T> {
    private final String key;
    private final SerializationStrategy<T> serializer;
    private final PreferenceStore store;

    public PreferenceStoreStrategy(PreferenceStore preferenceStore, SerializationStrategy<T> serializationStrategy, String string) {
        this.store = preferenceStore;
        this.serializer = serializationStrategy;
        this.key = string;
    }

    @SuppressLint(value={"CommitPrefEdits"})
    @Override
    public void clear() {
        this.store.edit().remove(this.key).commit();
    }

    @Override
    public T restore() {
        SharedPreferences sharedPreferences = this.store.get();
        return this.serializer.deserialize(sharedPreferences.getString(this.key, null));
    }

    @SuppressLint(value={"CommitPrefEdits"})
    @Override
    public void save(T t) {
        this.store.save(this.store.edit().putString(this.key, this.serializer.serialize(t)));
    }
}
