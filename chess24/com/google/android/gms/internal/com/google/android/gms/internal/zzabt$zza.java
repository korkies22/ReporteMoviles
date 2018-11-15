/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.zzabt;

private static final class zzabt.zza
extends Drawable {
    private static final zzabt.zza zzaDr = new zzabt.zza();
    private static final zza zzaDs = new zza();

    private zzabt.zza() {
    }

    public void draw(Canvas canvas) {
    }

    public Drawable.ConstantState getConstantState() {
        return zzaDs;
    }

    public int getOpacity() {
        return -2;
    }

    public void setAlpha(int n) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    private static final class zza
    extends Drawable.ConstantState {
        private zza() {
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return zzaDr;
        }
    }

}
