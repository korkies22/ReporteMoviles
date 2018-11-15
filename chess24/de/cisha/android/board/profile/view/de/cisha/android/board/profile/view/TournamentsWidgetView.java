/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TournamentsWidgetView
extends LinearLayout {
    public TournamentsWidgetView(Context context) {
        super(context);
        this.initView();
    }

    public TournamentsWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private void initView() {
        TournamentsWidgetView.inflate((Context)this.getContext(), (int)2131427522, (ViewGroup)this);
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
