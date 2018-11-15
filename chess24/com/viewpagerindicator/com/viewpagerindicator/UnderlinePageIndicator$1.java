/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 */
package com.viewpagerindicator;

import android.graphics.Paint;

class UnderlinePageIndicator
implements Runnable {
    UnderlinePageIndicator() {
    }

    @Override
    public void run() {
        if (!UnderlinePageIndicator.this.mFades) {
            return;
        }
        int n = Math.max(UnderlinePageIndicator.this.mPaint.getAlpha() - UnderlinePageIndicator.this.mFadeBy, 0);
        UnderlinePageIndicator.this.mPaint.setAlpha(n);
        UnderlinePageIndicator.this.invalidate();
        if (n > 0) {
            UnderlinePageIndicator.this.postDelayed((Runnable)this, 30L);
        }
    }
}
