/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingItemView
extends LinearLayout {
    private View _indicatorView;
    private String _title;
    private TextView _titleText;

    public SettingItemView(Context context, String string, boolean bl) {
        super(context);
        this._title = string;
        this.initView(bl);
    }

    private void initView(boolean bl) {
        int n = 0;
        this.setOrientation(0);
        SettingItemView.inflate((Context)this.getContext(), (int)2131427577, (ViewGroup)this);
        this._titleText = (TextView)this.findViewById(2131297216);
        this._titleText.setText((CharSequence)this._title);
        View view = this._indicatorView = this.findViewById(2131297215);
        if (!bl) {
            n = 4;
        }
        view.setVisibility(n);
        this.setChecked(bl);
    }

    public void setChecked(boolean bl) {
        View view = this._indicatorView;
        int n = bl ? 0 : 4;
        view.setVisibility(n);
    }
}
