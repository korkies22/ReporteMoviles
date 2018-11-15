/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.view.View
 */
package android.support.v7.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

public class LinearSnapHelper
extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n;
        int n2 = layoutManager.getChildCount();
        if (n2 == 0) {
            return 1.0f;
        }
        View view = null;
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        View view2 = null;
        for (int i = 0; i < n2; ++i) {
            View view3;
            int n5;
            View view4 = layoutManager.getChildAt(i);
            int n6 = layoutManager.getPosition(view4);
            if (n6 == -1) {
                view3 = view;
                n5 = n4;
            } else {
                n = n3;
                if (n6 < n3) {
                    view = view4;
                    n = n6;
                }
                view3 = view;
                n3 = n;
                n5 = n4;
                if (n6 > n4) {
                    view2 = view4;
                    n5 = n6;
                    n3 = n;
                    view3 = view;
                }
            }
            view = view3;
            n4 = n5;
        }
        if (view != null) {
            if (view2 == null) {
                return 1.0f;
            }
            n = Math.min(orientationHelper.getDecoratedStart(view), orientationHelper.getDecoratedStart(view2));
            n = Math.max(orientationHelper.getDecoratedEnd(view), orientationHelper.getDecoratedEnd(view2)) - n;
            if (n == 0) {
                return 1.0f;
            }
            return 1.0f * (float)n / (float)(n4 - n3 + 1);
        }
        return 1.0f;
    }

    private int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view, OrientationHelper orientationHelper) {
        int n = orientationHelper.getDecoratedStart(view);
        int n2 = orientationHelper.getDecoratedMeasurement(view) / 2;
        int n3 = layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2 : orientationHelper.getEnd() / 2;
        return n + n2 - n3;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper, int n, int n2) {
        int[] arrn = this.calculateScrollDistance(n, n2);
        float f = this.computeDistancePerChild(layoutManager, orientationHelper);
        if (f <= 0.0f) {
            return 0;
        }
        n = Math.abs(arrn[0]) > Math.abs(arrn[1]) ? arrn[0] : arrn[1];
        return Math.round((float)n / f);
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
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return this.findCenterView(layoutManager, this.getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return this.findCenterView(layoutManager, this.getHorizontalHelper(layoutManager));
        }
        return null;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        int n3;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return -1;
        }
        int n4 = layoutManager.getItemCount();
        if (n4 == 0) {
            return -1;
        }
        Object object = this.findSnapView(layoutManager);
        if (object == null) {
            return -1;
        }
        int n5 = layoutManager.getPosition((View)object);
        if (n5 == -1) {
            return -1;
        }
        object = (RecyclerView.SmoothScroller.ScrollVectorProvider)((Object)layoutManager);
        int n6 = n4 - 1;
        if ((object = object.computeScrollVectorForPosition(n6)) == null) {
            return -1;
        }
        if (layoutManager.canScrollHorizontally()) {
            n = n3 = this.estimateNextPositionDiffForFling(layoutManager, this.getHorizontalHelper(layoutManager), n, 0);
            if (object.x < 0.0f) {
                n = - n3;
            }
        } else {
            n = 0;
        }
        if (layoutManager.canScrollVertically()) {
            n2 = n3 = this.estimateNextPositionDiffForFling(layoutManager, this.getVerticalHelper(layoutManager), 0, n2);
            if (object.y < 0.0f) {
                n2 = - n3;
            }
        } else {
            n2 = 0;
        }
        if (layoutManager.canScrollVertically()) {
            n = n2;
        }
        if (n == 0) {
            return -1;
        }
        n = n2 = n5 + n;
        if (n2 < 0) {
            n = 0;
        }
        n2 = n;
        if (n >= n4) {
            n2 = n6;
        }
        return n2;
    }
}
