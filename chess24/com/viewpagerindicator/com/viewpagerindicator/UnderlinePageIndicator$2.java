/*
 * Decompiled with CFR 0_134.
 */
package com.viewpagerindicator;

class UnderlinePageIndicator
implements Runnable {
    UnderlinePageIndicator() {
    }

    @Override
    public void run() {
        if (UnderlinePageIndicator.this.mFades) {
            UnderlinePageIndicator.this.post(UnderlinePageIndicator.this.mFadeRunnable);
        }
    }
}
