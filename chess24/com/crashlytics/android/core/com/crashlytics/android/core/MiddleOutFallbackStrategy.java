/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.MiddleOutStrategy;
import com.crashlytics.android.core.StackTraceTrimmingStrategy;

class MiddleOutFallbackStrategy
implements StackTraceTrimmingStrategy {
    private final int maximumStackSize;
    private final MiddleOutStrategy middleOutStrategy;
    private final StackTraceTrimmingStrategy[] trimmingStrategies;

    public /* varargs */ MiddleOutFallbackStrategy(int n, StackTraceTrimmingStrategy ... arrstackTraceTrimmingStrategy) {
        this.maximumStackSize = n;
        this.trimmingStrategies = arrstackTraceTrimmingStrategy;
        this.middleOutStrategy = new MiddleOutStrategy(n);
    }

    @Override
    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] arrstackTraceElement) {
        if (arrstackTraceElement.length <= this.maximumStackSize) {
            return arrstackTraceElement;
        }
        StackTraceTrimmingStrategy[] arrstackTraceTrimmingStrategy = this.trimmingStrategies;
        int n = arrstackTraceTrimmingStrategy.length;
        StackTraceElement[] arrstackTraceElement2 = arrstackTraceElement;
        for (int i = 0; i < n; ++i) {
            StackTraceTrimmingStrategy stackTraceTrimmingStrategy = arrstackTraceTrimmingStrategy[i];
            if (arrstackTraceElement2.length <= this.maximumStackSize) break;
            arrstackTraceElement2 = stackTraceTrimmingStrategy.getTrimmedStackTrace(arrstackTraceElement);
        }
        arrstackTraceElement = arrstackTraceElement2;
        if (arrstackTraceElement2.length > this.maximumStackSize) {
            arrstackTraceElement = this.middleOutStrategy.getTrimmedStackTrace(arrstackTraceElement2);
        }
        return arrstackTraceElement;
    }
}
