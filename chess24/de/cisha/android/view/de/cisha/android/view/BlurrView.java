/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import de.cisha.android.view.AndroidBitmapHelper;

public class BlurrView
extends FrameLayout {
    private boolean _blurrActive;
    private ImageView _blurrImage;
    private int _blurrRadius;
    private boolean _recalculateImage;

    public BlurrView(Context context) {
        super(context);
        this.init();
    }

    public BlurrView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public BlurrView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void init() {
        this._blurrImage = new ImageView(this.getContext());
        this._blurrImage.setBackgroundColor(-1);
        this._blurrRadius = 10;
        this.setBlurrActive(false);
        this.addView((View)this._blurrImage, -1, -2);
    }

    private void recalculateImage() {
        if (this._blurrActive && this.getWidth() > 0 && this.getHeight() > 0 && this._recalculateImage) {
            Bitmap bitmap = Bitmap.createBitmap((int)this.getWidth(), (int)this.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            this._recalculateImage = false;
            this._blurrImage.bringToFront();
            for (int i = 0; i < this.getChildCount(); ++i) {
                View view = this.getChildAt(i);
                if (this._blurrImage == view || view.getWidth() <= 0 || view.getHeight() <= 0 || view.getVisibility() != 0) continue;
                this.drawChild(canvas, view, this.getDrawingTime());
            }
            canvas = AndroidBitmapHelper.blurImage(this.getContext(), bitmap, this._blurrRadius);
            bitmap.recycle();
            this._blurrImage.setImageBitmap((Bitmap)canvas);
        }
    }

    public int getBlurrRadius() {
        return this._blurrRadius;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        n3 = n = (n4 = 0);
        n2 = n;
        for (n = n4; n < this.getChildCount(); ++n) {
            View view = this.getChildAt(n);
            int n5 = n2;
            n4 = n3;
            if (view != this._blurrImage) {
                n4 = Math.max(n3, view.getHeight());
                n5 = Math.max(n2, view.getWidth());
            }
            n2 = n5;
            n3 = n4;
        }
        this._blurrImage.layout(0, 0, n2, n3);
        this.recalculateImage();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = n2 = 0;
        for (n = 0; n < this.getChildCount(); ++n) {
            View view = this.getChildAt(n);
            int n4 = n3;
            int n5 = n2;
            if (view != this._blurrImage) {
                n4 = Math.max(n3, view.getMeasuredHeight());
                n5 = Math.max(n2, view.getMeasuredWidth());
            }
            n3 = n4;
            n2 = n5;
        }
        this._blurrImage.measure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824));
        this.setMeasuredDimension(n2, n3);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3 || n2 != n4) {
            this.refreshBlurr();
        }
    }

    public void refreshBlurr() {
        this._recalculateImage = true;
        this.requestLayout();
    }

    public void setBlurrActive(boolean bl) {
        this._blurrActive = bl;
        if (bl) {
            this.refreshBlurr();
            this._blurrImage.setVisibility(0);
            this._blurrImage.bringToFront();
            return;
        }
        this._blurrImage.setVisibility(4);
    }

    protected void setBlurrClickListener(View.OnClickListener onClickListener) {
        this._blurrImage.setOnClickListener(onClickListener);
    }

    public void setBlurrRadius(int n) {
        this._blurrRadius = n;
    }
}
