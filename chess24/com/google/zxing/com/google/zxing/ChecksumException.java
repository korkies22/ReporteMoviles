/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class ChecksumException
extends ReaderException {
    private static final ChecksumException INSTANCE;

    static {
        ChecksumException checksumException;
        INSTANCE = checksumException = new ChecksumException();
        checksumException.setStackTrace(NO_TRACE);
    }

    private ChecksumException() {
    }

    private ChecksumException(Throwable throwable) {
        super(throwable);
    }

    public static ChecksumException getChecksumInstance() {
        if (isStackTrace) {
            return new ChecksumException();
        }
        return INSTANCE;
    }

    public static ChecksumException getChecksumInstance(Throwable throwable) {
        if (isStackTrace) {
            return new ChecksumException(throwable);
        }
        return INSTANCE;
    }
}
