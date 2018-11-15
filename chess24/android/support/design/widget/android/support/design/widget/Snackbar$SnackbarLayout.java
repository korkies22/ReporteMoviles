/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static final class Snackbar.SnackbarLayout
extends BaseTransientBottomBar.SnackbarBaseLayout {
    public Snackbar.SnackbarLayout(Context context) {
        super(context);
    }

    public Snackbar.SnackbarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        n2 = this.getChildCount();
        int n3 = this.getMeasuredWidth();
        int n4 = this.getPaddingLeft();
        int n5 = this.getPaddingRight();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (view.getLayoutParams().width != -1) continue;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n4 - n5), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824));
        }
    }
}
