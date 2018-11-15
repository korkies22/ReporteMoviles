/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzrw;

public abstract class zzru
extends zzrt {
    private boolean zzacO;

    protected zzru(zzrw zzrw2) {
        super(zzrw2);
    }

    public void initialize() {
        this.zzmr();
        this.zzacO = true;
    }

    public boolean isInitialized() {
        return this.zzacO;
    }

    protected abstract void zzmr();

    protected void zznA() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
