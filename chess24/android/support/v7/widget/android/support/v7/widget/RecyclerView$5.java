/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

class RecyclerView
implements ChildHelper.Callback {
    RecyclerView() {
    }

    @Override
    public void addView(View view, int n) {
        RecyclerView.this.addView(view, n);
        RecyclerView.this.dispatchChildAttached(view);
    }

    @Override
    public void attachViewToParent(View object, int n, ViewGroup.LayoutParams layoutParams) {
        RecyclerView.ViewHolder viewHolder = android.support.v7.widget.RecyclerView.getChildViewHolderInt((View)object);
        if (viewHolder != null) {
            if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                object = new StringBuilder();
                object.append("Called attach on a child which is not detached: ");
                object.append(viewHolder);
                object.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(object.toString());
            }
            viewHolder.clearTmpDetachFlag();
        }
        RecyclerView.this.attachViewToParent((View)object, n, layoutParams);
    }

    @Override
    public void detachViewFromParent(int n) {
        Object object = this.getChildAt(n);
        if (object != null && (object = android.support.v7.widget.RecyclerView.getChildViewHolderInt((View)object)) != null) {
            if (object.isTmpDetached() && !object.shouldIgnore()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("called detach on an already detached child ");
                stringBuilder.append(object);
                stringBuilder.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            object.addFlags(256);
        }
        RecyclerView.this.detachViewFromParent(n);
    }

    @Override
    public View getChildAt(int n) {
        return RecyclerView.this.getChildAt(n);
    }

    @Override
    public int getChildCount() {
        return RecyclerView.this.getChildCount();
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View view) {
        return android.support.v7.widget.RecyclerView.getChildViewHolderInt(view);
    }

    @Override
    public int indexOfChild(View view) {
        return RecyclerView.this.indexOfChild(view);
    }

    @Override
    public void onEnteredHiddenState(View object) {
        if ((object = android.support.v7.widget.RecyclerView.getChildViewHolderInt(object)) != null) {
            ((RecyclerView.ViewHolder)object).onEnteredHiddenState(RecyclerView.this);
        }
    }

    @Override
    public void onLeftHiddenState(View object) {
        if ((object = android.support.v7.widget.RecyclerView.getChildViewHolderInt(object)) != null) {
            ((RecyclerView.ViewHolder)object).onLeftHiddenState(RecyclerView.this);
        }
    }

    @Override
    public void removeAllViews() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            RecyclerView.this.dispatchChildDetached(view);
            view.clearAnimation();
        }
        RecyclerView.this.removeAllViews();
    }

    @Override
    public void removeViewAt(int n) {
        View view = RecyclerView.this.getChildAt(n);
        if (view != null) {
            RecyclerView.this.dispatchChildDetached(view);
            view.clearAnimation();
        }
        RecyclerView.this.removeViewAt(n);
    }
}
