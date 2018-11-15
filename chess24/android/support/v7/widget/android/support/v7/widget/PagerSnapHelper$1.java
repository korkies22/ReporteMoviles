/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

class PagerSnapHelper
extends LinearSmoothScroller {
    PagerSnapHelper(Context context) {
        super(context);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 100.0f / (float)displayMetrics.densityDpi;
    }

    @Override
    protected int calculateTimeForScrolling(int n) {
        return Math.min(100, super.calculateTimeForScrolling(n));
    }

    @Override
    protected void onTargetFound(View arrn, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        arrn = PagerSnapHelper.this.calculateDistanceToFinalSnap(PagerSnapHelper.this.mRecyclerView.getLayoutManager(), (View)arrn);
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = this.calculateTimeForDeceleration(Math.max(Math.abs(n), Math.abs(n2)));
        if (n3 > 0) {
            action.update(n, n2, n3, (Interpolator)this.mDecelerateInterpolator);
        }
    }
}
