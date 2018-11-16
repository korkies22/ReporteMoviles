// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.View;
import android.widget.LinearLayout;

public class SettingItemView extends LinearLayout
{
    private View _indicatorView;
    private String _title;
    private TextView _titleText;
    
    public SettingItemView(final Context context, final String title, final boolean b) {
        super(context);
        this._title = title;
        this.initView(b);
    }
    
    private void initView(final boolean checked) {
        int visibility = 0;
        this.setOrientation(0);
        inflate(this.getContext(), 2131427577, (ViewGroup)this);
        (this._titleText = (TextView)this.findViewById(2131297216)).setText((CharSequence)this._title);
        this._indicatorView = this.findViewById(2131297215);
        final View indicatorView = this._indicatorView;
        if (!checked) {
            visibility = 4;
        }
        indicatorView.setVisibility(visibility);
        this.setChecked(checked);
    }
    
    public void setChecked(final boolean b) {
        final View indicatorView = this._indicatorView;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 4;
        }
        indicatorView.setVisibility(visibility);
    }
}
