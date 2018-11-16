// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class TournamentsWidgetView extends LinearLayout
{
    public TournamentsWidgetView(final Context context) {
        super(context);
        this.initView();
    }
    
    public TournamentsWidgetView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    private void initView() {
        inflate(this.getContext(), 2131427522, (ViewGroup)this);
        this.setOrientation(1);
    }
    
    public TextView getViewErrorText() {
        return (TextView)this.findViewById(2131296853);
    }
    
    public ViewGroup getViewGroupTournamentsList() {
        return (ViewGroup)this.findViewById(2131296856);
    }
    
    public View getViewLoadingProgressbar() {
        return this.findViewById(2131296854);
    }
    
    public View getViewShowAllButton() {
        return this.findViewById(2131296852);
    }
}
