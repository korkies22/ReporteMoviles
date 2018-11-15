/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.widget;

class ContentLoadingProgressBar
implements Runnable {
    ContentLoadingProgressBar() {
    }

    @Override
    public void run() {
        ContentLoadingProgressBar.this.mPostedHide = false;
        ContentLoadingProgressBar.this.mStartTime = -1L;
        ContentLoadingProgressBar.this.setVisibility(8);
    }
}
