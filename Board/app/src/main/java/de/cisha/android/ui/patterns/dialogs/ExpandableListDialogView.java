// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.widget.TextView;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.content.Context;

public class ExpandableListDialogView extends SimpleDialogView
{
    private DropDownListView _dropDownListView;
    private boolean _interpolate;
    
    public ExpandableListDialogView(final Context context, final View view, final View contentView) {
        super(context);
        this._interpolate = true;
        (this._dropDownListView = new DropDownListView(context)).setHeadView(view, new LinearLayout.LayoutParams(-1, -1));
        this._dropDownListView.setContentView(contentView);
        this.getContentViewGroup().addView((View)this._dropDownListView, (int)(320.0f * this.getResources().getDisplayMetrics().density), -2);
    }
    
    public void enableAnimationInterpolators(final boolean interpolate) {
        this._interpolate = interpolate;
    }
    
    public boolean isContentViewOpened() {
        return this._dropDownListView.contentViewIsOpened();
    }
    
    @Override
    protected void onMeasure(final int n, final int n2) {
        final int size = View.MeasureSpec.getSize(n2);
        final TextView title = this._title;
        int measuredHeight = 0;
        int measuredHeight2;
        if (title == null) {
            measuredHeight2 = 0;
        }
        else {
            measuredHeight2 = this._title.getMeasuredHeight();
        }
        if (this._bottom != null) {
            measuredHeight = this._bottom.getMeasuredHeight();
        }
        this._dropDownListView.setMaxHeight(size - measuredHeight2 - measuredHeight - 10);
        super.onMeasure(n, n2);
    }
    
    public void openCloseContentView(final boolean b) {
        this._dropDownListView.openCloseContentView(b, this._interpolate);
    }
}
