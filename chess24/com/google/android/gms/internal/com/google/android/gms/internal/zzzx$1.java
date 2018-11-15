/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

final class zzzx
extends ThreadLocal<Boolean> {
    zzzx() {
    }

    @Override
    protected /* synthetic */ Object initialValue() {
        return this.zzvg();
    }

    protected Boolean zzvg() {
        return false;
    }
}
