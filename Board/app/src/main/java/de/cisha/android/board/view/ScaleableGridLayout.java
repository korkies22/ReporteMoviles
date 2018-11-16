// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View;
import android.graphics.Rect;
import android.view.ViewGroup.LayoutParams;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup;

public class ScaleableGridLayout extends ViewGroup
{
    protected Context _context;
    protected int _height;
    protected int _totalCols;
    protected int _totalRows;
    protected int _width;
    
    public ScaleableGridLayout(final Context context, final int n, final int n2) {
        super(context);
        this._width = 400;
        this._height = 400;
        this._totalRows = 8;
        this._totalCols = 8;
        this.init(context, n, n2);
    }
    
    public ScaleableGridLayout(final Context context, final AttributeSet set) {
        super(context, set);
        this._width = 400;
        this._height = 400;
        this._totalRows = 8;
        this._totalCols = 8;
    }
    
    protected ScaleableGridLayout(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set);
        this._width = 400;
        this._height = 400;
        this._totalRows = 8;
        this._totalCols = 8;
        this.init(context, n, n2);
    }
    
    private void init(final Context context, final int totalRows, final int totalCols) {
        this._context = context;
        this._totalRows = totalRows;
        this._totalCols = totalCols;
    }
    
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        return viewGroup.LayoutParams instanceof LayoutParams;
    }
    
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1, -1, -1);
    }
    
    public LayoutParams generateLayoutParams(final AttributeSet set) {
        return new LayoutParams(-1, -1, -1, -1);
    }
    
    protected LayoutParams generateLayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        return new LayoutParams(-1, -1, -1, -1);
    }
    
    protected Rect getBounds(final int n, final int n2) {
        return this.getBounds(n, n2, 1, 1);
    }
    
    protected Rect getBounds(final int n, final int n2, final int n3, final int n4) {
        final float n5 = this._width / this._totalCols;
        final float n6 = this._height / this._totalRows;
        return new Rect(Math.round(n * n5), Math.round(n2 * n6), Math.round(n5 * (n + n3)), Math.round(n6 * (n2 + n4)));
    }
    
    protected void onLayout(final boolean b, int i, int childCount, final int n, final int n2) {
        View child;
        LayoutParams layoutParams;
        Rect bounds;
        for (childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            child = this.getChildAt(i);
            layoutParams = (LayoutParams)child.getLayoutParams();
            bounds = this.getBounds(layoutParams.col, layoutParams.row, layoutParams.colspan, layoutParams.rowspan);
            if (layoutParams.row >= 0 && layoutParams.col >= 0) {
                child.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
            }
            else {
                child.setVisibility(8);
            }
        }
    }
    
    protected void onMeasure(int i, int n) {
        final int width = this._width;
        final int height = this._height;
        this._width = Math.min(View.MeasureSpec.getSize(n), View.MeasureSpec.getSize(i));
        final WindowManager windowManager = (WindowManager)this.getContext().getSystemService("window");
        i = windowManager.getDefaultDisplay().getWidth();
        n = windowManager.getDefaultDisplay().getHeight();
        this._width = Math.min(this._width, i);
        this._width = Math.min(this._width, n);
        this._height = this._width;
        View child;
        LayoutParams layoutParams;
        for (n = this.getChildCount(), i = 0; i < n; ++i) {
            child = this.getChildAt(i);
            layoutParams = (LayoutParams)child.getLayoutParams();
            child.measure(View.MeasureSpec.makeMeasureSpec(this._width * layoutParams.colspan / this._totalCols, 1073741824), View.MeasureSpec.makeMeasureSpec(this._height * layoutParams.rowspan / this._totalRows, 1073741824));
        }
        i = this._width;
        n = this._height;
        this.setMeasuredDimension(i, n);
        this.onSizeChanged(i, n, width, height);
    }
    
    protected void setSize(final int width, final int height) {
        this._width = width;
        this._height = height;
        this.invalidate();
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        public int col;
        public int colspan;
        public int row;
        public int rowspan;
        
        public LayoutParams(final int col, final int row, final int colspan, final int rowspan) {
            super(0, 0);
            this.colspan = 1;
            this.rowspan = 1;
            this.col = -1;
            this.row = -1;
            this.col = col;
            this.row = row;
            this.colspan = colspan;
            this.rowspan = rowspan;
        }
        
        public LayoutParams(final Context context, final AttributeSet set) {
            super(context, set);
            this.colspan = 1;
            this.rowspan = 1;
            this.col = -1;
            this.row = -1;
        }
        
        public LayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
            super(viewGroup.LayoutParams);
            this.colspan = 1;
            this.rowspan = 1;
            this.col = -1;
            this.row = -1;
        }
    }
}
