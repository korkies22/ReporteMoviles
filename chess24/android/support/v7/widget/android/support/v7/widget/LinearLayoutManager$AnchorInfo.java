/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

static class LinearLayoutManager.AnchorInfo {
    int mCoordinate;
    boolean mLayoutFromEnd;
    OrientationHelper mOrientationHelper;
    int mPosition;
    boolean mValid;

    LinearLayoutManager.AnchorInfo() {
        this.reset();
    }

    void assignCoordinateFromPadding() {
        int n = this.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
        this.mCoordinate = n;
    }

    public void assignFromView(View view, int n) {
        this.mCoordinate = this.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd(view) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart(view);
        this.mPosition = n;
    }

    public void assignFromViewAndKeepVisibleRect(View view, int n) {
        int n2 = this.mOrientationHelper.getTotalSpaceChange();
        if (n2 >= 0) {
            this.assignFromView(view, n);
            return;
        }
        this.mPosition = n;
        if (this.mLayoutFromEnd) {
            n = this.mOrientationHelper.getEndAfterPadding() - n2 - this.mOrientationHelper.getDecoratedEnd(view);
            this.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - n;
            if (n > 0) {
                n2 = this.mOrientationHelper.getDecoratedMeasurement(view);
                int n3 = this.mCoordinate;
                int n4 = this.mOrientationHelper.getStartAfterPadding();
                if ((n2 = n3 - n2 - (n4 + Math.min(this.mOrientationHelper.getDecoratedStart(view) - n4, 0))) < 0) {
                    this.mCoordinate += Math.min(n, - n2);
                    return;
                }
            }
        } else {
            int n5 = this.mOrientationHelper.getDecoratedStart(view);
            n = n5 - this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = n5;
            if (n > 0) {
                int n6 = this.mOrientationHelper.getDecoratedMeasurement(view);
                int n7 = this.mOrientationHelper.getEndAfterPadding();
                int n8 = this.mOrientationHelper.getDecoratedEnd(view);
                n2 = this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n7 - n2 - n8) - (n5 + n6);
                if (n2 < 0) {
                    this.mCoordinate -= Math.min(n, - n2);
                }
            }
        }
    }

    boolean isViewValidAsAnchor(View object, RecyclerView.State state) {
        if (!(object = (RecyclerView.LayoutParams)object.getLayoutParams()).isItemRemoved() && object.getViewLayoutPosition() >= 0 && object.getViewLayoutPosition() < state.getItemCount()) {
            return true;
        }
        return false;
    }

    void reset() {
        this.mPosition = -1;
        this.mCoordinate = Integer.MIN_VALUE;
        this.mLayoutFromEnd = false;
        this.mValid = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AnchorInfo{mPosition=");
        stringBuilder.append(this.mPosition);
        stringBuilder.append(", mCoordinate=");
        stringBuilder.append(this.mCoordinate);
        stringBuilder.append(", mLayoutFromEnd=");
        stringBuilder.append(this.mLayoutFromEnd);
        stringBuilder.append(", mValid=");
        stringBuilder.append(this.mValid);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
