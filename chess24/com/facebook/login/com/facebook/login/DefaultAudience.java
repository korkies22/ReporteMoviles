/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login;

public enum DefaultAudience {
    NONE(null),
    ONLY_ME("only_me"),
    FRIENDS("friends"),
    EVERYONE("everyone");
    
    private final String nativeProtocolAudience;

    private DefaultAudience(String string2) {
        this.nativeProtocolAudience = string2;
    }

    public String getNativeProtocolAudience() {
        return this.nativeProtocolAudience;
    }
}
