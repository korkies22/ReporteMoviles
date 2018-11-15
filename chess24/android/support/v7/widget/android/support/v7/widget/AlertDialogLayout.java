/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class AlertDialogLayout
extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        super(context);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n4 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n3, 0, n2, 0);
            layoutParams.height = n4;
        }
    }

    private static int resolveMinimumHeight(View view) {
        int n = ViewCompat.getMinimumHeight(view);
        if (n > 0) {
            return n;
        }
        if (view instanceof ViewGroup && (view = (ViewGroup)view).getChildCount() == 1) {
            return AlertDialogLayout.resolveMinimumHeight(view.getChildAt(0));
        }
        return 0;
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n3 + n, n4 + n2);
    }

    private boolean tryOnMeasure(int n, int n2) {
        View view;
        View view2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8 = this.getChildCount();
        View view3 = view = (view2 = null);
        View view4 = view;
        for (n6 = 0; n6 < n8; ++n6) {
            view = this.getChildAt(n6);
            if (view.getVisibility() == 8) continue;
            n5 = view.getId();
            if (n5 == R.id.topPanel) {
                view2 = view;
                continue;
            }
            if (n5 == R.id.buttonPanel) {
                view4 = view;
                continue;
            }
            if (n5 != R.id.contentPanel && n5 != R.id.customPanel) {
                return false;
            }
            if (view3 != null) {
                return false;
            }
            view3 = view;
        }
        int n9 = View.MeasureSpec.getMode((int)n2);
        int n10 = View.MeasureSpec.getSize((int)n2);
        int n11 = View.MeasureSpec.getMode((int)n);
        int n12 = this.getPaddingTop() + this.getPaddingBottom();
        if (view2 != null) {
            view2.measure(n, 0);
            n12 += view2.getMeasuredHeight();
            n5 = View.combineMeasuredStates((int)0, (int)view2.getMeasuredState());
        } else {
            n5 = 0;
        }
        if (view4 != null) {
            view4.measure(n, 0);
            n6 = AlertDialogLayout.resolveMinimumHeight(view4);
            n3 = view4.getMeasuredHeight() - n6;
            n12 += n6;
            n5 = View.combineMeasuredStates((int)n5, (int)view4.getMeasuredState());
        } else {
            n3 = n6 = 0;
        }
        if (view3 != null) {
            n4 = n9 == 0 ? 0 : View.MeasureSpec.makeMeasureSpec((int)Math.max(0, n10 - n12), (int)n9);
            view3.measure(n, n4);
            n7 = view3.getMeasuredHeight();
            n12 += n7;
            n5 = View.combineMeasuredStates((int)n5, (int)view3.getMeasuredState());
        } else {
            n7 = 0;
        }
        int n13 = n10 - n12;
        n10 = n5;
        int n14 = n13;
        n4 = n12;
        if (view4 != null) {
            n3 = Math.min(n13, n3);
            n10 = n13;
            n4 = n6;
            if (n3 > 0) {
                n10 = n13 - n3;
                n4 = n6 + n3;
            }
            view4.measure(n, View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824));
            n4 = n12 - n6 + view4.getMeasuredHeight();
            n6 = View.combineMeasuredStates((int)n5, (int)view4.getMeasuredState());
            n14 = n10;
            n10 = n6;
        }
        n5 = n10;
        n6 = n4;
        if (view3 != null) {
            n5 = n10;
            n6 = n4;
            if (n14 > 0) {
                view3.measure(n, View.MeasureSpec.makeMeasureSpec((int)(n7 + n14), (int)n9));
                n6 = n4 - n7 + view3.getMeasuredHeight();
                n5 = View.combineMeasuredStates((int)n10, (int)view3.getMeasuredState());
            }
        }
        n4 = 0;
        for (n12 = 0; n12 < n8; ++n12) {
            view = this.getChildAt(n12);
            n10 = n4;
            if (view.getVisibility() != 8) {
                n10 = Math.max(n4, view.getMeasuredWidth());
            }
            n4 = n10;
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)(n4 + (this.getPaddingLeft() + this.getPaddingRight())), (int)n, (int)n5), View.resolveSizeAndState((int)n6, (int)n2, (int)0));
        if (n11 != 1073741824) {
            this.forceUniformWidth(n8, n2);
        }
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getPaddingLeft();
        int n6 = n3 - n;
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingRight();
        n = this.getMeasuredHeight();
        int n9 = this.getChildCount();
        int n10 = this.getGravity();
        n3 = n10 & 112;
        if (n3 != 16) {
            n = n3 != 80 ? this.getPaddingTop() : this.getPaddingTop() + n4 - n2 - n;
        } else {
            n3 = this.getPaddingTop();
            n = (n4 - n2 - n) / 2 + n3;
        }
        Drawable drawable = this.getDividerDrawable();
        n3 = drawable == null ? 0 : drawable.getIntrinsicHeight();
        for (n4 = 0; n4 < n9; ++n4) {
            drawable = this.getChildAt(n4);
            n2 = n;
            if (drawable != null) {
                n2 = n;
                if (drawable.getVisibility() != 8) {
                    int n11;
                    int n12 = drawable.getMeasuredWidth();
                    int n13 = drawable.getMeasuredHeight();
                    LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)drawable.getLayoutParams();
                    n2 = n11 = layoutParams.gravity;
                    if (n11 < 0) {
                        n2 = n10 & 8388615;
                    }
                    n2 = (n2 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this)) & 7) != 1 ? (n2 != 5 ? layoutParams.leftMargin + n5 : n6 - n7 - n12 - layoutParams.rightMargin) : (n6 - n5 - n8 - n12) / 2 + n5 + layoutParams.leftMargin - layoutParams.rightMargin;
                    n11 = n;
                    if (this.hasDividerBeforeChildAt(n4)) {
                        n11 = n + n3;
                    }
                    n = n11 + layoutParams.topMargin;
                    this.setChildFrame((View)drawable, n2, n, n12, n13);
                    n2 = n + (n13 + layoutParams.bottomMargin);
                }
            }
            n = n2;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (!this.tryOnMeasure(n, n2)) {
            super.onMeasure(n, n2);
        }
    }
}
