/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package android.support.v4.widget;

import android.support.v4.widget.CircleImageView;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout
extends Animation {
    SwipeRefreshLayout() {
    }

    public void applyTransformation(float f, Transformation transformation) {
        int n = !SwipeRefreshLayout.this.mUsingCustomStart ? SwipeRefreshLayout.this.mSpinnerOffsetEnd - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop) : SwipeRefreshLayout.this.mSpinnerOffsetEnd;
        int n2 = SwipeRefreshLayout.this.mFrom;
        n = (int)((float)(n - SwipeRefreshLayout.this.mFrom) * f);
        int n3 = SwipeRefreshLayout.this.mCircleView.getTop();
        SwipeRefreshLayout.this.setTargetOffsetTopAndBottom(n2 + n - n3);
        SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f);
    }
}
