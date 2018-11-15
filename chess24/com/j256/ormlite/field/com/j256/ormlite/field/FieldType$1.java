/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.field.FieldType;

static final class FieldType
extends ThreadLocal<FieldType.LevelCounters> {
    FieldType() {
    }

    @Override
    protected FieldType.LevelCounters initialValue() {
        return new FieldType.LevelCounters(null);
    }
}
