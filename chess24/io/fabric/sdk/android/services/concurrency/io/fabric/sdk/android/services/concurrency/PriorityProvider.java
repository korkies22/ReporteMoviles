/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Priority;

public interface PriorityProvider<T>
extends Comparable<T> {
    public Priority getPriority();
}
