/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.design.R;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderScrollingViewBehavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public static class AppBarLayout.ScrollingViewBehavior
extends HeaderScrollingViewBehavior {
    public AppBarLayout.ScrollingViewBehavior() {
    }

    public AppBarLayout.ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
        this.setOverlayTop(context.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
        context.recycle();
    }

    private static int getAppBarLayoutOffset(AppBarLayout object) {
        if ((object = ((CoordinatorLayout.LayoutParams)object.getLayoutParams()).getBehavior()) instanceof AppBarLayout.Behavior) {
            return ((AppBarLayout.Behavior)object).getTopBottomOffsetForScrollingSibling();
        }
        return 0;
    }

    private void offsetChildAsNeeded(CoordinatorLayout object, View view, View view2) {
        object = ((CoordinatorLayout.LayoutParams)view2.getLayoutParams()).getBehavior();
        if (object instanceof AppBarLayout.Behavior) {
            object = (AppBarLayout.Behavior)object;
            ViewCompat.offsetTopAndBottom(view, view2.getBottom() - view.getTop() + ((AppBarLayout.Behavior)object).mOffsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(view2));
        }
    }

    AppBarLayout findFirstDependency(List<View> list) {
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            View view = list.get(i);
            if (!(view instanceof AppBarLayout)) continue;
            return (AppBarLayout)view;
        }
        return null;
    }

    @Override
    float getOverlapRatioForOffset(View object) {
        if (object instanceof AppBarLayout) {
            object = (AppBarLayout)((Object)object);
            int n = object.getTotalScrollRange();
            int n2 = object.getDownNestedPreScrollRange();
            int n3 = AppBarLayout.ScrollingViewBehavior.getAppBarLayoutOffset((AppBarLayout)((Object)object));
            if (n2 != 0 && n + n3 <= n2) {
                return 0.0f;
            }
            if ((n -= n2) != 0) {
                return 1.0f + (float)n3 / (float)n;
            }
        }
        return 0.0f;
    }

    @Override
    int getScrollRange(View view) {
        if (view instanceof AppBarLayout) {
            return ((AppBarLayout)view).getTotalScrollRange();
        }
        return super.getScrollRange(view);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return view2 instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        this.offsetChildAsNeeded(coordinatorLayout, view, view2);
        return false;
    }

    @Override
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean bl) {
        AppBarLayout appBarLayout = this.findFirstDependency(coordinatorLayout.getDependencies(view));
        if (appBarLayout != null) {
            rect.offset(view.getLeft(), view.getTop());
            view = this.mTempRect1;
            view.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
            if (!view.contains(rect)) {
                appBarLayout.setExpanded(false, bl ^ true);
                return true;
            }
        }
        return false;
    }
}
