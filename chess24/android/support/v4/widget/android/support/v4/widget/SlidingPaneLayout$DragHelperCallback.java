/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v4.widget;

import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.view.ViewGroup;

private class SlidingPaneLayout.DragHelperCallback
extends ViewDragHelper.Callback {
    SlidingPaneLayout.DragHelperCallback() {
    }

    @Override
    public int clampViewPositionHorizontal(View object, int n, int n2) {
        object = (SlidingPaneLayout.LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
        if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
            n2 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + object.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
            int n3 = SlidingPaneLayout.this.mSlideRange;
            return Math.max(Math.min(n, n2), n2 - n3);
        }
        n2 = SlidingPaneLayout.this.getPaddingLeft() + object.leftMargin;
        int n4 = SlidingPaneLayout.this.mSlideRange;
        return Math.min(Math.max(n, n2), n4 + n2);
    }

    @Override
    public int clampViewPositionVertical(View view, int n, int n2) {
        return view.getTop();
    }

    @Override
    public int getViewHorizontalDragRange(View view) {
        return SlidingPaneLayout.this.mSlideRange;
    }

    @Override
    public void onEdgeDragStarted(int n, int n2) {
        SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, n2);
    }

    @Override
    public void onViewCaptured(View view, int n) {
        SlidingPaneLayout.this.setAllChildrenVisible();
    }

    @Override
    public void onViewDragStateChanged(int n) {
        if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0) {
            if (SlidingPaneLayout.this.mSlideOffset == 0.0f) {
                SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
                SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
                SlidingPaneLayout.this.mPreservedOpenState = false;
                return;
            }
            SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
            SlidingPaneLayout.this.mPreservedOpenState = true;
        }
    }

    @Override
    public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        SlidingPaneLayout.this.onPanelDragged(n);
        SlidingPaneLayout.this.invalidate();
    }

    @Override
    public void onViewReleased(View view, float f, float f2) {
        int n;
        block7 : {
            int n2;
            block8 : {
                SlidingPaneLayout.LayoutParams layoutParams;
                block4 : {
                    int n3;
                    block6 : {
                        block5 : {
                            layoutParams = (SlidingPaneLayout.LayoutParams)view.getLayoutParams();
                            if (!SlidingPaneLayout.this.isLayoutRtlSupport()) break block4;
                            n3 = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                            if (f < 0.0f) break block5;
                            n = n3;
                            if (f != 0.0f) break block6;
                            n = n3;
                            if (SlidingPaneLayout.this.mSlideOffset <= 0.5f) break block6;
                        }
                        n = n3 + SlidingPaneLayout.this.mSlideRange;
                    }
                    n3 = SlidingPaneLayout.this.mSlideableView.getWidth();
                    n = SlidingPaneLayout.this.getWidth() - n - n3;
                    break block7;
                }
                n = SlidingPaneLayout.this.getPaddingLeft();
                n2 = layoutParams.leftMargin + n;
                if (f > 0.0f) break block8;
                n = n2;
                if (f != 0.0f) break block7;
                n = n2;
                if (SlidingPaneLayout.this.mSlideOffset <= 0.5f) break block7;
            }
            n = n2 + SlidingPaneLayout.this.mSlideRange;
        }
        SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(n, view.getTop());
        SlidingPaneLayout.this.invalidate();
    }

    @Override
    public boolean tryCaptureView(View view, int n) {
        if (SlidingPaneLayout.this.mIsUnableToDrag) {
            return false;
        }
        return ((SlidingPaneLayout.LayoutParams)view.getLayoutParams()).slideable;
    }
}
