/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class FormatException
extends ReaderException {
    private static final FormatException INSTANCE;

    static {
        FormatException formatException;
        INSTANCE = formatException = new FormatException();
        formatException.setStackTrace(NO_TRACE);
    }

    private FormatException() {
    }

    private FormatException(Throwable throwable) {
        super(throwable);
    }

    public static FormatException getFormatInstance() {
        if (isStackTrace) {
            return new FormatException();
        }
        return INSTANCE;
    }

    public static FormatException getFormatInstance(Throwable throwable) {
        if (isStackTrace) {
            return new FormatException(throwable);
        }
        return INSTANCE;
    }
}
