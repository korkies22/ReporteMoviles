/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.WireFormat;

static enum WireFormat.JavaType {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(0.0),
    BOOLEAN(false),
    STRING(""),
    BYTE_STRING(ByteString.EMPTY),
    ENUM(null),
    MESSAGE(null);
    
    private final Object defaultDefault;

    private WireFormat.JavaType(Object object) {
        this.defaultDefault = object;
    }

    Object getDefaultDefault() {
        return this.defaultDefault;
    }
}
