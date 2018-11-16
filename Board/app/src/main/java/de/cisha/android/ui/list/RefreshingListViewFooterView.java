// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import android.widget.RelativeLayout;

public class RefreshingListViewFooterView extends RelativeLayout
{
    private CustomButton _refreshButton;
    
    public RefreshingListViewFooterView(final Context context) {
        super(context);
        this.init();
    }
    
    public RefreshingListViewFooterView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), R.layout.list_loading_footer, (ViewGroup)this);
        this._refreshButton = (CustomButton)this.findViewById(R.id.refreshing_list_footer_button);
    }
    
    public void showButton(final boolean b) {
        final CustomButton refreshButton = this._refreshButton;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        refreshButton.setVisibility(visibility);
    }
}
