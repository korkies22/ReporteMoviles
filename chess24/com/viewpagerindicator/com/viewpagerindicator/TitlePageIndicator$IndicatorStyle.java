/*
 * Decompiled with CFR 0_134.
 */
package com.viewpagerindicator;

import com.viewpagerindicator.TitlePageIndicator;

public static enum TitlePageIndicator.IndicatorStyle {
    None(0),
    Triangle(1),
    Underline(2);
    
    public final int value;

    private TitlePageIndicator.IndicatorStyle(int n2) {
        this.value = n2;
    }

    public static TitlePageIndicator.IndicatorStyle fromValue(int n) {
        for (TitlePageIndicator.IndicatorStyle indicatorStyle : TitlePageIndicator.IndicatorStyle.values()) {
            if (indicatorStyle.value != n) continue;
            return indicatorStyle;
        }
        return null;
    }
}
