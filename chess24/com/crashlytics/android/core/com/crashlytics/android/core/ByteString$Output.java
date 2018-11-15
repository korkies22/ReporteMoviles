/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStream;

static final class ByteString.Output
extends FilterOutputStream {
    private final ByteArrayOutputStream bout;

    private ByteString.Output(ByteArrayOutputStream byteArrayOutputStream) {
        super(byteArrayOutputStream);
        this.bout = byteArrayOutputStream;
    }

    public ByteString toByteString() {
        return new ByteString(this.bout.toByteArray(), null);
    }
}
