// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import android.view.View.OnClickListener;
import android.view.View.MeasureSpec;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import android.widget.FrameLayout;

public class BlurrView extends FrameLayout
{
    private boolean _blurrActive;
    private ImageView _blurrImage;
    private int _blurrRadius;
    private boolean _recalculateImage;
    
    public BlurrView(final Context context) {
        super(context);
        this.init();
    }
    
    public BlurrView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public BlurrView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.init();
    }
    
    private void init() {
        (this._blurrImage = new ImageView(this.getContext())).setBackgroundColor(-1);
        this._blurrRadius = 10;
        this.setBlurrActive(false);
        this.addView((View)this._blurrImage, -1, -2);
    }
    
    private void recalculateImage() {
        if (this._blurrActive && this.getWidth() > 0 && this.getHeight() > 0 && this._recalculateImage) {
            final Bitmap bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            int i = 0;
            this._recalculateImage = false;
            this._blurrImage.bringToFront();
            while (i < this.getChildCount()) {
                final View child = this.getChildAt(i);
                if (this._blurrImage != child && child.getWidth() > 0 && child.getHeight() > 0 && child.getVisibility() == 0) {
                    this.drawChild(canvas, child, this.getDrawingTime());
                }
                ++i;
            }
            final Bitmap blurImage = AndroidBitmapHelper.blurImage(this.getContext(), bitmap, this._blurrRadius);
            bitmap.recycle();
            this._blurrImage.setImageBitmap(blurImage);
        }
    }
    
    public int getBlurrRadius() {
        return this._blurrRadius;
    }
    
    protected void onLayout(final boolean b, int i, int n, int n2, int max) {
        super.onLayout(b, i, n, n2, max);
        View child;
        int max2;
        for (max = 0, i = (n = (n2 = max)), i = max; i < this.getChildCount(); ++i, n = max2, n2 = max) {
            child = this.getChildAt(i);
            max2 = n;
            max = n2;
            if (child != this._blurrImage) {
                max = Math.max(n2, child.getHeight());
                max2 = Math.max(n, child.getWidth());
            }
        }
        this._blurrImage.layout(0, 0, n, n2);
        this.recalculateImage();
    }
    
    protected void onMeasure(int i, int n) {
        super.onMeasure(i, n);
        i = 0;
        int n2;
        n = (n2 = 0);
        while (i < this.getChildCount()) {
            final View child = this.getChildAt(i);
            int max = n2;
            int max2 = n;
            if (child != this._blurrImage) {
                max = Math.max(n2, child.getMeasuredHeight());
                max2 = Math.max(n, child.getMeasuredWidth());
            }
            ++i;
            n2 = max;
            n = max2;
        }
        this._blurrImage.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), View.MeasureSpec.makeMeasureSpec(n, 1073741824));
        this.setMeasuredDimension(n, n2);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3 || n2 != n4) {
            this.refreshBlurr();
        }
    }
    
    public void refreshBlurr() {
        this._recalculateImage = true;
        this.requestLayout();
    }
    
    public void setBlurrActive(final boolean blurrActive) {
        this._blurrActive = blurrActive;
        if (blurrActive) {
            this.refreshBlurr();
            this._blurrImage.setVisibility(0);
            this._blurrImage.bringToFront();
            return;
        }
        this._blurrImage.setVisibility(4);
    }
    
    protected void setBlurrClickListener(final View.OnClickListener onClickListener) {
        this._blurrImage.setOnClickListener(onClickListener);
    }
    
    public void setBlurrRadius(final int blurrRadius) {
        this._blurrRadius = blurrRadius;
    }
}
