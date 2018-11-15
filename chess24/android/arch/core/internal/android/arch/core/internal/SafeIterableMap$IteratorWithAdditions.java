/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;
import android.support.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;

private class SafeIterableMap.IteratorWithAdditions
implements Iterator<Map.Entry<K, V>>,
SafeIterableMap.SupportRemove<K, V> {
    private boolean mBeforeStart = true;
    private SafeIterableMap.Entry<K, V> mCurrent;

    private SafeIterableMap.IteratorWithAdditions() {
    }

    @Override
    public boolean hasNext() {
        boolean bl = this.mBeforeStart;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) {
            if (SafeIterableMap.this.mStart != null) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = bl2;
        if (this.mCurrent != null) {
            bl3 = bl2;
            if (this.mCurrent.mNext != null) {
                bl3 = true;
            }
        }
        return bl3;
    }

    @Override
    public Map.Entry<K, V> next() {
        if (this.mBeforeStart) {
            this.mBeforeStart = false;
            this.mCurrent = SafeIterableMap.this.mStart;
        } else {
            SafeIterableMap.Entry entry = this.mCurrent != null ? this.mCurrent.mNext : null;
            this.mCurrent = entry;
        }
        return this.mCurrent;
    }

    @Override
    public void supportRemove(@NonNull SafeIterableMap.Entry<K, V> entry) {
        if (entry == this.mCurrent) {
            this.mCurrent = this.mCurrent.mPrevious;
            boolean bl = this.mCurrent == null;
            this.mBeforeStart = bl;
        }
    }
}
