/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

@RestrictTo(value={RestrictTo.Scope.LIBRARY})
public class ContentFrameLayout
extends FrameLayout {
    private OnAttachListener mAttachListener;
    private final Rect mDecorPadding = new Rect();
    private TypedValue mFixedHeightMajor;
    private TypedValue mFixedHeightMinor;
    private TypedValue mFixedWidthMajor;
    private TypedValue mFixedWidthMinor;
    private TypedValue mMinWidthMajor;
    private TypedValue mMinWidthMinor;

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void dispatchFitSystemWindows(Rect rect) {
        this.fitSystemWindows(rect);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.mFixedHeightMajor == null) {
            this.mFixedHeightMajor = new TypedValue();
        }
        return this.mFixedHeightMajor;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.mFixedHeightMinor == null) {
            this.mFixedHeightMinor = new TypedValue();
        }
        return this.mFixedHeightMinor;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.mFixedWidthMajor == null) {
            this.mFixedWidthMajor = new TypedValue();
        }
        return this.mFixedWidthMajor;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.mFixedWidthMinor == null) {
            this.mFixedWidthMinor = new TypedValue();
        }
        return this.mFixedWidthMinor;
    }

    public TypedValue getMinWidthMajor() {
        if (this.mMinWidthMajor == null) {
            this.mMinWidthMajor = new TypedValue();
        }
        return this.mMinWidthMajor;
    }

    public TypedValue getMinWidthMinor() {
        if (this.mMinWidthMinor == null) {
            this.mMinWidthMinor = new TypedValue();
        }
        return this.mMinWidthMinor;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAttachListener != null) {
            this.mAttachListener.onAttachedFromWindow();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttachListener != null) {
            this.mAttachListener.onDetachedFromWindow();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onMeasure(int var1_1, int var2_2) {
        var11_3 = this.getContext().getResources().getDisplayMetrics();
        var3_4 = var11_3.widthPixels;
        var4_5 = var11_3.heightPixels;
        var7_6 = 1;
        var4_5 = var3_4 < var4_5 ? 1 : 0;
        var8_7 = View.MeasureSpec.getMode((int)var1_1);
        var9_8 = View.MeasureSpec.getMode((int)var2_2);
        if (var8_7 != Integer.MIN_VALUE) ** GOTO lbl-1000
        var10_9 = var4_5 != 0 ? this.mFixedWidthMinor : this.mFixedWidthMajor;
        if (var10_9 == null || var10_9.type == 0) ** GOTO lbl-1000
        var3_4 = var10_9.type == 5 ? (int)var10_9.getDimension(var11_3) : (var10_9.type == 6 ? (int)var10_9.getFraction((float)var11_3.widthPixels, (float)var11_3.widthPixels) : 0);
        if (var3_4 > 0) {
            var6_10 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var3_4 - (this.mDecorPadding.left + this.mDecorPadding.right), View.MeasureSpec.getSize((int)var1_1)), (int)1073741824);
            var5_11 = true;
        } else lbl-1000: // 3 sources:
        {
            var5_11 = false;
            var6_10 = var1_1;
        }
        var3_4 = var2_2;
        if (var9_8 == Integer.MIN_VALUE) {
            var10_9 = var4_5 != 0 ? this.mFixedHeightMajor : this.mFixedHeightMinor;
            var3_4 = var2_2;
            if (var10_9 != null) {
                var3_4 = var2_2;
                if (var10_9.type != 0) {
                    var1_1 = var10_9.type == 5 ? (int)var10_9.getDimension(var11_3) : (var10_9.type == 6 ? (int)var10_9.getFraction((float)var11_3.heightPixels, (float)var11_3.heightPixels) : 0);
                    var3_4 = var2_2;
                    if (var1_1 > 0) {
                        var3_4 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var1_1 - (this.mDecorPadding.top + this.mDecorPadding.bottom), View.MeasureSpec.getSize((int)var2_2)), (int)1073741824);
                    }
                }
            }
        }
        super.onMeasure(var6_10, var3_4);
        var9_8 = this.getMeasuredWidth();
        var6_10 = View.MeasureSpec.makeMeasureSpec((int)var9_8, (int)1073741824);
        if (var5_11 || var8_7 != Integer.MIN_VALUE) ** GOTO lbl-1000
        var10_9 = var4_5 != 0 ? this.mMinWidthMinor : this.mMinWidthMajor;
        if (var10_9 == null || var10_9.type == 0) ** GOTO lbl-1000
        var1_1 = var10_9.type == 5 ? (int)var10_9.getDimension(var11_3) : (var10_9.type == 6 ? (int)var10_9.getFraction((float)var11_3.widthPixels, (float)var11_3.widthPixels) : 0);
        var2_2 = var1_1;
        if (var1_1 > 0) {
            var2_2 = var1_1 - (this.mDecorPadding.left + this.mDecorPadding.right);
        }
        if (var9_8 < var2_2) {
            var2_2 = View.MeasureSpec.makeMeasureSpec((int)var2_2, (int)1073741824);
            var1_1 = var7_6;
        } else lbl-1000: // 3 sources:
        {
            var1_1 = 0;
            var2_2 = var6_10;
        }
        if (var1_1 == 0) return;
        super.onMeasure(var2_2, var3_4);
    }

    public void setAttachListener(OnAttachListener onAttachListener) {
        this.mAttachListener = onAttachListener;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setDecorPadding(int n, int n2, int n3, int n4) {
        this.mDecorPadding.set(n, n2, n3, n4);
        if (ViewCompat.isLaidOut((View)this)) {
            this.requestLayout();
        }
    }

    public static interface OnAttachListener {
        public void onAttachedFromWindow();

        public void onDetachedFromWindow();
    }

}
