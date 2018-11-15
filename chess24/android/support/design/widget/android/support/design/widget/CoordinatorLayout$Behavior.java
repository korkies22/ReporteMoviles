/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.AbsSavedState
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public static abstract class CoordinatorLayout.Behavior<V extends View> {
    public CoordinatorLayout.Behavior() {
    }

    public CoordinatorLayout.Behavior(Context context, AttributeSet attributeSet) {
    }

    public static Object getTag(View view) {
        return ((CoordinatorLayout.LayoutParams)view.getLayoutParams()).mBehaviorTag;
    }

    public static void setTag(View view, Object object) {
        ((CoordinatorLayout.LayoutParams)view.getLayoutParams()).mBehaviorTag = object;
    }

    public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
        if (this.getScrimOpacity(coordinatorLayout, v) > 0.0f) {
            return true;
        }
        return false;
    }

    public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect) {
        return false;
    }

    @ColorInt
    public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
        return -16777216;
    }

    @FloatRange(from=0.0, to=1.0)
    public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
        return 0.0f;
    }

    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
        return false;
    }

    @NonNull
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
        return windowInsetsCompat;
    }

    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
    }

    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
        return false;
    }

    public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
    }

    public void onDetachedFromLayoutParams() {
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        return false;
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        return false;
    }

    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3, int n4) {
        return false;
    }

    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2, boolean bl) {
        return false;
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
        return false;
    }

    @Deprecated
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, @NonNull int[] arrn) {
    }

    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, @NonNull int[] arrn, int n3) {
        if (n3 == 0) {
            this.onNestedPreScroll(coordinatorLayout, v, view, n, n2, arrn);
        }
    }

    @Deprecated
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, int n3, int n4) {
    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            this.onNestedScroll(coordinatorLayout, v, view, n, n2, n3, n4);
        }
    }

    @Deprecated
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n) {
    }

    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n, int n2) {
        if (n2 == 0) {
            this.onNestedScrollAccepted(coordinatorLayout, v, view, view2, n);
        }
    }

    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean bl) {
        return false;
    }

    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return View.BaseSavedState.EMPTY_STATE;
    }

    @Deprecated
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n) {
        return false;
    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int n, int n2) {
        if (n2 == 0) {
            return this.onStartNestedScroll(coordinatorLayout, v, view, view2, n);
        }
        return false;
    }

    @Deprecated
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
    }

    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int n) {
        if (n == 0) {
            this.onStopNestedScroll(coordinatorLayout, v, view);
        }
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        return false;
    }
}
