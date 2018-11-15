/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.Gravity
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class ForegroundLinearLayout
extends LinearLayoutCompat {
    private Drawable mForeground;
    boolean mForegroundBoundsChanged = false;
    private int mForegroundGravity = 119;
    protected boolean mForegroundInPadding = true;
    private final Rect mOverlayBounds = new Rect();
    private final Rect mSelfBounds = new Rect();

    public ForegroundLinearLayout(Context context) {
        this(context, null);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ForegroundLinearLayout, n, 0);
        this.mForegroundGravity = context.getInt(R.styleable.ForegroundLinearLayout_android_foregroundGravity, this.mForegroundGravity);
        attributeSet = context.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground);
        if (attributeSet != null) {
            this.setForeground((Drawable)attributeSet);
        }
        this.mForegroundInPadding = context.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
        context.recycle();
    }

    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        if (this.mForeground != null) {
            Drawable drawable = this.mForeground;
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                Rect rect = this.mSelfBounds;
                Rect rect2 = this.mOverlayBounds;
                int n = this.getRight() - this.getLeft();
                int n2 = this.getBottom() - this.getTop();
                if (this.mForegroundInPadding) {
                    rect.set(0, 0, n, n2);
                } else {
                    rect.set(this.getPaddingLeft(), this.getPaddingTop(), n - this.getPaddingRight(), n2 - this.getPaddingBottom());
                }
                Gravity.apply((int)this.mForegroundGravity, (int)drawable.getIntrinsicWidth(), (int)drawable.getIntrinsicHeight(), (Rect)rect, (Rect)rect2);
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    @RequiresApi(value=21)
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mForeground != null) {
            this.mForeground.setHotspot(f, f2);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mForeground != null && this.mForeground.isStateful()) {
            this.mForeground.setState(this.getDrawableState());
        }
    }

    public Drawable getForeground() {
        return this.mForeground;
    }

    public int getForegroundGravity() {
        return this.mForegroundGravity;
    }

    @RequiresApi(value=11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mForeground != null) {
            this.mForeground.jumpToCurrentState();
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mForegroundBoundsChanged = bl | this.mForegroundBoundsChanged;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mForegroundBoundsChanged = true;
    }

    public void setForeground(Drawable drawable) {
        if (this.mForeground != drawable) {
            if (this.mForeground != null) {
                this.mForeground.setCallback(null);
                this.unscheduleDrawable(this.mForeground);
            }
            this.mForeground = drawable;
            if (drawable != null) {
                this.setWillNotDraw(false);
                drawable.setCallback((Drawable.Callback)this);
                if (drawable.isStateful()) {
                    drawable.setState(this.getDrawableState());
                }
                if (this.mForegroundGravity == 119) {
                    drawable.getPadding(new Rect());
                }
            } else {
                this.setWillNotDraw(true);
            }
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setForegroundGravity(int n) {
        if (this.mForegroundGravity != n) {
            int n2 = n;
            if ((8388615 & n) == 0) {
                n2 = n | 8388611;
            }
            n = n2;
            if ((n2 & 112) == 0) {
                n = n2 | 48;
            }
            this.mForegroundGravity = n;
            if (this.mForegroundGravity == 119 && this.mForeground != null) {
                Rect rect = new Rect();
                this.mForeground.getPadding(rect);
            }
            this.requestLayout();
        }
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (!super.verifyDrawable(drawable) && drawable != this.mForeground) {
            return false;
        }
        return true;
    }
}
