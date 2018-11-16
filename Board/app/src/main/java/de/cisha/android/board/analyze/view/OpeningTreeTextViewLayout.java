// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import android.view.View;
import android.view.View.MeasureSpec;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class OpeningTreeTextViewLayout extends LinearLayout
{
    public OpeningTreeTextViewLayout(final Context context) {
        super(context);
    }
    
    public OpeningTreeTextViewLayout(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    protected void onMeasure(final int n, final int n2) {
        final int size = View.MeasureSpec.getSize(n);
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            child.measure(View.MeasureSpec.makeMeasureSpec(size, 0), n2);
            int visibility;
            if (child.getMeasuredWidth() > size) {
                visibility = 8;
            }
            else {
                visibility = 0;
            }
            child.setVisibility(visibility);
        }
        super.onMeasure(n, n2);
    }
}
