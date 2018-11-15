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
        return this.mLayoutManager.getDecoratedRight(view) + layoutParams.rightMargin;
    }

    @Override
    public int getDecoratedMeasurement(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
    }

    @Override
    public int getDecoratedMeasurementInOther(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override
    public int getDecoratedStart(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.mLayoutManager.getDecoratedLeft(view) - layoutParams.leftMargin;
    }

    @Override
    public int getEnd() {
        return this.mLayoutManager.getWidth();
    }

    @Override
    public int getEndAfterPadding() {
        return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
    }

    @Override
    public int getEndPadding() {
        return this.mLayoutManager.getPaddingRight();
    }

    @Override
    public int getMode() {
        return this.mLayoutManager.getWidthMode();
    }

    @Override
    public int getModeInOther() {
        return this.mLayoutManager.getHeightMode();
    }

    @Override
    public int getStartAfterPadding() {
        return this.mLayoutManager.getPaddingLeft();
    }

    @Override
    public int getTotalSpace() {
        return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft() - this.mLayoutManager.getPaddingRight();
    }

    @Override
    public int getTransformedEndWithDecoration(View view) {
        this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
        return this.mTmpRect.right;
    }

    @Override
    public int getTransformedStartWithDecoration(View view) {
        this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
        return this.mTmpRect.left;
    }

    @Override
    public void offsetChild(View view, int n) {
        view.offsetLeftAndRight(n);
    }

    @Override
    public void offsetChildren(int n) {
        this.mLayoutManager.offsetChildrenHorizontal(n);
    }
}
