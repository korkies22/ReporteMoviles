/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.share.widget.LikeView;

@Deprecated
public static enum LikeView.AuxiliaryViewPosition {
    BOTTOM("bottom", 0),
    INLINE("inline", 1),
    TOP("top", 2);
    
    static LikeView.AuxiliaryViewPosition DEFAULT;
    private int intValue;
    private String stringValue;

    static {
        DEFAULT = BOTTOM;
    }

    private LikeView.AuxiliaryViewPosition(String string2, int n2) {
        this.stringValue = string2;
        this.intValue = n2;
    }

    static /* synthetic */ int access$100(LikeView.AuxiliaryViewPosition auxiliaryViewPosition) {
        return auxiliaryViewPosition.getValue();
    }

    static LikeView.AuxiliaryViewPosition fromInt(int n) {
        for (LikeView.AuxiliaryViewPosition auxiliaryViewPosition : LikeView.AuxiliaryViewPosition.values()) {
            if (auxiliaryViewPosition.getValue() != n) continue;
            return auxiliaryViewPosition;
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
