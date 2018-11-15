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
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public static class FloatingActionButton.Behavior
extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private static final boolean AUTO_HIDE_DEFAULT = true;
    private boolean mAutoHideEnabled;
    private FloatingActionButton.OnVisibilityChangedListener mInternalAutoHideListener;
    private Rect mTmpRect;

    public FloatingActionButton.Behavior() {
        this.mAutoHideEnabled = true;
    }

    public FloatingActionButton.Behavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton_Behavior_Layout);
        this.mAutoHideEnabled = context.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
        context.recycle();
    }

    private static boolean isBottomSheet(@NonNull View view) {
        if ((view = view.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams) {
            return ((CoordinatorLayout.LayoutParams)view).getBehavior() instanceof BottomSheetBehavior;
        }
        return false;
    }

    private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
        Rect rect = floatingActionButton.mShadowPadding;
        if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            int n = floatingActionButton.getRight();
            int n2 = coordinatorLayout.getWidth();
            int n3 = layoutParams.rightMargin;
            int n4 = 0;
            n = n >= n2 - n3 ? rect.right : (floatingActionButton.getLeft() <= layoutParams.leftMargin ? - rect.left : 0);
            if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - layoutParams.bottomMargin) {
                n4 = rect.bottom;
            } else if (floatingActionButton.getTop() <= layoutParams.topMargin) {
                n4 = - rect.top;
            }
            if (n4 != 0) {
                ViewCompat.offsetTopAndBottom((View)floatingActionButton, n4);
            }
            if (n != 0) {
                ViewCompat.offsetLeftAndRight((View)floatingActionButton, n);
            }
        }
    }

    private boolean shouldUpdateVisibility(View view, FloatingActionButton floatingActionButton) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
        if (!this.mAutoHideEnabled) {
            return false;
        }
        if (layoutParams.getAnchorId() != view.getId()) {
            return false;
        }
        if (floatingActionButton.getUserSetVisibility() != 0) {
            return false;
        }
        return true;
    }

    private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
        if (!this.shouldUpdateVisibility((View)appBarLayout, floatingActionButton)) {
            return false;
        }
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        Rect rect = this.mTmpRect;
        ViewGroupUtils.getDescendantRect(coordinatorLayout, (View)appBarLayout, rect);
        if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
            floatingActionButton.hide(this.mInternalAutoHideListener, false);
        } else {
            floatingActionButton.show(this.mInternalAutoHideListener, false);
        }
        return true;
    }

    private boolean updateFabVisibilityForBottomSheet(View view, FloatingActionButton floatingActionButton) {
        if (!this.shouldUpdateVisibility(view, floatingActionButton)) {
            return false;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
        if (view.getTop() < floatingActionButton.getHeight() / 2 + layoutParams.topMargin) {
            floatingActionButton.hide(this.mInternalAutoHideListener, false);
        } else {
            floatingActionButton.show(this.mInternalAutoHideListener, false);
        }
        return true;
    }

    @Override
    public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton floatingActionButton, @NonNull Rect rect) {
        coordinatorLayout = floatingActionButton.mShadowPadding;
        rect.set(floatingActionButton.getLeft() + coordinatorLayout.left, floatingActionButton.getTop() + coordinatorLayout.top, floatingActionButton.getRight() - coordinatorLayout.right, floatingActionButton.getBottom() - coordinatorLayout.bottom);
        return true;
    }

    public boolean isAutoHideEnabled() {
        return this.mAutoHideEnabled;
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
        if (view instanceof AppBarLayout) {
            this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
        } else if (FloatingActionButton.Behavior.isBottomSheet(view)) {
            this.updateFabVisibilityForBottomSheet(view, floatingActionButton);
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int n) {
        View view;
        List<View> list = coordinatorLayout.getDependencies((View)floatingActionButton);
        int n2 = list.size();
        for (int i = 0; i < n2 && !((view = list.get(i)) instanceof AppBarLayout ? this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton) : FloatingActionButton.Behavior.isBottomSheet(view) && this.updateFabVisibilityForBottomSheet(view, floatingActionButton)); ++i) {
        }
        coordinatorLayout.onLayoutChild((View)floatingActionButton, n);
        this.offsetIfNeeded(coordinatorLayout, floatingActionButton);
        return true;
    }

    public void setAutoHideEnabled(boolean bl) {
        this.mAutoHideEnabled = bl;
    }

    @VisibleForTesting
    void setInternalAutoHideListener(FloatingActionButton.OnVisibilityChangedListener onVisibilityChangedListener) {
        this.mInternalAutoHideListener = onVisibilityChangedListener;
    }
}
