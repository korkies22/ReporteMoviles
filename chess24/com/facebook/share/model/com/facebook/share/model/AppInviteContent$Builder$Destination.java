/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import com.facebook.share.model.AppInviteContent;

@Deprecated
public static enum AppInviteContent.Builder.Destination {
    FACEBOOK("facebook"),
    MESSENGER("messenger");
    
    private final String name;

    private AppInviteContent.Builder.Destination(String string2) {
        this.name = string2;
    }

    public boolean equalsName(String string) {
        if (string == null) {
            return false;
        }
        return this.name.equals(string);
    }

    public String toString() {
        return this.name;
    }
}
