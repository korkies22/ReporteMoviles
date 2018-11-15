/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RadioButton
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.settings.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingRadioButtonView
extends RelativeLayout {
    private RadioButton _radioButton;

    public SettingRadioButtonView(Context context) {
        super(context);
        this.init();
    }

    public SettingRadioButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        LayoutInflater.from((Context)this.getContext()).inflate(2131427538, (ViewGroup)this, true);
        this._radioButton = (RadioButton)this.findViewById(2131296972);
        this._radioButton.setClickable(false);
        this._radioButton.setBackgroundResource(0);
    }

    public RadioButton getRadioButton() {
        return this._radioButton;
    }

    public boolean isChecked() {
        return this._radioButton.isChecked();
    }

    public void setDescription(CharSequence charSequence) {
        ((TextView)this.findViewById(2131296971)).setText(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        ((TextView)this.findViewById(2131296973)).setText(charSequence);
    }

    public void toggle() {
        this._radioButton.setChecked(this._radioButton.isChecked() ^ true);
    }
}
