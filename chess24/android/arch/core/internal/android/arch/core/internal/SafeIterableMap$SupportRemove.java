/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.internal;

import android.arch.core.internal.SafeIterableMap;
import android.support.annotation.NonNull;

static interface SafeIterableMap.SupportRemove<K, V> {
    public void supportRemove(@NonNull SafeIterableMap.Entry<K, V> var1);
}
