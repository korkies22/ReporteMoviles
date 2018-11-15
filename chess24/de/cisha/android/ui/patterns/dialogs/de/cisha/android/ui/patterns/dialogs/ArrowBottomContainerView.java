/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.CornerPathEffect
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.AbsoluteLayout
 *  android.widget.AbsoluteLayout$LayoutParams
 *  android.widget.LinearLayout
 */
package de.cisha.android.ui.patterns.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.R;
import java.util.Iterator;
import java.util.LinkedList;

public class ArrowBottomContainerView
extends LinearLayout {
    private int _arrowHalfWidth;
    private int _arrowHeight;
    private int _arrowX;
    private int _bottomPadding;
    private View _clippedView;
    private int _colorStroke;
    private int[] _colors;
    private int _cornerSize;
    private boolean _drawArrow = true;
    private int _leftPadding;
    private Paint _pFill;
    private Paint _pStroke;
    private boolean _paddingHasBeenAdjustedToArrow = false;
    private float[] _positions;
    private int _rightPadding;
    private int _strokeWidth;
    private int _topPadding;
    private Path pth;

    public ArrowBottomContainerView(Context context) {
        super(context);
        this.init(false);
    }

    public ArrowBottomContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ArrowBottomContainerView);
        try {
            this._arrowHeight = context.getDimensionPixelSize(R.styleable.ArrowBottomContainerView_arrowHeight, 0);
            this._strokeWidth = context.getDimensionPixelSize(R.styleable.ArrowBottomContainerView_aBorderWidth, 0);
            this._cornerSize = context.getDimensionPixelSize(R.styleable.ArrowBottomContainerView_cornerSize, 0);
            this._arrowX = context.getDimensionPixelSize(R.styleable.ArrowBottomContainerView_arrowX, 0);
            this.init(true);
            return;
        }
        finally {
            context.recycle();
        }
    }

    private void init(boolean bl) {
        CornerPathEffect cornerPathEffect;
        float f = this.getResources().getDisplayMetrics().density;
        this.setWillNotDraw(false);
        if (!bl) {
            this._arrowHeight = (int)(15.0f * f);
            this._strokeWidth = (int)(2.0f * f);
            this._cornerSize = (int)(5.0f * f);
            this.setArrowX(20);
        }
        this._arrowHalfWidth = (int)(0.7 * (double)this._arrowHeight);
        this._pStroke = new Paint();
        this._pFill = new Paint();
        if (this._cornerSize > 0) {
            cornerPathEffect = new CornerPathEffect((float)this._cornerSize);
            this._pFill.setPathEffect((PathEffect)cornerPathEffect);
            this._pStroke.setPathEffect((PathEffect)cornerPathEffect);
        }
        this._pFill.setStyle(Paint.Style.FILL);
        this.pth = new Path();
        this._colors = new int[]{-6447715, -2105377, -6447715};
        this._positions = new float[]{0.0f, 0.5f, 1.0f};
        this._pStroke.setStrokeWidth((float)this._strokeWidth);
        this._pStroke.setStyle(Paint.Style.STROKE);
        cornerPathEffect = this._pStroke;
        int n = this._colorStroke != 0 ? this._colorStroke : -1;
        cornerPathEffect.setColor(n);
        this._pStroke.setShadowLayer(3.0f, 0.0f, (float)this._strokeWidth, 2130706432);
        this.resetPadding();
        this.initDraw();
    }

    private void initDraw() {
        int n = this.getWidth() - this._strokeWidth;
        int n2 = this.getHeight();
        int n3 = this._strokeWidth;
        int n4 = this._arrowHeight > 0 ? 1 : 2;
        n2 -= n3 * n4;
        n3 = this._strokeWidth;
        n4 = 0;
        int n5 = this._strokeWidth;
        this.pth.reset();
        if (this._cornerSize + (n3 += 0) + this._arrowHalfWidth > this._arrowX) {
            this._arrowX = this._cornerSize + n3 + this._arrowHalfWidth;
        }
        if (n - this._cornerSize - this._arrowHalfWidth < this._arrowX) {
            this._arrowX = n - this._cornerSize - this._arrowHalfWidth;
        }
        Paint paint = this._pFill;
        float f = n3;
        float f2 = n5 + 0;
        float f3 = n;
        paint.setShader((Shader)new LinearGradient(f, f2, f3, f2, this._colors, this._positions, Shader.TileMode.CLAMP));
        this.pth.moveTo(f, f2);
        this.pth.lineTo(f3, f2);
        if (this.isDrawingArrow()) {
            n4 = this.getArrowHeight();
        }
        paint = this.pth;
        float f4 = n2 - n4;
        paint.lineTo(f3, f4);
        if (this.isDrawingArrow()) {
            this.pth.lineTo((float)(this._arrowX + this._arrowHalfWidth), f4);
            this.pth.lineTo((float)this._arrowX, (float)n2);
            this.pth.lineTo((float)(this._arrowX - this._arrowHalfWidth), f4);
        }
        this.pth.lineTo(f, f4);
        this.pth.lineTo(f, f2);
        this.pth.close();
        this.invalidate();
    }

    private void locateArrowToViewCenter(View view, boolean bl, int n, int n2, int n3, int n4) {
        LinkedList<View> linkedList = new LinkedList<View>();
        Object object = this.getParent();
        while (object instanceof View) {
            linkedList.add((View)object);
            object = object.getParent();
        }
        n3 = view.getLeft() + view.getWidth() / 2;
        n = view.getTop() - view.getPaddingTop();
        View view2 = null;
        object = view.getParent();
        do {
            view = view2;
            if (!(object instanceof View) || linkedList.contains((Object)(view = (View)object))) break;
            n3 += view.getLeft();
            n += view.getTop() - view.getPaddingTop();
            object = object.getParent();
        } while (true);
        object = this.getParent();
        if (object instanceof View) {
            float f;
            int n5;
            object = (View)object;
            n4 = this.getMeasuredWidth();
            n2 = this.getMeasuredHeight();
            int n6 = n4;
            if (n4 > object.getWidth()) {
                n6 = object.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            }
            int n7 = n2;
            if (n2 > object.getHeight()) {
                n7 = object.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            }
            float f2 = this.getResources().getDisplayMetrics().density;
            n2 = (int)((double)n3 - (double)n6 / 2.0);
            float f3 = n2;
            float f4 = object.getLeft();
            if (f3 < f4 + (f = 5.0f * f2)) {
                n2 = (int)((float)object.getLeft() + f);
            }
            n4 = n5 = n2 + n6;
            if ((float)n5 > (float)object.getRight() - f) {
                n4 = (int)((float)object.getRight() - f);
                n2 = (int)Math.max(f, (float)(n4 - n6));
            }
            if ((f3 = (float)n) > (f4 = (float)(object.getBottom() - object.getPaddingBottom() - object.getPaddingTop())) - (f2 = 1.0f * f2)) {
                n = (int)((float)(object.getBottom() - object.getPaddingBottom() - object.getPaddingTop()) - f2);
            }
            n7 = n6 = n - n7;
            if (n6 < object.getTop() + object.getPaddingTop()) {
                n7 = object.getTop() + object.getPaddingTop();
            }
            object = linkedList.iterator();
            n6 = n2;
            while (object.hasNext() && (view2 = (View)object.next()) != view) {
                n6 += view2.getLeft();
            }
            this._arrowX = n3 - n6;
            this.doLayoutInOwnArea(bl, n2, n7, n4, n);
            return;
        }
    }

    private void resetPadding() {
        Paint paint = this._pStroke;
        int n = 0;
        int n2 = paint != null ? (int)this._pStroke.getStrokeWidth() : 0;
        if (!this._paddingHasBeenAdjustedToArrow) {
            this._paddingHasBeenAdjustedToArrow = true;
            int n3 = this._leftPadding;
            int n4 = this._topPadding;
            int n5 = this._rightPadding;
            int n6 = this._bottomPadding;
            if (this.isDrawingArrow()) {
                n = this.getArrowHeight();
            }
            this.setPadding(n3 + n2, n4 + n2, n5 + n2, n6 + n + this._strokeWidth * 2);
        }
    }

    public void clipArrowToView(View view) {
        this._clippedView = view;
        this.requestLayout();
    }

    @SuppressLint(value={"WrongCall", "NewApi"})
    public void doLayoutInOwnArea(boolean bl, int n, int n2, int n3, int n4) {
        if (this._clippedView != null) {
            if (Build.VERSION.SDK_INT >= 11) {
                this.setTop(n2);
                this.setBottom(n4);
                this.setLeft(n);
                this.setRight(n3);
            } else {
                ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
                if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
                    layoutParams = (AbsoluteLayout.LayoutParams)layoutParams;
                    layoutParams.x = n;
                    layoutParams.y = n2;
                    layoutParams.height = n4 - n2;
                    layoutParams.width = n3 - n;
                    this.setLayoutParams(layoutParams);
                    this.requestLayout();
                } else if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                    layoutParams.setMargins(n, n2, n3, n4);
                    this.setLayoutParams(layoutParams);
                    this.requestLayout();
                }
            }
        }
        super.onLayout(bl, n, n2, n3, n4);
        this.initDraw();
    }

    public int getArrowHeight() {
        return this._arrowHeight;
    }

    public int getArrowX() {
        return this._arrowX;
    }

    public boolean isDrawingArrow() {
        return this._drawArrow;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.pth, this._pStroke);
        canvas.drawPath(this.pth, this._pFill);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this._clippedView != null) {
            this.locateArrowToViewCenter(this._clippedView, bl, n, n2, n3, n4);
            return;
        }
        this.doLayoutInOwnArea(bl, n, n2, n3, n4);
    }

    protected void onMeasure(int n, int n2) {
        ViewParent viewParent;
        super.onMeasure(n, n2);
        if (this._clippedView != null && Build.VERSION.SDK_INT < 11 && (viewParent = this.getParent()) instanceof View) {
            viewParent = (View)viewParent;
            n = viewParent.getLeft();
            n2 = viewParent.getTop();
            int n3 = viewParent.getRight();
            int n4 = viewParent.getBottom();
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n), (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)(n4 - n2), (int)Integer.MIN_VALUE));
            this.locateArrowToViewCenter(this._clippedView, true, n, n2, n3, n4);
        }
    }

    public void setArrowHeight(int n) {
        this._arrowHeight = n;
        this._arrowHalfWidth = (int)((float)n * 0.7f);
        this._paddingHasBeenAdjustedToArrow = false;
        this.resetPadding();
        this.invalidate();
    }

    public void setArrowX(int n) {
        this._arrowX = n;
        this.invalidate();
    }

    public void setDrawsArrow(boolean bl) {
        this._drawArrow = bl;
        this._paddingHasBeenAdjustedToArrow = false;
        this.resetPadding();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        if (!this._paddingHasBeenAdjustedToArrow) {
            this._leftPadding = n;
            this._topPadding = n2;
            this._rightPadding = n3;
            this._bottomPadding = n4;
        }
        super.setPadding(n, n2, n3, n4);
    }

    public void setStrokeColor(int n) {
        this._colorStroke = n;
        this._pStroke.setColor(this._colorStroke);
        this.invalidate();
    }
}
