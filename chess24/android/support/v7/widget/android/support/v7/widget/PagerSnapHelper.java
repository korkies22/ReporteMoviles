/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class PagerSnapHelper
extends SnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    private int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view, OrientationHelper orientationHelper) {
        int n = orientationHelper.getDecoratedStart(view);
        int n2 = orientationHelper.getDecoratedMeasurement(view) / 2;
        int n3 = layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2 : orientationHelper.getEnd() / 2;
        return n + n2 - n3;
    }

    @Nullable
    private View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n = layoutManager.getChildCount();
        View view = null;
        if (n == 0) {
            return null;
        }
        int n2 = layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2 : orientationHelper.getEnd() / 2;
        int n3 = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            View view2 = layoutManager.getChildAt(i);
            int n4 = Math.abs(orientationHelper.getDecoratedStart(view2) + orientationHelper.getDecoratedMeasurement(view2) / 2 - n2);
            int n5 = n3;
            if (n4 < n3) {
                view = view2;
                n5 = n4;
            }
            n3 = n5;
        }
        return view;
    }

    @Nullable
    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n = layoutManager.getChildCount();
        View view = null;
        if (n == 0) {
            return null;
        }
        int n2 = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            View view2 = layoutManager.getChildAt(i);
            int n3 = orientationHelper.getDecoratedStart(view2);
            int n4 = n2;
            if (n3 < n2) {
                view = view2;
                n4 = n3;
            }
            n2 = n4;
        }
        return view;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        int[] arrn = new int[2];
        arrn[0] = layoutManager.canScrollHorizontally() ? this.distanceToCenter(layoutManager, view, this.getHorizontalHelper(layoutManager)) : 0;
        if (layoutManager.canScrollVertically()) {
            arrn[1] = this.distanceToCenter(layoutManager, view, this.getVerticalHelper(layoutManager));
            return arrn;
        }
        arrn[1] = 0;
        return arrn;
    }

    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(this.mRecyclerView.getContext()){

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
        };
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return this.findCenterView(layoutManager, this.getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return this.findCenterView(layoutManager, this.getHorizontalHelper(layoutManager));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        int n3;
        block10 : {
            block11 : {
                int n4 = layoutManager.getItemCount();
                if (n4 == 0) {
                    return -1;
                }
                View view = null;
                if (layoutManager.canScrollVertically()) {
                    view = this.findStartView(layoutManager, this.getVerticalHelper(layoutManager));
                } else if (layoutManager.canScrollHorizontally()) {
                    view = this.findStartView(layoutManager, this.getHorizontalHelper(layoutManager));
                }
                if (view == null) {
                    return -1;
                }
                n3 = layoutManager.getPosition(view);
                if (n3 == -1) {
                    return -1;
                }
                boolean bl = layoutManager.canScrollHorizontally();
                int n5 = 0;
                n = (bl ? n > 0 : n2 > 0) ? 1 : 0;
                n2 = n5;
                if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) break block10;
                layoutManager = ((RecyclerView.SmoothScroller.ScrollVectorProvider)((Object)layoutManager)).computeScrollVectorForPosition(n4 - 1);
                n2 = n5;
                if (layoutManager == null) break block10;
                if (layoutManager.x < 0.0f) break block11;
                n2 = n5;
                if (layoutManager.y >= 0.0f) break block10;
            }
            n2 = 1;
        }
        if (n2 != 0) {
            n2 = n3;
            if (n == 0) return n2;
            return n3 - 1;
        }
        n2 = n3;
        if (n == 0) return n2;
        return n3 + 1;
    }

}
