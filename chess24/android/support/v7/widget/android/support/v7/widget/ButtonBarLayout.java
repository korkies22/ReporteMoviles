/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class ButtonBarLayout
extends LinearLayout {
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize = -1;
    private int mMinimumHeight = 0;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        this.mAllowStacking = context.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true);
        context.recycle();
    }

    private int getNextVisibleChildIndex(int n) {
        int n2 = this.getChildCount();
        while (n < n2) {
            if (this.getChildAt(n).getVisibility() == 0) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private boolean isStacked() {
        if (this.getOrientation() == 1) {
            return true;
        }
        return false;
    }

    private void setStacked(boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public int getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight());
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4 = View.MeasureSpec.getSize((int)n);
        boolean bl = this.mAllowStacking;
        int n5 = 0;
        if (bl) {
            if (n4 > this.mLastWidthSize && this.isStacked()) {
                this.setStacked(false);
            }
            this.mLastWidthSize = n4;
        }
        if (!this.isStacked() && View.MeasureSpec.getMode((int)n) == 1073741824) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)Integer.MIN_VALUE);
            n4 = 1;
        } else {
            n3 = n;
            n4 = 0;
        }
        super.onMeasure(n3, n2);
        int n6 = n4;
        if (this.mAllowStacking) {
            n6 = n4;
            if (!this.isStacked()) {
                n3 = (this.getMeasuredWidthAndState() & -16777216) == 16777216 ? 1 : 0;
                n6 = n4;
                if (n3 != 0) {
                    this.setStacked(true);
                    n6 = 1;
                }
            }
        }
        if (n6 != 0) {
            super.onMeasure(n, n2);
        }
        n4 = this.getNextVisibleChildIndex(0);
        n = n5;
        if (n4 >= 0) {
            View view = this.getChildAt(n4);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            n2 = 0 + (this.getPaddingTop() + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
            if (this.isStacked()) {
                n4 = this.getNextVisibleChildIndex(n4 + 1);
                n = n2;
                if (n4 >= 0) {
                    n = n2 + (this.getChildAt(n4).getPaddingTop() + (int)(16.0f * this.getResources().getDisplayMetrics().density));
                }
            } else {
                n = n2 + this.getPaddingBottom();
            }
        }
        if (ViewCompat.getMinimumHeight((View)this) != n) {
            this.setMinimumHeight(n);
        }
    }

    public void setAllowStacking(boolean bl) {
        if (this.mAllowStacking != bl) {
            this.mAllowStacking = bl;
            if (!this.mAllowStacking && this.getOrientation() == 1) {
                this.setStacked(false);
            }
            this.requestLayout();
        }
    }
}
