/*
 * Decompiled with CFR 0_134.
 */
package com.viewpagerindicator;

import com.viewpagerindicator.TitlePageIndicator;

public static enum TitlePageIndicator.LinePosition {
    Bottom(0),
    Top(1);
    
    public final int value;

    private TitlePageIndicator.LinePosition(int n2) {
        this.value = n2;
    }

    public static TitlePageIndicator.LinePosition fromValue(int n) {
        for (TitlePageIndicator.LinePosition linePosition : TitlePageIndicator.LinePosition.values()) {
            if (linePosition.value != n) continue;
            return linePosition;
        }
        return null;
    }
}
