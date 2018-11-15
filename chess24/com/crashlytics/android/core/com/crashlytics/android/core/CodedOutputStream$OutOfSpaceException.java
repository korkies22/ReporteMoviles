/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CodedOutputStream;
import java.io.IOException;

static class CodedOutputStream.OutOfSpaceException
extends IOException {
    private static final long serialVersionUID = -6947486886997889499L;

    CodedOutputStream.OutOfSpaceException() {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.");
    }
}
