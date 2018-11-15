/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;

private static class SafeIterableMap.DescendingIterator<K, V>
extends SafeIterableMap.ListIterator<K, V> {
    SafeIterableMap.DescendingIterator(SafeIterableMap.Entry<K, V> entry, SafeIterableMap.Entry<K, V> entry2) {
        super(entry, entry2);
    }

    @Override
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> entry) {
        return entry.mNext;
    }

    @Override
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> entry) {
        return entry.mPrevious;
    }
}
