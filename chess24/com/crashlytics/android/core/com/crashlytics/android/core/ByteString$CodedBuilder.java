/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.CodedOutputStream;

static final class ByteString.CodedBuilder {
    private final byte[] buffer;
    private final CodedOutputStream output;

    private ByteString.CodedBuilder(int n) {
        this.buffer = new byte[n];
        this.output = CodedOutputStream.newInstance(this.buffer);
    }

    public ByteString build() {
        this.output.checkNoSpaceLeft();
        return new ByteString(this.buffer, null);
    }

    public CodedOutputStream getCodedOutput() {
        return this.output;
    }
}
