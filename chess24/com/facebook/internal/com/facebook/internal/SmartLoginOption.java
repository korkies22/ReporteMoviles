/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import java.util.EnumSet;

public enum SmartLoginOption {
    None(0L),
    Enabled(1L),
    RequireConfirm(2L);
    
    public static final EnumSet<SmartLoginOption> ALL = EnumSet.allOf(SmartLoginOption.class);
    private final long mValue;

    private SmartLoginOption(long l) {
        this.mValue = l;
    }

    public static EnumSet<SmartLoginOption> parseOptions(long l) {
        EnumSet<SmartLoginOption> enumSet = EnumSet.noneOf(SmartLoginOption.class);
        for (SmartLoginOption smartLoginOption : ALL) {
            if ((l & smartLoginOption.getValue()) == 0L) continue;
            enumSet.add(smartLoginOption);
        }
        return enumSet;
    }

    public long getValue() {
        return this.mValue;
    }
}
