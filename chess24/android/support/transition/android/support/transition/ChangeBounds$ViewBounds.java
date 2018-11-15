/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.view.View
 */
package android.support.transition;

import android.graphics.PointF;
import android.support.transition.ChangeBounds;
import android.support.transition.ViewUtils;
import android.view.View;

private static class ChangeBounds.ViewBounds {
    private int mBottom;
    private int mBottomRightCalls;
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mTopLeftCalls;
    private View mView;

    ChangeBounds.ViewBounds(View view) {
        this.mView = view;
    }

    private void setLeftTopRightBottom() {
        ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
        this.mTopLeftCalls = 0;
        this.mBottomRightCalls = 0;
    }

    void setBottomRight(PointF pointF) {
        this.mRight = Math.round(pointF.x);
        this.mBottom = Math.round(pointF.y);
        ++this.mBottomRightCalls;
        if (this.mTopLeftCalls == this.mBottomRightCalls) {
            this.setLeftTopRightBottom();
        }
    }

    void setTopLeft(PointF pointF) {
        this.mLeft = Math.round(pointF.x);
        this.mTop = Math.round(pointF.y);
        ++this.mTopLeftCalls;
        if (this.mTopLeftCalls == this.mBottomRightCalls) {
            this.setLeftTopRightBottom();
        }
    }
}
