/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;

static class SafeIterableMap.AscendingIterator<K, V>
extends SafeIterableMap.ListIterator<K, V> {
    SafeIterableMap.AscendingIterator(SafeIterableMap.Entry<K, V> entry, SafeIterableMap.Entry<K, V> entry2) {
        super(entry, entry2);
    }

    @Override
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> entry) {
        return entry.mPrevious;
    }

    @Override
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> entry) {
        return entry.mNext;
    }
}
