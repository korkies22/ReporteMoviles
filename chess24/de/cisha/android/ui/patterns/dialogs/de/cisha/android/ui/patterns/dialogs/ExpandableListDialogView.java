/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.dialogs.DropDownListView;
import de.cisha.android.ui.patterns.dialogs.SimpleDialogView;

public class ExpandableListDialogView
extends SimpleDialogView {
    private DropDownListView _dropDownListView;
    private boolean _interpolate = true;

    public ExpandableListDialogView(Context context, View view, View view2) {
        super(context);
        this._dropDownListView = new DropDownListView(context);
        context = new LinearLayout.LayoutParams(-1, -1);
        this._dropDownListView.setHeadView(view, (LinearLayout.LayoutParams)context);
        this._dropDownListView.setContentView(view2);
        this.getContentViewGroup().addView((View)this._dropDownListView, (int)(320.0f * this.getResources().getDisplayMetrics().density), -2);
    }

    public void enableAnimationInterpolators(boolean bl) {
        this._interpolate = bl;
    }

    public boolean isContentViewOpened() {
        return this._dropDownListView.contentViewIsOpened();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n2);
        TextView textView = this._title;
        int n4 = 0;
        int n5 = textView == null ? 0 : this._title.getMeasuredHeight();
        if (this._bottom != null) {
            n4 = this._bottom.getMeasuredHeight();
        }
        this._dropDownListView.setMaxHeight(n3 - n5 - n4 - 10);
        super.onMeasure(n, n2);
    }

    public void openCloseContentView(boolean bl) {
        this._dropDownListView.openCloseContentView(bl, this._interpolate);
    }
}
