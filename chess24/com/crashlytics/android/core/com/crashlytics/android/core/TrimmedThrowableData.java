/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.StackTraceTrimmingStrategy;

class TrimmedThrowableData {
    public final TrimmedThrowableData cause;
    public final String className;
    public final String localizedMessage;
    public final StackTraceElement[] stacktrace;

    public TrimmedThrowableData(Throwable object, StackTraceTrimmingStrategy stackTraceTrimmingStrategy) {
        this.localizedMessage = object.getLocalizedMessage();
        this.className = object.getClass().getName();
        this.stacktrace = stackTraceTrimmingStrategy.getTrimmedStackTrace(object.getStackTrace());
        object = object.getCause();
        object = object != null ? new TrimmedThrowableData((Throwable)object, stackTraceTrimmingStrategy) : null;
        this.cause = object;
    }
}
