/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.PriorityProvider;

public enum Priority {
    LOW,
    NORMAL,
    HIGH,
    IMMEDIATE;
    

    private Priority() {
    }

    static <Y> int compareTo(PriorityProvider priorityProvider, Y object) {
        object = object instanceof PriorityProvider ? ((PriorityProvider)object).getPriority() : NORMAL;
        return object.ordinal() - priorityProvider.getPriority().ordinal();
    }
}
