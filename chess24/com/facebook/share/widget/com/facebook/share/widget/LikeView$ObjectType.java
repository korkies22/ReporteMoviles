/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.share.widget.LikeView;

@Deprecated
public static enum LikeView.ObjectType {
    UNKNOWN("unknown", 0),
    OPEN_GRAPH("open_graph", 1),
    PAGE("page", 2);
    
    public static LikeView.ObjectType DEFAULT = UNKNOWN;
    private int intValue;
    private String stringValue;

    private LikeView.ObjectType(String string2, int n2) {
        this.stringValue = string2;
        this.intValue = n2;
    }

    public static LikeView.ObjectType fromInt(int n) {
        for (LikeView.ObjectType objectType : LikeView.ObjectType.values()) {
            if (objectType.getValue() != n) continue;
            return objectType;
        }
        return null;
    }

    public int getValue() {
        return this.intValue;
    }

    public String toString() {
        return this.stringValue;
    }
}
