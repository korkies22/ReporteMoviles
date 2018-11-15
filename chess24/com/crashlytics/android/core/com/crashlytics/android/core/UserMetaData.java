/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

public class UserMetaData {
    public static final UserMetaData EMPTY = new UserMetaData();
    public final String email;
    public final String id;
    public final String name;

    public UserMetaData() {
        this(null, null, null);
    }

    public UserMetaData(String string, String string2, String string3) {
        this.id = string;
        this.name = string2;
        this.email = string3;
    }

    public boolean isEmpty() {
        if (this.id == null && this.name == null && this.email == null) {
            return true;
        }
        return false;
    }
}
