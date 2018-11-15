/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login.widget;

import com.facebook.login.widget.LoginButton;

public static enum LoginButton.ToolTipMode {
    AUTOMATIC("automatic", 0),
    DISPLAY_ALWAYS("display_always", 1),
    NEVER_DISPLAY("never_display", 2);
    
    public static LoginButton.ToolTipMode DEFAULT;
    private int intValue;
    private String stringValue;

    static {
        DEFAULT = AUTOMATIC;
    }

    private LoginButton.ToolTipMode(String string2, int n2) {
        this.stringValue = string2;
        this.intValue = n2;
    }

    public static LoginButton.ToolTipMode fromInt(int n) {
        for (LoginButton.ToolTipMode toolTipMode : LoginButton.ToolTipMode.values()) {
            if (toolTipMode.getValue() != n) continue;
            return toolTipMode;
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
