/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.TextView
 */
package com.viewpagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.viewpagerindicator.R;
import com.viewpagerindicator.TabPageIndicator;

private class TabPageIndicator.TabView
extends TextView {
    private int mIndex;

    public TabPageIndicator.TabView(Context context) {
        super(context, null, R.attr.vpiTabPageIndicatorStyle);
    }

    static /* synthetic */ int access$302(TabPageIndicator.TabView tabView, int n) {
        tabView.mIndex = n;
        return n;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (TabPageIndicator.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > TabPageIndicator.this.mMaxTabWidth) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)TabPageIndicator.this.mMaxTabWidth, (int)1073741824), n2);
        }
    }
}
