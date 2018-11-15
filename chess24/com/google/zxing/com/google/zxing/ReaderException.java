/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

public abstract class ReaderException
extends Exception {
    protected static final StackTraceElement[] NO_TRACE;
    protected static final boolean isStackTrace;

    static {
        boolean bl = System.getProperty("surefire.test.class.path") != null;
        isStackTrace = bl;
        NO_TRACE = new StackTraceElement[0];
    }

    ReaderException() {
    }

    ReaderException(Throwable throwable) {
        super(throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final Throwable fillInStackTrace() {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }
}
