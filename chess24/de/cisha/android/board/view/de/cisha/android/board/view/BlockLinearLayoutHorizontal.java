/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BlockLinearLayoutHorizontal
extends LinearLayout {
    private boolean _blockLayout = false;

    public BlockLinearLayoutHorizontal(Context context) {
        super(context);
    }

    public BlockLinearLayoutHorizontal(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean isBlockLayout() {
        return this._blockLayout;
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this._blockLayout) {
            this.resetMargins();
        }
    }

    public void resetMargins() {
        int n;
        int n2 = n = 0;
        while (n < this.getChildCount()) {
            n2 += this.getChildAt(n).getMeasuredWidth();
            ++n;
        }
        if (this.getChildCount() > 1) {
            n = this.getMeasuredWidth();
            int n3 = this.getPaddingLeft();
            int n4 = this.getPaddingRight();
            n2 = this._blockLayout ? (n - n2 - n3 - n4) / (this.getChildCount() - 1) : 0;
            for (n = 0; n < this.getChildCount(); ++n) {
                ((LinearLayout.LayoutParams)this.getChildAt((int)n).getLayoutParams()).leftMargin = n2;
            }
            ((LinearLayout.LayoutParams)this.getChildAt((int)0).getLayoutParams()).leftMargin = 0;
        }
    }

    public void setBlockLayout(boolean bl) {
        this._blockLayout = bl;
        this.resetMargins();
        this.forceLayout();
    }
}
