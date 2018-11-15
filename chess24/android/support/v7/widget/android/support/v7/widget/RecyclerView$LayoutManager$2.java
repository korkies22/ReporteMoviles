/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewBoundsCheck;
import android.view.View;
import android.view.ViewGroup;

class RecyclerView.LayoutManager
implements ViewBoundsCheck.Callback {
    RecyclerView.LayoutManager() {
    }

    @Override
    public View getChildAt(int n) {
        return LayoutManager.this.getChildAt(n);
    }

    @Override
    public int getChildCount() {
        return LayoutManager.this.getChildCount();
    }

    @Override
    public int getChildEnd(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return LayoutManager.this.getDecoratedBottom(view) + layoutParams.bottomMargin;
    }

    @Override
    public int getChildStart(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return LayoutManager.this.getDecoratedTop(view) - layoutParams.topMargin;
    }

    @Override
    public View getParent() {
        return LayoutManager.this.mRecyclerView;
    }

    @Override
    public int getParentEnd() {
        return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
    }

    @Override
    public int getParentStart() {
        return LayoutManager.this.getPaddingTop();
    }
}
