/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;
import android.support.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;

private static abstract class SafeIterableMap.ListIterator<K, V>
implements Iterator<Map.Entry<K, V>>,
SafeIterableMap.SupportRemove<K, V> {
    SafeIterableMap.Entry<K, V> mExpectedEnd;
    SafeIterableMap.Entry<K, V> mNext;

    SafeIterableMap.ListIterator(SafeIterableMap.Entry<K, V> entry, SafeIterableMap.Entry<K, V> entry2) {
        this.mExpectedEnd = entry2;
        this.mNext = entry;
    }

    private SafeIterableMap.Entry<K, V> nextNode() {
        if (this.mNext != this.mExpectedEnd && this.mExpectedEnd != null) {
            return this.forward(this.mNext);
        }
        return null;
    }

    abstract SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> var1);

    abstract SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> var1);

    @Override
    public boolean hasNext() {
        if (this.mNext != null) {
            return true;
        }
        return false;
    }

    @Override
    public Map.Entry<K, V> next() {
        SafeIterableMap.Entry<K, V> entry = this.mNext;
        this.mNext = this.nextNode();
        return entry;
    }

    @Override
    public void supportRemove(@NonNull SafeIterableMap.Entry<K, V> entry) {
        if (this.mExpectedEnd == entry && entry == this.mNext) {
            this.mNext = null;
            this.mExpectedEnd = null;
        }
        if (this.mExpectedEnd == entry) {
            this.mExpectedEnd = this.backward(this.mExpectedEnd);
        }
        if (this.mNext == entry) {
            this.mNext = this.nextNode();
        }
    }
}
