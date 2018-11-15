/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.WireFormat;

static final class WireFormat.FieldType
extends WireFormat.FieldType {
    WireFormat.FieldType(String string2, int n2, WireFormat.JavaType javaType, int n3) {
    }

    @Override
    public boolean isPackable() {
        return false;
    }
}
