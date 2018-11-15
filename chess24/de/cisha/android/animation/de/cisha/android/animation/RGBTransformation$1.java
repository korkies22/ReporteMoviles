/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.animation;

import android.widget.TextView;

class RGBTransformation
implements Runnable {
    final /* synthetic */ int val$rgb;

    RGBTransformation(int n) {
        this.val$rgb = n;
    }

    @Override
    public void run() {
        RGBTransformation.this._view.setTextColor(this.val$rgb);
    }
}
