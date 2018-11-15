/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package uk.co.jasonfry.android.tools.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class PageControl
extends LinearLayout {
    private Drawable activeDrawable;
    private Drawable inactiveDrawable;
    private ArrayList<ImageView> indicators;
    private Context mContext;
    private int mCurrentPage = 0;
    private int mIndicatorSize = 12;
    private OnPageControlClickListener mOnPageControlClickListener = null;
    private int mPageCount = 0;

    public PageControl(Context context) {
        super(context);
        this.mContext = context;
        this.initPageControl();
    }

    public PageControl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    private void initPageControl() {
        Log.i((String)"uk.co.jasonfry.android.tools.ui.PageControl", (String)"Initialising PageControl");
        this.indicators = new ArrayList();
        this.activeDrawable = new ShapeDrawable();
        this.inactiveDrawable = new ShapeDrawable();
        this.activeDrawable.setBounds(0, 0, this.mIndicatorSize, this.mIndicatorSize);
        this.inactiveDrawable.setBounds(0, 0, this.mIndicatorSize, this.mIndicatorSize);
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize((float)this.mIndicatorSize, (float)this.mIndicatorSize);
        OvalShape ovalShape2 = new OvalShape();
        ovalShape2.resize((float)this.mIndicatorSize, (float)this.mIndicatorSize);
        TypedArray typedArray = this.mContext.getTheme().obtainStyledAttributes(new int[]{16842808, 16842810});
        ((ShapeDrawable)this.activeDrawable).getPaint().setColor(typedArray.getColor(0, -12303292));
        ((ShapeDrawable)this.inactiveDrawable).getPaint().setColor(typedArray.getColor(1, -3355444));
        ((ShapeDrawable)this.activeDrawable).setShape((Shape)ovalShape);
        ((ShapeDrawable)this.inactiveDrawable).setShape((Shape)ovalShape2);
        this.mIndicatorSize = (int)((float)this.mIndicatorSize * this.getResources().getDisplayMetrics().density);
        this.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (PageControl.this.mOnPageControlClickListener != null) {
                    if (motionEvent.getAction() != 1) {
                        return true;
                    }
                    if (PageControl.this.getOrientation() == 0) {
                        float f = (float)PageControl.this.getWidth() / (float)PageControl.this.getPageCount();
                        int n = (int)(motionEvent.getX() / f);
                        PageControl.this.mOnPageControlClickListener.goToPage(n);
                    } else {
                        float f = (float)PageControl.this.getHeight() / (float)PageControl.this.getPageCount();
                        int n = (int)(motionEvent.getY() / f);
                        PageControl.this.mOnPageControlClickListener.goToPage(n);
                    }
                    return false;
                }
                return true;
            }
        });
    }

    public Drawable getActiveDrawable() {
        return this.activeDrawable;
    }

    public int getCurrentPage() {
        return this.mCurrentPage;
    }

    public Drawable getInactiveDrawable() {
        return this.inactiveDrawable;
    }

    public int getIndicatorSize() {
        return this.mIndicatorSize;
    }

    public OnPageControlClickListener getOnPageControlClickListener() {
        return this.mOnPageControlClickListener;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    protected void onFinishInflate() {
        this.initPageControl();
    }

    public void setActiveDrawable(Drawable drawable) {
        this.activeDrawable = drawable;
        if (this.indicators.size() > this.mCurrentPage) {
            this.indicators.get(this.mCurrentPage).setBackgroundDrawable(this.activeDrawable);
        }
    }

    public void setCurrentPage(int n) {
        if (n < this.mPageCount) {
            this.indicators.get(this.mCurrentPage).setBackgroundDrawable(this.inactiveDrawable);
            this.indicators.get(n).setBackgroundDrawable(this.activeDrawable);
            this.mCurrentPage = n;
        }
    }

    public void setInactiveDrawable(Drawable drawable) {
        this.inactiveDrawable = drawable;
        for (int i = 0; i < this.mPageCount; ++i) {
            if (i == this.mCurrentPage) continue;
            this.indicators.get(i).setBackgroundDrawable(this.inactiveDrawable);
        }
    }

    public void setIndicatorSize(int n) {
        this.mIndicatorSize = n;
        for (n = 0; n < this.mPageCount; ++n) {
            this.indicators.get(n).setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(this.mIndicatorSize, this.mIndicatorSize));
        }
    }

    public void setOnPageControlClickListener(OnPageControlClickListener onPageControlClickListener) {
        this.mOnPageControlClickListener = onPageControlClickListener;
    }

    public void setPageCount(int n) {
        this.indicators.clear();
        this.removeAllViews();
        this.mPageCount = n;
        if (this.mCurrentPage >= n) {
            this.mCurrentPage = n - 1;
        }
        for (int i = 0; i < n; ++i) {
            ImageView imageView = new ImageView(this.mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.mIndicatorSize, this.mIndicatorSize);
            layoutParams.setMargins(this.mIndicatorSize / 2, this.mIndicatorSize, this.mIndicatorSize / 2, this.mIndicatorSize);
            imageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            imageView.setBackgroundDrawable(this.inactiveDrawable);
            this.indicators.add(imageView);
            this.addView((View)imageView);
        }
    }

    public static interface OnPageControlClickListener {
        public void goBackwards();

        public void goForwards();

        public void goToPage(int var1);
    }

}
