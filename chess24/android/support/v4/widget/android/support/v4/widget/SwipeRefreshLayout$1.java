/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.v4.widget;

import android.support.v4.widget.CircleImageView;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.animation.Animation;

class SwipeRefreshLayout
implements Animation.AnimationListener {
    SwipeRefreshLayout() {
    }

    public void onAnimationEnd(Animation animation) {
        if (SwipeRefreshLayout.this.mRefreshing) {
            SwipeRefreshLayout.this.mProgress.setAlpha(255);
            SwipeRefreshLayout.this.mProgress.start();
            if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                SwipeRefreshLayout.this.mListener.onRefresh();
            }
            SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop();
            return;
        }
        SwipeRefreshLayout.this.reset();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
