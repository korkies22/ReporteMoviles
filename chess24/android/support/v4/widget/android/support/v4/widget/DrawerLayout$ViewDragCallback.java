/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v4.widget;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.view.ViewGroup;

private class DrawerLayout.ViewDragCallback
extends ViewDragHelper.Callback {
    private final int mAbsGravity;
    private ViewDragHelper mDragger;
    private final Runnable mPeekRunnable = new Runnable(){

        @Override
        public void run() {
            ViewDragCallback.this.peekDrawer();
        }
    };

    DrawerLayout.ViewDragCallback(int n) {
        this.mAbsGravity = n;
    }

    private void closeOtherDrawer() {
        View view;
        int n = this.mAbsGravity;
        int n2 = 3;
        if (n == 3) {
            n2 = 5;
        }
        if ((view = DrawerLayout.this.findDrawerWithGravity(n2)) != null) {
            DrawerLayout.this.closeDrawer(view);
        }
    }

    @Override
    public int clampViewPositionHorizontal(View view, int n, int n2) {
        if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
            return Math.max(- view.getWidth(), Math.min(n, 0));
        }
        n2 = DrawerLayout.this.getWidth();
        return Math.max(n2 - view.getWidth(), Math.min(n, n2));
    }

    @Override
    public int clampViewPositionVertical(View view, int n, int n2) {
        return view.getTop();
    }

    @Override
    public int getViewHorizontalDragRange(View view) {
        if (DrawerLayout.this.isDrawerView(view)) {
            return view.getWidth();
        }
        return 0;
    }

    @Override
    public void onEdgeDragStarted(int n, int n2) {
        View view = (n & 1) == 1 ? DrawerLayout.this.findDrawerWithGravity(3) : DrawerLayout.this.findDrawerWithGravity(5);
        if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
            this.mDragger.captureChildView(view, n2);
        }
    }

    @Override
    public boolean onEdgeLock(int n) {
        return false;
    }

    @Override
    public void onEdgeTouched(int n, int n2) {
        DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }

    @Override
    public void onViewCaptured(View view, int n) {
        ((DrawerLayout.LayoutParams)view.getLayoutParams()).isPeeking = false;
        this.closeOtherDrawer();
    }

    @Override
    public void onViewDragStateChanged(int n) {
        DrawerLayout.this.updateDrawerState(this.mAbsGravity, n, this.mDragger.getCapturedView());
    }

    @Override
    public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        n2 = view.getWidth();
        float f = DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3) ? (float)(n + n2) / (float)n2 : (float)(DrawerLayout.this.getWidth() - n) / (float)n2;
        DrawerLayout.this.setDrawerViewOffset(view, f);
        n = f == 0.0f ? 4 : 0;
        view.setVisibility(n);
        DrawerLayout.this.invalidate();
    }

    @Override
    public void onViewReleased(View view, float f, float f2) {
        int n;
        block4 : {
            int n2;
            int n3;
            block5 : {
                block3 : {
                    f2 = DrawerLayout.this.getDrawerViewOffset(view);
                    n2 = view.getWidth();
                    if (!DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) break block3;
                    n = f <= 0.0f && (f != 0.0f || f2 <= 0.5f) ? - n2 : 0;
                    break block4;
                }
                n3 = DrawerLayout.this.getWidth();
                if (f < 0.0f) break block5;
                n = n3;
                if (f != 0.0f) break block4;
                n = n3;
                if (f2 <= 0.5f) break block4;
            }
            n = n3 - n2;
        }
        this.mDragger.settleCapturedViewAt(n, view.getTop());
        DrawerLayout.this.invalidate();
    }

    void peekDrawer() {
        View view;
        int n = this.mDragger.getEdgeSize();
        int n2 = this.mAbsGravity;
        int n3 = 0;
        n2 = n2 == 3 ? 1 : 0;
        if (n2 != 0) {
            view = DrawerLayout.this.findDrawerWithGravity(3);
            if (view != null) {
                n3 = - view.getWidth();
            }
            n3 += n;
        } else {
            view = DrawerLayout.this.findDrawerWithGravity(5);
            n3 = DrawerLayout.this.getWidth() - n;
        }
        if (view != null && (n2 != 0 && view.getLeft() < n3 || n2 == 0 && view.getLeft() > n3) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)view.getLayoutParams();
            this.mDragger.smoothSlideViewTo(view, n3, view.getTop());
            layoutParams.isPeeking = true;
            DrawerLayout.this.invalidate();
            this.closeOtherDrawer();
            DrawerLayout.this.cancelChildViewTouch();
        }
    }

    public void removeCallbacks() {
        DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }

    public void setDragger(ViewDragHelper viewDragHelper) {
        this.mDragger = viewDragHelper;
    }

    @Override
    public boolean tryCaptureView(View view, int n) {
        if (DrawerLayout.this.isDrawerView(view) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
            return true;
        }
        return false;
    }

}
