/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.text.Layout
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewPropertyAnimator
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class SnackbarContentLayout
extends LinearLayout
implements BaseTransientBottomBar.ContentViewCallback {
    private Button mActionView;
    private int mMaxInlineActionWidth;
    private int mMaxWidth;
    private TextView mMessageView;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
        this.mMaxWidth = context.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.mMaxInlineActionWidth = context.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        context.recycle();
    }

    private static void updateTopBottomPadding(View view, int n, int n2) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), n, ViewCompat.getPaddingEnd(view), n2);
            return;
        }
        view.setPadding(view.getPaddingLeft(), n, view.getPaddingRight(), n2);
    }

    private boolean updateViewsWithinLayout(int n, int n2, int n3) {
        boolean bl;
        if (n != this.getOrientation()) {
            this.setOrientation(n);
            bl = true;
        } else {
            bl = false;
        }
        if (this.mMessageView.getPaddingTop() != n2 || this.mMessageView.getPaddingBottom() != n3) {
            SnackbarContentLayout.updateTopBottomPadding((View)this.mMessageView, n2, n3);
            bl = true;
        }
        return bl;
    }

    @Override
    public void animateContentIn(int n, int n2) {
        this.mMessageView.setAlpha(0.0f);
        ViewPropertyAnimator viewPropertyAnimator = this.mMessageView.animate().alpha(1.0f);
        long l = n2;
        viewPropertyAnimator = viewPropertyAnimator.setDuration(l);
        long l2 = n;
        viewPropertyAnimator.setStartDelay(l2).start();
        if (this.mActionView.getVisibility() == 0) {
            this.mActionView.setAlpha(0.0f);
            this.mActionView.animate().alpha(1.0f).setDuration(l).setStartDelay(l2).start();
        }
    }

    @Override
    public void animateContentOut(int n, int n2) {
        this.mMessageView.setAlpha(1.0f);
        ViewPropertyAnimator viewPropertyAnimator = this.mMessageView.animate().alpha(0.0f);
        long l = n2;
        viewPropertyAnimator = viewPropertyAnimator.setDuration(l);
        long l2 = n;
        viewPropertyAnimator.setStartDelay(l2).start();
        if (this.mActionView.getVisibility() == 0) {
            this.mActionView.setAlpha(1.0f);
            this.mActionView.animate().alpha(0.0f).setDuration(l).setStartDelay(l2).start();
        }
    }

    public Button getActionView() {
        return this.mActionView;
    }

    public TextView getMessageView() {
        return this.mMessageView;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageView = (TextView)this.findViewById(R.id.snackbar_text);
        this.mActionView = (Button)this.findViewById(R.id.snackbar_action);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = n;
        if (this.mMaxWidth > 0) {
            n3 = n;
            if (this.getMeasuredWidth() > this.mMaxWidth) {
                n3 = View.MeasureSpec.makeMeasureSpec((int)this.mMaxWidth, (int)1073741824);
                super.onMeasure(n3, n2);
            }
        }
        int n4 = this.getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int n5 = this.getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        n = this.mMessageView.getLayout().getLineCount();
        int n6 = 1;
        n = n > 1 ? 1 : 0;
        if (n != 0 && this.mMaxInlineActionWidth > 0 && this.mActionView.getMeasuredWidth() > this.mMaxInlineActionWidth) {
            if (!this.updateViewsWithinLayout(1, n4, n4 - n5)) return;
            n = n6;
        } else {
            n = n != 0 ? n4 : n5;
            if (!this.updateViewsWithinLayout(0, n, n)) return;
            n = n6;
        }
        if (n == 0) return;
        super.onMeasure(n3, n2);
    }
}
