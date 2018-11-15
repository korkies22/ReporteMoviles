/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;

class RecyclerView
implements Runnable {
    RecyclerView() {
    }

    @Override
    public void run() {
        if (RecyclerView.this.mItemAnimator != null) {
            RecyclerView.this.mItemAnimator.runPendingAnimations();
        }
        RecyclerView.this.mPostedAnimatorRunner = false;
    }
}
