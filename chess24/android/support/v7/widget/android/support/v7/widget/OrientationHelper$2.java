/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.graphics.Rect;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

static final class OrientationHelper
extends android.support.v7.widget.OrientationHelper {
    OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        super(layoutManager, null);
    }

    @Override
    public int getDecoratedEnd(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedBottom(view) + layoutParams.bottomMargin;
    }

    @Override
    public int getDecoratedMeasurement(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override
    public int getDecoratedMeasurementInOther(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
    }

    @Override
    public int getDecoratedStart(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedTop(view) - layoutParams.topMargin;
    }

    @Override
    public int getEnd() {
        return this.mLayoutManager.getHeight();
    }

    @Override
    public int getEndAfterPadding() {
        return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
    }

    @Override
    public int getEndPadding() {
        return this.mLayoutManager.getPaddingBottom();
    }

    @Override
    public int getMode() {
        return this.mLayoutManager.getHeightMode();
    }

    @Override
    public int getModeInOther() {
        return this.mLayoutManager.getWidthMode();
    }

    @Override
    public int getStartAfterPadding() {
        return this.mLayoutManager.getPaddingTop();
    }

    @Override
    public int getTotalSpace() {
        return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop() - this.mLayoutManager.getPaddingBottom();
    }

    @Override
    public int getTransformedEndWithDecoration(View view) {
        this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
        return this.mTmpRect.bottom;
    }

    @Override
    public int getTransformedStartWithDecoration(View view) {
        this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
        return this.mTmpRect.top;
    }

    @Override
    public void offsetChild(View view, int n) {
        view.offsetTopAndBottom(n);
    }

    @Override
    public void offsetChildren(int n) {
        this.mLayoutManager.offsetChildrenVertical(n);
    }
}
