/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;
import android.support.annotation.NonNull;
import java.util.Map;

static class SafeIterableMap.Entry<K, V>
implements Map.Entry<K, V> {
    @NonNull
    final K mKey;
    SafeIterableMap.Entry<K, V> mNext;
    SafeIterableMap.Entry<K, V> mPrevious;
    @NonNull
    final V mValue;

    SafeIterableMap.Entry(@NonNull K k, @NonNull V v) {
        this.mKey = k;
        this.mValue = v;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SafeIterableMap.Entry)) {
            return false;
        }
        object = (SafeIterableMap.Entry)object;
        if (this.mKey.equals(object.mKey) && this.mValue.equals(object.mValue)) {
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public K getKey() {
        return this.mKey;
    }

    @NonNull
    @Override
    public V getValue() {
        return this.mValue;
    }

    @Override
    public V setValue(V v) {
        throw new UnsupportedOperationException("An entry modification is not supported");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mKey);
        stringBuilder.append("=");
        stringBuilder.append(this.mValue);
        return stringBuilder.toString();
    }
}
