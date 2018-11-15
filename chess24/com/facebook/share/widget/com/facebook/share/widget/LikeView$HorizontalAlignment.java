/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.share.widget.LikeView;

@Deprecated
public static enum LikeView.HorizontalAlignment {
    CENTER("center", 0),
    LEFT("left", 1),
    RIGHT("right", 2);
    
    static LikeView.HorizontalAlignment DEFAULT;
    private int intValue;
    private String stringValue;

    static {
        DEFAULT = CENTER;
    }

    private LikeView.HorizontalAlignment(String string2, int n2) {
        this.stringValue = string2;
        this.intValue = n2;
    }

    static /* synthetic */ int access$200(LikeView.HorizontalAlignment horizontalAlignment) {
        return horizontalAlignment.getValue();
    }

    static LikeView.HorizontalAlignment fromInt(int n) {
        for (LikeView.HorizontalAlignment horizontalAlignment : LikeView.HorizontalAlignment.values()) {
            if (horizontalAlignment.getValue() != n) continue;
            return horizontalAlignment;
        }
        return null;
    }

    private int getValue() {
        return this.intValue;
    }

    public String toString() {
        return this.stringValue;
    }
}
