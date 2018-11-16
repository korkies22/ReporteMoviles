// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.widget.LinearLayout.LayoutParams;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class BlockLinearLayoutHorizontal extends LinearLayout
{
    private boolean _blockLayout;
    
    public BlockLinearLayoutHorizontal(final Context context) {
        super(context);
        this._blockLayout = false;
    }
    
    public BlockLinearLayoutHorizontal(final Context context, final AttributeSet set) {
        super(context, set);
        this._blockLayout = false;
    }
    
    public boolean isBlockLayout() {
        return this._blockLayout;
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        if (this._blockLayout) {
            this.resetMargins();
        }
    }
    
    public void resetMargins() {
        int n;
        for (int i = n = 0; i < this.getChildCount(); ++i) {
            n += this.getChildAt(i).getMeasuredWidth();
        }
        if (this.getChildCount() > 1) {
            final int measuredWidth = this.getMeasuredWidth();
            final int paddingLeft = this.getPaddingLeft();
            final int paddingRight = this.getPaddingRight();
            int leftMargin;
            if (this._blockLayout) {
                leftMargin = (measuredWidth - n - paddingLeft - paddingRight) / (this.getChildCount() - 1);
            }
            else {
                leftMargin = 0;
            }
            for (int j = 0; j < this.getChildCount(); ++j) {
                ((LinearLayout.LayoutParams)this.getChildAt(j).getLayoutParams()).leftMargin = leftMargin;
            }
            ((LinearLayout.LayoutParams)this.getChildAt(0).getLayoutParams()).leftMargin = 0;
        }
    }
    
    public void setBlockLayout(final boolean blockLayout) {
        this._blockLayout = blockLayout;
        this.resetMargins();
        this.forceLayout();
    }
}
