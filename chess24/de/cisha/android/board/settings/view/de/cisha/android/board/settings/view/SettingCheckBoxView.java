/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.settings.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingCheckBoxView
extends RelativeLayout {
    private CheckBox _checkBox;

    public SettingCheckBoxView(Context context) {
        super(context);
        this.init();
    }

    public SettingCheckBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        SettingCheckBoxView.inflate((Context)this.getContext(), (int)2131427540, (ViewGroup)this);
        this._checkBox = (CheckBox)this.findViewById(2131296970);
        this.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SettingCheckBoxView.this._checkBox.toggle();
            }
        });
    }

    public CheckBox getCheckBox() {
        return this._checkBox;
    }

    public void setDescription(CharSequence charSequence) {
        ((TextView)this.findViewById(2131296971)).setText(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        ((TextView)this.findViewById(2131296973)).setText(charSequence);
    }

}
