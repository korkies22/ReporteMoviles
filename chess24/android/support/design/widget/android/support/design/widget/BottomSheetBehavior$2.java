/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import java.lang.ref.WeakReference;

class BottomSheetBehavior
extends ViewDragHelper.Callback {
    BottomSheetBehavior() {
    }

    @Override
    public int clampViewPositionHorizontal(View view, int n, int n2) {
        return view.getLeft();
    }

    @Override
    public int clampViewPositionVertical(View view, int n, int n2) {
        int n3 = BottomSheetBehavior.this.mMinOffset;
        n2 = BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset;
        return MathUtils.clamp(n, n3, n2);
    }

    @Override
    public int getViewVerticalDragRange(View view) {
        if (BottomSheetBehavior.this.mHideable) {
            return BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset;
        }
        return BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
    }

    @Override
    public void onViewDragStateChanged(int n) {
        if (n == 1) {
            BottomSheetBehavior.this.setStateInternal(1);
        }
    }

    @Override
    public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        BottomSheetBehavior.this.dispatchOnSlide(n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void onViewReleased(View var1_1, float var2_2, float var3_3) {
        block7 : {
            var5_4 = 4;
            if (var3_3 >= 0.0f) break block7;
            var4_5 = BottomSheetBehavior.this.mMinOffset;
            ** GOTO lbl14
        }
        if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(var1_1, var3_3)) {
            var4_5 = BottomSheetBehavior.this.mParentHeight;
            var5_4 = 5;
        } else if (var3_3 == 0.0f) {
            var4_5 = var1_1.getTop();
            if (Math.abs(var4_5 - BottomSheetBehavior.this.mMinOffset) < Math.abs(var4_5 - BottomSheetBehavior.this.mMaxOffset)) {
                var4_5 = BottomSheetBehavior.this.mMinOffset;
lbl14: // 2 sources:
                var5_4 = 3;
            } else {
                var4_5 = BottomSheetBehavior.this.mMaxOffset;
            }
        } else {
            var4_5 = BottomSheetBehavior.this.mMaxOffset;
        }
        if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(var1_1.getLeft(), var4_5)) {
            BottomSheetBehavior.this.setStateInternal(2);
            ViewCompat.postOnAnimation(var1_1, new BottomSheetBehavior.SettleRunnable(BottomSheetBehavior.this, var1_1, var5_4));
            return;
        }
        BottomSheetBehavior.this.setStateInternal(var5_4);
    }

    @Override
    public boolean tryCaptureView(View view, int n) {
        View view2;
        if (BottomSheetBehavior.this.mState == 1) {
            return false;
        }
        if (BottomSheetBehavior.this.mTouchingScrollingChild) {
            return false;
        }
        if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == n && (view2 = (View)BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null && view2.canScrollVertically(-1)) {
            return false;
        }
        if (BottomSheetBehavior.this.mViewRef != null && BottomSheetBehavior.this.mViewRef.get() == view) {
            return true;
        }
        return false;
    }
}
