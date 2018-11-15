/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.widget.ListView
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AlertController;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.ListView;

public static class AlertController.RecycleListView
extends ListView {
    private final int mPaddingBottomNoButtons;
    private final int mPaddingTopNoTitle;

    public AlertController.RecycleListView(Context context) {
        this(context, null);
    }

    public AlertController.RecycleListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
        this.mPaddingBottomNoButtons = context.getDimensionPixelOffset(R.styleable.RecycleListView_paddingBottomNoButtons, -1);
        this.mPaddingTopNoTitle = context.getDimensionPixelOffset(R.styleable.RecycleListView_paddingTopNoTitle, -1);
    }

    public void setHasDecor(boolean bl, boolean bl2) {
        if (!bl2 || !bl) {
            int n = this.getPaddingLeft();
            int n2 = bl ? this.getPaddingTop() : this.mPaddingTopNoTitle;
            int n3 = this.getPaddingRight();
            int n4 = bl2 ? this.getPaddingBottom() : this.mPaddingBottomNoButtons;
            this.setPadding(n, n2, n3, n4);
        }
    }
}
