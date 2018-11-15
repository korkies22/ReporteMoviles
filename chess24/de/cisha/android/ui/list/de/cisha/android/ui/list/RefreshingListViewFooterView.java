/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RelativeLayout
 */
package de.cisha.android.ui.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public class RefreshingListViewFooterView
extends RelativeLayout {
    private CustomButton _refreshButton;

    public RefreshingListViewFooterView(Context context) {
        super(context);
        this.init();
    }

    public RefreshingListViewFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        RefreshingListViewFooterView.inflate((Context)this.getContext(), (int)R.layout.list_loading_footer, (ViewGroup)this);
        this._refreshButton = (CustomButton)this.findViewById(R.id.refreshing_list_footer_button);
    }

    public void showButton(boolean bl) {
        CustomButton customButton = this._refreshButton;
        int n = bl ? 0 : 8;
        customButton.setVisibility(n);
    }
}
