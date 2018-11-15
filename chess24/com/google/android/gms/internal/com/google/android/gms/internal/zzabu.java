/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class zzabu
extends ImageView {
    private Uri zzaDu;
    private int zzaDv;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
    }

    public void zzcK(int n) {
        this.zzaDv = n;
    }

    public void zzr(Uri uri) {
        this.zzaDu = uri;
    }

    public int zzwO() {
        return this.zzaDv;
    }
}
