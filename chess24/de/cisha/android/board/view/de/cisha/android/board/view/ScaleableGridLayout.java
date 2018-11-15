/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.Display
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class ScaleableGridLayout
extends ViewGroup {
    protected Context _context;
    protected int _height = 400;
    protected int _totalCols = 8;
    protected int _totalRows = 8;
    protected int _width = 400;

    public ScaleableGridLayout(Context context, int n, int n2) {
        super(context);
        this.init(context, n, n2);
    }

    public ScaleableGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected ScaleableGridLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet);
        this.init(context, n, n2);
    }

    private void init(Context context, int n, int n2) {
        this._context = context;
        this._totalRows = n;
        this._totalCols = n2;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1, -1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(-1, -1, -1, -1);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(-1, -1, -1, -1);
    }

    protected Rect getBounds(int n, int n2) {
        return this.getBounds(n, n2, 1, 1);
    }

    protected Rect getBounds(int n, int n2, int n3, int n4) {
        float f = (float)this._width / (float)this._totalCols;
        float f2 = (float)this._height / (float)this._totalRows;
        return new Rect(Math.round((float)n * f), Math.round((float)n2 * f2), Math.round(f * (float)(n + n3)), Math.round(f2 * (float)(n2 + n4)));
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.getBounds(layoutParams.col, layoutParams.row, layoutParams.colspan, layoutParams.rowspan);
            if (layoutParams.row >= 0 && layoutParams.col >= 0) {
                view.layout(rect.left, rect.top, rect.right, rect.bottom);
                continue;
            }
            view.setVisibility(8);
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3 = this._width;
        int n4 = this._height;
        this._width = Math.min(View.MeasureSpec.getSize((int)n2), View.MeasureSpec.getSize((int)n));
        WindowManager windowManager = (WindowManager)this.getContext().getSystemService("window");
        n = windowManager.getDefaultDisplay().getWidth();
        n2 = windowManager.getDefaultDisplay().getHeight();
        this._width = Math.min(this._width, n);
        this._height = this._width = Math.min(this._width, n2);
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            windowManager = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)windowManager.getLayoutParams();
            windowManager.measure(View.MeasureSpec.makeMeasureSpec((int)(this._width * layoutParams.colspan / this._totalCols), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(this._height * layoutParams.rowspan / this._totalRows), (int)1073741824));
        }
        n = this._width;
        n2 = this._height;
        this.setMeasuredDimension(n, n2);
        this.onSizeChanged(n, n2, n3, n4);
    }

    protected void setSize(int n, int n2) {
        this._width = n;
        this._height = n2;
        this.invalidate();
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        public int col = -1;
        public int colspan = 1;
        public int row = -1;
        public int rowspan = 1;

        public LayoutParams(int n, int n2, int n3, int n4) {
            super(0, 0);
            this.col = n;
            this.row = n2;
            this.colspan = n3;
            this.rowspan = n4;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

}
