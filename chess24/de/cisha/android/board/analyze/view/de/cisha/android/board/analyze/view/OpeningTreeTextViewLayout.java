/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class OpeningTreeTextViewLayout
extends LinearLayout {
    public OpeningTreeTextViewLayout(Context context) {
        super(context);
    }

    public OpeningTreeTextViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)0), n2);
            int n4 = view.getMeasuredWidth() > n3 ? 8 : 0;
            view.setVisibility(n4);
        }
        super.onMeasure(n, n2);
    }
}
