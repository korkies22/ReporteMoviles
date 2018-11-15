/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 */
package uk.co.jasonfry.android.tools.widget;

import android.os.Handler;
import android.os.Message;
import uk.co.jasonfry.android.tools.util.AnimationUtil;

class BounceSwipeView
extends Handler {
    BounceSwipeView() {
    }

    public void handleMessage(Message message) {
        int n = AnimationUtil.quadraticOutEase(BounceSwipeView.this.mCurrentAnimationFrame, BounceSwipeView.this.mPaddingStartValue, - BounceSwipeView.this.mPaddingChange, 4.0f);
        if (BounceSwipeView.this.mBouncingSide) {
            uk.co.jasonfry.android.tools.widget.BounceSwipeView.super.setPadding(n, BounceSwipeView.this.getPaddingTop(), BounceSwipeView.this.getPaddingRight(), BounceSwipeView.this.getPaddingBottom());
        } else if (!BounceSwipeView.this.mBouncingSide) {
            uk.co.jasonfry.android.tools.widget.BounceSwipeView.super.setPadding(BounceSwipeView.this.getPaddingLeft(), BounceSwipeView.this.getPaddingTop(), n, BounceSwipeView.this.getPaddingBottom());
        }
        uk.co.jasonfry.android.tools.widget.BounceSwipeView.access$108(BounceSwipeView.this);
        if (BounceSwipeView.this.mCurrentAnimationFrame <= 4) {
            BounceSwipeView.this.mEaseAnimationFrameHandler.sendEmptyMessageDelayed(0, 30L);
        }
    }
}
