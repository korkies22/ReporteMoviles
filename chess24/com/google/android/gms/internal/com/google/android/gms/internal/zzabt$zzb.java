/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.zzabt;

static final class zzabt.zzb
extends Drawable.ConstantState {
    int mChangingConfigurations;
    int zzaDt;

    zzabt.zzb(zzabt.zzb zzb2) {
        if (zzb2 != null) {
            this.mChangingConfigurations = zzb2.mChangingConfigurations;
            this.zzaDt = zzb2.zzaDt;
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public Drawable newDrawable() {
        return new zzabt(this);
    }
}
