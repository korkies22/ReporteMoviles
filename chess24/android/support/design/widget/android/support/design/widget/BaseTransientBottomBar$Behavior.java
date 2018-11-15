/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SnackbarManager;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.MotionEvent;
import android.view.View;

final class BaseTransientBottomBar.Behavior
extends SwipeDismissBehavior<BaseTransientBottomBar.SnackbarBaseLayout> {
    BaseTransientBottomBar.Behavior() {
    }

    @Override
    public boolean canSwipeDismissView(View view) {
        return view instanceof BaseTransientBottomBar.SnackbarBaseLayout;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, BaseTransientBottomBar.SnackbarBaseLayout snackbarBaseLayout, MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n != 3) {
            switch (n) {
                default: {
                    return super.onInterceptTouchEvent(coordinatorLayout, snackbarBaseLayout, motionEvent);
                }
                case 0: {
                    if (!coordinatorLayout.isPointInChildBounds((View)snackbarBaseLayout, (int)motionEvent.getX(), (int)motionEvent.getY())) return super.onInterceptTouchEvent(coordinatorLayout, snackbarBaseLayout, motionEvent);
                    SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                    return super.onInterceptTouchEvent(coordinatorLayout, snackbarBaseLayout, motionEvent);
                }
                case 1: 
            }
        }
        SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
        return super.onInterceptTouchEvent(coordinatorLayout, snackbarBaseLayout, motionEvent);
    }
}
