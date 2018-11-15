/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

class ProcMapEntry {
    public final long address;
    public final String path;
    public final String perms;
    public final long size;

    public ProcMapEntry(long l, long l2, String string, String string2) {
        this.address = l;
        this.size = l2;
        this.perms = string;
        this.path = string2;
    }
}
