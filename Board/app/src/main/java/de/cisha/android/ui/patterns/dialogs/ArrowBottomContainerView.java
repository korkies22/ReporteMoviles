// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.content.res.TypedArray;
import android.view.View.MeasureSpec;
import android.graphics.Canvas;
import android.annotation.SuppressLint;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout.LayoutParams;
import android.os.Build.VERSION;
import java.util.Iterator;
import android.view.ViewParent;
import java.util.LinkedList;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.CornerPathEffect;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;

public class ArrowBottomContainerView extends LinearLayout
{
    private int _arrowHalfWidth;
    private int _arrowHeight;
    private int _arrowX;
    private int _bottomPadding;
    private View _clippedView;
    private int _colorStroke;
    private int[] _colors;
    private int _cornerSize;
    private boolean _drawArrow;
    private int _leftPadding;
    private Paint _pFill;
    private Paint _pStroke;
    private boolean _paddingHasBeenAdjustedToArrow;
    private float[] _positions;
    private int _rightPadding;
    private int _strokeWidth;
    private int _topPadding;
    private Path pth;
    
    public ArrowBottomContainerView(final Context context) {
        super(context);
        this._drawArrow = true;
        this.init(this._paddingHasBeenAdjustedToArrow = false);
    }
    
    public ArrowBottomContainerView(Context obtainStyledAttributes, final AttributeSet set) {
        super(obtainStyledAttributes, set);
        this._drawArrow = true;
        this._paddingHasBeenAdjustedToArrow = false;
        obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.ArrowBottomContainerView);
        try {
            this._arrowHeight = ((TypedArray)obtainStyledAttributes).getDimensionPixelSize(R.styleable.ArrowBottomContainerView_arrowHeight, 0);
            this._strokeWidth = ((TypedArray)obtainStyledAttributes).getDimensionPixelSize(R.styleable.ArrowBottomContainerView_aBorderWidth, 0);
            this._cornerSize = ((TypedArray)obtainStyledAttributes).getDimensionPixelSize(R.styleable.ArrowBottomContainerView_cornerSize, 0);
            this._arrowX = ((TypedArray)obtainStyledAttributes).getDimensionPixelSize(R.styleable.ArrowBottomContainerView_arrowX, 0);
            ((TypedArray)obtainStyledAttributes).recycle();
            this.init(true);
        }
        finally {
            ((TypedArray)obtainStyledAttributes).recycle();
        }
    }
    
    private void init(final boolean b) {
        final float density = this.getResources().getDisplayMetrics().density;
        this.setWillNotDraw(false);
        if (!b) {
            this._arrowHeight = (int)(15.0f * density);
            this._strokeWidth = (int)(2.0f * density);
            this._cornerSize = (int)(5.0f * density);
            this.setArrowX(20);
        }
        this._arrowHalfWidth = (int)(0.7 * this._arrowHeight);
        this._pStroke = new Paint();
        this._pFill = new Paint();
        if (this._cornerSize > 0) {
            final CornerPathEffect cornerPathEffect = new CornerPathEffect((float)this._cornerSize);
            this._pFill.setPathEffect((PathEffect)cornerPathEffect);
            this._pStroke.setPathEffect((PathEffect)cornerPathEffect);
        }
        this._pFill.setStyle(Paint.Style.FILL);
        this.pth = new Path();
        this._colors = new int[] { -6447715, -2105377, -6447715 };
        this._positions = new float[] { 0.0f, 0.5f, 1.0f };
        this._pStroke.setStrokeWidth((float)this._strokeWidth);
        this._pStroke.setStyle(Paint.Style.STROKE);
        final Paint pStroke = this._pStroke;
        int colorStroke;
        if (this._colorStroke != 0) {
            colorStroke = this._colorStroke;
        }
        else {
            colorStroke = -1;
        }
        pStroke.setColor(colorStroke);
        this._pStroke.setShadowLayer(3.0f, 0.0f, (float)this._strokeWidth, 2130706432);
        this.resetPadding();
        this.initDraw();
    }
    
    private void initDraw() {
        final int n = this.getWidth() - this._strokeWidth;
        final int height = this.getHeight();
        final int strokeWidth = this._strokeWidth;
        int n2;
        if (this._arrowHeight > 0) {
            n2 = 1;
        }
        else {
            n2 = 2;
        }
        final int n3 = height - strokeWidth * n2;
        final int strokeWidth2 = this._strokeWidth;
        int arrowHeight = 0;
        final int n4 = strokeWidth2 + 0;
        final int strokeWidth3 = this._strokeWidth;
        this.pth.reset();
        if (this._cornerSize + n4 + this._arrowHalfWidth > this._arrowX) {
            this._arrowX = this._cornerSize + n4 + this._arrowHalfWidth;
        }
        if (n - this._cornerSize - this._arrowHalfWidth < this._arrowX) {
            this._arrowX = n - this._cornerSize - this._arrowHalfWidth;
        }
        final Paint pFill = this._pFill;
        final float n5 = n4;
        final float n6 = strokeWidth3 + 0;
        final float n7 = n;
        pFill.setShader((Shader)new LinearGradient(n5, n6, n7, n6, this._colors, this._positions, Shader.TileMode.CLAMP));
        this.pth.moveTo(n5, n6);
        this.pth.lineTo(n7, n6);
        if (this.isDrawingArrow()) {
            arrowHeight = this.getArrowHeight();
        }
        final Path pth = this.pth;
        final float n8 = n3 - arrowHeight;
        pth.lineTo(n7, n8);
        if (this.isDrawingArrow()) {
            this.pth.lineTo((float)(this._arrowX + this._arrowHalfWidth), n8);
            this.pth.lineTo((float)this._arrowX, (float)n3);
            this.pth.lineTo((float)(this._arrowX - this._arrowHalfWidth), n8);
        }
        this.pth.lineTo(n5, n8);
        this.pth.lineTo(n5, n6);
        this.pth.close();
        this.invalidate();
    }
    
    private void locateArrowToViewCenter(View view, final boolean b, int n, int measuredHeight, int n2, int measuredWidth) {
        final LinkedList<View> list = new LinkedList<View>();
        for (ViewParent viewParent = this.getParent(); viewParent instanceof View; viewParent = viewParent.getParent()) {
            list.add((View)viewParent);
        }
        n2 = view.getLeft() + view.getWidth() / 2;
        n = view.getTop() - view.getPaddingTop();
        final View view2 = null;
        ViewParent viewParent2 = view.getParent();
        while (true) {
            view = view2;
            if (!(viewParent2 instanceof View)) {
                break;
            }
            view = (View)viewParent2;
            if (list.contains(view)) {
                break;
            }
            n2 += view.getLeft();
            n += view.getTop() - view.getPaddingTop();
            viewParent2 = viewParent2.getParent();
        }
        final ViewParent parent = this.getParent();
        if (parent instanceof View) {
            final View view3 = (View)parent;
            measuredWidth = this.getMeasuredWidth();
            measuredHeight = this.getMeasuredHeight();
            int n3;
            if ((n3 = measuredWidth) > view3.getWidth()) {
                n3 = view3.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            }
            int n4;
            if ((n4 = measuredHeight) > view3.getHeight()) {
                n4 = view3.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            }
            final float density = this.getResources().getDisplayMetrics().density;
            measuredHeight = (int)(n2 - n3 / 2.0);
            final float n5 = measuredHeight;
            final float n6 = view3.getLeft();
            final float n7 = 5.0f * density;
            if (n5 < n6 + n7) {
                measuredHeight = (int)(view3.getLeft() + n7);
            }
            if ((measuredWidth = measuredHeight + n3) > view3.getRight() - n7) {
                measuredWidth = (int)(view3.getRight() - n7);
                measuredHeight = (int)Math.max(n7, measuredWidth - n3);
            }
            final float n8 = n;
            final float n9 = view3.getBottom() - view3.getPaddingBottom() - view3.getPaddingTop();
            final float n10 = 1.0f * density;
            if (n8 > n9 - n10) {
                n = (int)(view3.getBottom() - view3.getPaddingBottom() - view3.getPaddingTop() - n10);
            }
            int n11;
            if ((n11 = n - n4) < view3.getTop() + view3.getPaddingTop()) {
                n11 = view3.getTop() + view3.getPaddingTop();
            }
            final Iterator<Object> iterator = list.iterator();
            int n12 = measuredHeight;
            while (iterator.hasNext()) {
                final View view4 = iterator.next();
                if (view4 == view) {
                    break;
                }
                n12 += view4.getLeft();
            }
            this._arrowX = n2 - n12;
            this.doLayoutInOwnArea(b, measuredHeight, n11, measuredWidth, n);
        }
    }
    
    private void resetPadding() {
        final Paint pStroke = this._pStroke;
        int arrowHeight = 0;
        int n;
        if (pStroke != null) {
            n = (int)this._pStroke.getStrokeWidth();
        }
        else {
            n = 0;
        }
        if (!this._paddingHasBeenAdjustedToArrow) {
            this._paddingHasBeenAdjustedToArrow = true;
            final int leftPadding = this._leftPadding;
            final int topPadding = this._topPadding;
            final int rightPadding = this._rightPadding;
            final int bottomPadding = this._bottomPadding;
            if (this.isDrawingArrow()) {
                arrowHeight = this.getArrowHeight();
            }
            this.setPadding(leftPadding + n, topPadding + n, rightPadding + n, bottomPadding + arrowHeight + this._strokeWidth * 2);
        }
    }
    
    public void clipArrowToView(final View clippedView) {
        this._clippedView = clippedView;
        this.requestLayout();
    }
    
    @SuppressLint({ "WrongCall", "NewApi" })
    public void doLayoutInOwnArea(final boolean b, final int n, final int n2, final int right, final int bottom) {
        if (this._clippedView != null) {
            if (Build.VERSION.SDK_INT >= 11) {
                this.setTop(n2);
                this.setBottom(bottom);
                this.setLeft(n);
                this.setRight(right);
            }
            else {
                final ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
                if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
                    final AbsoluteLayout.LayoutParams layoutParams2 = (AbsoluteLayout.LayoutParams)layoutParams;
                    layoutParams2.x = n;
                    layoutParams2.y = n2;
                    layoutParams2.height = bottom - n2;
                    layoutParams2.width = right - n;
                    this.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
                    this.requestLayout();
                }
                else if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    final ViewGroup.MarginLayoutParams layoutParams3 = (ViewGroup.MarginLayoutParams)layoutParams;
                    layoutParams3.setMargins(n, n2, right, bottom);
                    this.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
                    this.requestLayout();
                }
            }
        }
        super.onLayout(b, n, n2, right, bottom);
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
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.pth, this._pStroke);
        canvas.drawPath(this.pth, this._pFill);
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        if (this._clippedView != null) {
            this.locateArrowToViewCenter(this._clippedView, b, n, n2, n3, n4);
            return;
        }
        this.doLayoutInOwnArea(b, n, n2, n3, n4);
    }
    
    protected void onMeasure(int left, int top) {
        super.onMeasure(left, top);
        if (this._clippedView != null && Build.VERSION.SDK_INT < 11) {
            final ViewParent parent = this.getParent();
            if (parent instanceof View) {
                final View view = (View)parent;
                left = view.getLeft();
                top = view.getTop();
                final int right = view.getRight();
                final int bottom = view.getBottom();
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(right - left, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(bottom - top, Integer.MIN_VALUE));
                this.locateArrowToViewCenter(this._clippedView, true, left, top, right, bottom);
            }
        }
    }
    
    public void setArrowHeight(final int arrowHeight) {
        this._arrowHeight = arrowHeight;
        this._arrowHalfWidth = (int)(arrowHeight * 0.7f);
        this._paddingHasBeenAdjustedToArrow = false;
        this.resetPadding();
        this.invalidate();
    }
    
    public void setArrowX(final int arrowX) {
        this._arrowX = arrowX;
        this.invalidate();
    }
    
    public void setDrawsArrow(final boolean drawArrow) {
        this._drawArrow = drawArrow;
        this._paddingHasBeenAdjustedToArrow = false;
        this.resetPadding();
    }
    
    public void setPadding(final int leftPadding, final int topPadding, final int rightPadding, final int bottomPadding) {
        if (!this._paddingHasBeenAdjustedToArrow) {
            this._leftPadding = leftPadding;
            this._topPadding = topPadding;
            this._rightPadding = rightPadding;
            this._bottomPadding = bottomPadding;
        }
        super.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }
    
    public void setStrokeColor(final int colorStroke) {
        this._colorStroke = colorStroke;
        this._pStroke.setColor(this._colorStroke);
        this.invalidate();
    }
}
