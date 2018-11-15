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
        ContentLoadingProgressBar.this.mPostedShow = false;
        if (!ContentLoadingProgressBar.this.mDismissed) {
            ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
            ContentLoadingProgressBar.this.setVisibility(0);
        }
    }
}
