/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.share.widget.LikeView;

@Deprecated
public static enum LikeView.Style {
    STANDARD("standard", 0),
    BUTTON("button", 1),
    BOX_COUNT("box_count", 2);
    
    static LikeView.Style DEFAULT;
    private int intValue;
    private String stringValue;

    static {
        DEFAULT = STANDARD;
    }

    private LikeView.Style(String string2, int n2) {
        this.stringValue = string2;
        this.intValue = n2;
    }

    static /* synthetic */ int access$000(LikeView.Style style) {
        return style.getValue();
    }

    static LikeView.Style fromInt(int n) {
        for (LikeView.Style style : LikeView.Style.values()) {
            if (style.getValue() != n) continue;
            return style;
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
